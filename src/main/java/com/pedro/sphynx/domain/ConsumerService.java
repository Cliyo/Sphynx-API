package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.entities.Permission;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import com.pedro.sphynx.infrastructure.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class ConsumerService{

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public ConsumerDataComplete createVerify(ConsumerDataInput data, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        if(consumerRepository.existsByRa(data.ra())){
            throw new Validation(messages.getString("error.raAlreadyExists"));
        }

        if(!permissionRepository.existsByLevel(data.permission())){
            throw new Validation(messages.getString("error.permissionNotExists"));
        }

        else{
            Permission permission = permissionRepository.getReferenceByLevel(data.permission());
            Consumer consumer = new Consumer(data, permission);
            consumerRepository.save(consumer);
            return new ConsumerDataComplete(consumer);
        }
    }

    public ConsumerDataComplete updateVerify(ConsumerDataEditInput data, String ra, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        if(consumerRepository.existsByRa(ra)){
            var consumer = consumerRepository.getReferenceByRa(ra);
            consumer.actualizeData(data);
            return new ConsumerDataComplete(consumer);
        } else{
            throw new Validation(messages.getString("error.raDontExists"));
        }
    }

    public void deleteVerify(String ra, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        if(!consumerRepository.existsByRa(ra)){
            throw new Validation(messages.getString("error.raDontExists"));
        }
        else{
            consumerRepository.deleteByRa(ra);
        }
    }
}
