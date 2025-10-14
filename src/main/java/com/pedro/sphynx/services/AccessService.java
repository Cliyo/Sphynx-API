package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.access.AccessDataComplete;
import com.pedro.sphynx.dtos.access.AccessDataFingerprintInput;
import com.pedro.sphynx.dtos.access.AccessDataTagInput;
import com.pedro.sphynx.dtos.auth.UserDataComplete;
import com.pedro.sphynx.dtos.local.LocalDataComplete;
import com.pedro.sphynx.entities.Access;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.exceptions.Validation;

import com.pedro.sphynx.repositories.AccessRepository;
import com.pedro.sphynx.repositories.LocalRepository;
import com.pedro.sphynx.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private EmailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    @Transactional
    public AccessDataComplete validateCreation(AccessDataTagInput data) {
        String macFormatted = data.mac().replaceAll("-", ":");
        String tag = data.tag();

        if (!userRepository.existsByTag(tag)) {
            throw new Validation(messages.getString("error.tagDontExists"));
        }

        UserDataComplete consumer = new UserDataComplete(userRepository.findByTag(tag));

        if (!userRepository.existsByRa(consumer.ra())) {
            throw new Validation(messages.getString("error.raDontExistsInConsumer"));
        }

        if (!localRepository.existsByMac(macFormatted)) {
            throw new Validation(messages.getString("error.localDontExists"));
        }

        String consumerGroup = userRepository.findByTag(data.tag()).getGroup().getName();
        List<String> locals = localRepository.findAllByMac(macFormatted).stream().flatMap(local -> local.getGroups().stream()).map(group -> group.getName()).collect(Collectors.toList());

        LocalDataComplete local = new LocalDataComplete(localRepository.findByMac(macFormatted));

        AccessDataComplete accessDataComplete;
        Boolean hasPermission = locals.contains(consumerGroup);
        if(!hasPermission){
            accessDataComplete = createAccess(consumer, local, false, null);
        } else {
            accessDataComplete = createAccess(consumer, local, true, null);
        }

        emailService.sendSimpleMessage(
            consumer.userCreator().user(),
            "Sphynx | Acesso " + (hasPermission ? "autorizado" : "negado"), 
            "Ola " + consumer.userCreator().name() + ",\n\nO dependente de nome " + consumer.name() + " teve seu acesso " + (hasPermission ? "autorizado" : "negado") + " em " + local.name() + "." + "\n\n Dia e hora do acesso: " + accessDataComplete.date() + "\n\n Atenciosamente,\nEquipe Sphynx"
        );

        return accessDataComplete;
    }

    @Transactional
    public AccessDataComplete validateCreation(AccessDataFingerprintInput data) {
        String macFormatted = data.mac().replaceAll("-", ":");
        long fingerprint = Integer.parseInt(data.fingerprint());

        // byte[] fingerprintMatch = fingerprintService.matchFingerprint(fingerprint);

        // if (fingerprintMatch == null) {
        //     throw new Validation(messages.getString("error.tagDontExists"));
        // }

        if (!userRepository.existsByFingerprint(fingerprint)) {
            throw new Validation(messages.getString("error.tagDontExists"));
        }

        UserDataComplete consumer = new UserDataComplete(userRepository.findByFingerprint(fingerprint));

        if (!userRepository.existsByRa(consumer.ra())) {
            throw new Validation(messages.getString("error.raDontExistsInConsumer"));
        }

        if (!localRepository.existsByMac(macFormatted)) {
            throw new Validation(messages.getString("error.localDontExists"));
        }

        String consumerGroup = userRepository.findByFingerprint(fingerprint).getGroup().getName();
        List<String> locals = localRepository.findAllByMac(macFormatted).stream().flatMap(local -> local.getGroups().stream()).map(group -> group.getName()).collect(Collectors.toList());

        LocalDataComplete local = new LocalDataComplete(localRepository.findByMac(macFormatted));

        if(!locals.contains(consumerGroup)){
            return createAccess(consumer, local, false, null);
        }

        return createAccess(consumer, local, true, null);

    }

    private AccessDataComplete createAccess(UserDataComplete consumer, LocalDataComplete local, boolean hasPermission, String errorMessage) {
        User user = userRepository.findByTag(consumer.tag());
        
        Access access = new Access(
            null,
            user,
            localRepository.findByMac(local.mac().replaceAll("-", ":")),
            user.getUnit(),
            hasPermission, 
            LocalDateTime.now()
        );
        accessRepository.save(access);
        entityManager.flush();

        return new AccessDataComplete(access);
    }

    public List<AccessDataComplete> getAllAccesses(Optional<String> ra, Optional<String> local, Optional<String> date) {
        List<AccessDataComplete> listAccess;

        if(ra.isPresent() && local.isEmpty() && date.isEmpty()){
            listAccess = accessRepository.findAllByUserRa(ra.get())
                    .stream()
                    .map(AccessDataComplete::new)
                    .sorted(Comparator.comparing(AccessDataComplete::id))
                    .toList();
        }

        else if(ra.isEmpty() && local.isPresent() && date.isEmpty()){
            listAccess = accessRepository.findAllByLocalName(local.get())
                    .stream()
                    .map(AccessDataComplete::new)
                    .sorted(Comparator.comparing(AccessDataComplete::id))
                    .toList();
        }

        else if(ra.isEmpty() && local.isEmpty() && date.isPresent()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime dateTimeStart = LocalDate.parse(date.get(), formatter).atStartOfDay();
            LocalDateTime dateTimeEnd = dateTimeStart.plusDays(1);

            listAccess = accessRepository.findAllByDateBetween(dateTimeStart, dateTimeEnd)
                    .stream()
                    .map(AccessDataComplete::new)
                    .sorted(Comparator.comparing(AccessDataComplete::id))
                    .toList();
        }

        else if(ra.isPresent() &&  local.isPresent() && date.isPresent()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime dateTimeStart = LocalDate.parse(date.get(), formatter).atStartOfDay();
            LocalDateTime dateTimeEnd = dateTimeStart.plusDays(1);

            listAccess = accessRepository.findAllByUser_RaAndLocal_NameAndDateBetween(ra.get(), local.get(), dateTimeStart, dateTimeEnd)
                    .stream()
                    .map(AccessDataComplete::new)
                    .sorted(Comparator.comparing(AccessDataComplete::id))
                    .toList();
        }

        else{
            listAccess = accessRepository.findAll()
                    .stream()
                    .map(AccessDataComplete::new)
                    .toList();
        }

        System.out.println("acessos: " + listAccess);
        return listAccess;
    }
}