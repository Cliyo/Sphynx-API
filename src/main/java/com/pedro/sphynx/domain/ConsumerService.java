package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import com.pedro.sphynx.infrastructure.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Service
public class ConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private PersonRepository personRepository;

    private ResourceBundle messages = ResourceBundle.getBundle("messagesEn");

    public void defineMessagesLanguage(String language){
        if(language.equals("pt-BR")){
            messages = ResourceBundle.getBundle("messagesPt");
        }
        else if(language.equals("en-US")){
            messages = ResourceBundle.getBundle("messagesEn");
        }
    }

    public ConsumerDataComplete createVerify(ConsumerDataInput data, String language){
        defineMessagesLanguage(language);

        if(consumerRepository.existsByPersonRa(data.ra())){
            throw new Validation(messages.getString("error.raAlreadyExists"));
        }

        else if(!personRepository.existsByRa(data.ra())){
            throw new Validation(messages.getString("error.raDontExistsInPerson"));
        }

        else{
            Consumer consumer = new Consumer(null, personRepository.findOneByRa(data.ra()), data.tag(), data.permission(), LocalDateTime.now(), null);
            consumerRepository.save(consumer);
            return new ConsumerDataComplete(consumer);
        }
    }

    public ConsumerDataComplete updateVerify(ConsumerDataEditInput data, String ra, String language){
        defineMessagesLanguage(language);

        if(consumerRepository.existsByPersonRa(ra)){
            var consumer = consumerRepository.getReferenceByPersonRa(ra);
            consumer.actualizeData(data);
            return new ConsumerDataComplete(consumer);
        } else{
            throw new Validation(messages.getString("error.raDontExists"));
        }
    }
}
