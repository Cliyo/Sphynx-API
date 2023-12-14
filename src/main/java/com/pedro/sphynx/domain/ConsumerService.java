package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInputDTO;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
import com.pedro.sphynx.infrastructure.entities.Consumer;
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

    private ResourceBundle messages = ResourceBundle.getBundle("messages");

    public ConsumerDataComplete createVerify(ConsumerDataInputDTO data){
        if(consumerRepository.existsByPersonRa(data.ra())){
            throw new RuntimeException(messages.getString("error.raAlreadyExists"));
        }

        else if(!personRepository.existsByRa(data.ra())){
            throw new RuntimeException(messages.getString("error.raDontExistsInPerson"));
        }

        else{
            Consumer consumer = new Consumer(null, personRepository.findOneByRa(data.ra()), data.tag(), LocalDateTime.now(), null);
            consumerRepository.save(consumer);
            return new ConsumerDataComplete(consumer);
        }
    }

    public ConsumerDataComplete updateVerify(ConsumerDataEditInputDTO data, String ra){
        if(consumerRepository.existsByPersonRa(ra)){
            var consumer = consumerRepository.getReferenceByPersonRa(ra);
            consumer.actualizeData(data);
            return new ConsumerDataComplete(consumer);
        } else{
            throw new RuntimeException(messages.getString("error.raDontExists"));
        }
    }
}
