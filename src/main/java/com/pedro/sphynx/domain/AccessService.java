package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.access.AccessDataComplete;
import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.infrastructure.entities.Access;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.*;

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
    private ConsumerRepository consumerRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private LocalGroupRepository localGroupRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    @Transactional
    public AccessDataComplete validateCreation(AccessDataInput data) {
        String macFormatted = data.mac().replaceAll("-", ":");
        String tag = data.tag();

        if (!consumerRepository.existsByTag(tag)) {
            throw new Validation(messages.getString("error.tagDontExists"));
        }

        ConsumerDataComplete consumer = new ConsumerDataComplete(consumerRepository.findByTag(tag));

        if (!consumerRepository.existsByRa(consumer.ra())) {
            throw new Validation(messages.getString("error.raDontExistsInConsumer"));
        }

        if (!localRepository.existsByMac(macFormatted)) {
            throw new Validation(messages.getString("error.localDontExists"));
        }

        String consumerGroup = consumerRepository.findByTag(data.tag()).getGroup().getName();
        List<String> localGroups = localGroupRepository.findAllByLocalMac(macFormatted).stream().map(l -> l.getGroup().getName()).collect(Collectors.toList());

        LocalDataComplete local = new LocalDataComplete(localRepository.findByMac(macFormatted));

        if(!localGroups.contains(consumerGroup)){
            return createAccess(consumer, local, false, null);
        }

        return createAccess(consumer, local, true, null);

        
    }

    private AccessDataComplete createAccess(ConsumerDataComplete consumer, LocalDataComplete local, boolean hasPermission, String errorMessage) {
        Access access = new Access(null, consumerRepository.findByTag(consumer.tag()), localRepository.findByMac(local.mac().replaceAll("-", ":")), hasPermission, LocalDateTime.now());
        accessRepository.save(access);
        entityManager.flush();

        return new AccessDataComplete(access);
    }

    public List<AccessDataComplete> getAllAccesses(Optional<String> ra, Optional<String> local, Optional<String> date) {
        List<AccessDataComplete> listAccess;

        if(ra.isPresent() && local.isEmpty() && date.isEmpty()){
            listAccess = accessRepository.findAllByConsumerRa(ra.get())
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

            listAccess = accessRepository.findAllByConsumer_RaAndLocal_NameAndDateBetween(ra.get(), local.get(), dateTimeStart, dateTimeEnd)
                    .stream()
                    .map(AccessDataComplete::new)
                    .sorted(Comparator.comparing(AccessDataComplete::id))
                    .toList();
        }

        else{
            listAccess = accessRepository.findAll()
                    .stream()
                    .map(AccessDataComplete::new)
                    .sorted(Comparator.comparing(AccessDataComplete::id))
                    .toList();
        }

        return listAccess;
    }
}