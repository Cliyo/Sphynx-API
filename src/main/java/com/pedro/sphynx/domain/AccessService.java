package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.access.AccessDataComplete;
import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.infrastructure.entities.Access;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.AccessRepository;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private LocalRepository localRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public AccessDataComplete validateCreation(AccessDataInput data) {
        ResourceBundle messages = defineMessagesLanguage(null);

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

        LocalDataComplete local = new LocalDataComplete(localRepository.findByMac(macFormatted));

        if (local.permission().level() < consumer.permission().level()) {
            return createAccess(consumer, local, false, messages.getString("error.consumerDontHavePermission"));
        }
        else{
            return createAccess(consumer, local, true, null);
        }
        
    }

    private AccessDataComplete createAccess(ConsumerDataComplete consumer, LocalDataComplete local, boolean hasPermission, String errorMessage) {
        Access access = new Access(null, consumerRepository.findByTag(consumer.tag()), localRepository.findByMac(local.mac().replaceAll("-", ":")), hasPermission, LocalDateTime.now());
        accessRepository.save(access);
        entityManager.flush();

        return new AccessDataComplete(access);
    }
}