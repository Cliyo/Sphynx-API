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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public AccessDataComplete validateCreation(AccessDataInput data){
        ResourceBundle messages = defineMessagesLanguage(null);

        if(!consumerRepository.existsByTag(data.tag())){
            throw new Validation(messages.getString("error.tagDontExists"));
        }
        ConsumerDataComplete consumer = new ConsumerDataComplete(consumerRepository.findByTag(data.tag()));

        if(!consumerRepository.existsByRa(consumer.ra())){
            throw new Validation(messages.getString("error.raDontExistsInConsumer"));
        }

        if(!localRepository.existsByMac(data.mac())){
            throw new Validation(messages.getString("error.localDontExists"));
        }
        LocalDataComplete local = new LocalDataComplete(localRepository.findByMac(data.mac()));

        if(local.permission().level() < consumer.permission().level()){
            var access = new Access(null, consumerRepository.findByTag(data.tag()), localRepository.findByMac(data.mac()), false, LocalDateTime.now());

            accessRepository.save(access);

            throw new Validation(messages.getString("error.consumerDontHavePermission"));
        }

        var access = new Access(null, consumerRepository.findByTag(data.tag()), localRepository.findByMac(data.mac()), true, LocalDateTime.now());

        accessRepository.save(access);

        return new AccessDataComplete(access);
    }

}
