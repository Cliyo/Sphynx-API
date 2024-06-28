package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.entities.ConsumerGroup;
import com.pedro.sphynx.infrastructure.entities.Group;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import com.pedro.sphynx.infrastructure.repository.ConsumerGroupRepository;
import com.pedro.sphynx.infrastructure.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class ConsumerService{

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ConsumerGroupRepository consumerGroupRepository;

    public ConsumerDataComplete createVerify(ConsumerDataInput data, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        if(consumerRepository.existsByRa(data.ra())){
            throw new Validation(messages.getString("error.raAlreadyExists"));
        }

        if(!groupRepository.existsById(data.group())){
            throw new Validation(messages.getString("error.groupNotExists"));
        }

        Consumer consumer = new Consumer(data);
        consumerRepository.save(consumer);

        Group group = groupRepository.getReferenceById(data.group());
        ConsumerGroup consumerGroup = new ConsumerGroup(null, consumer, group);
        consumerGroupRepository.save(consumerGroup);

        return new ConsumerDataComplete(consumer);

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
