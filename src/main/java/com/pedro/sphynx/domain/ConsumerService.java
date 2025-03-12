package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.entities.Group;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import com.pedro.sphynx.infrastructure.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
public class ConsumerService{

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private GroupRepository groupRepository;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    public ConsumerDataComplete create(ConsumerDataInput data){
        if(consumerRepository.existsByRa(data.ra())){
            throw new Validation(messages.getString("error.raAlreadyExists"));
        }

        if(!groupRepository.existsById(data.group())){
            throw new Validation(messages.getString("error.groupNotExists"));
        }

        Group group = groupRepository.getReferenceById(data.group());

        Consumer consumer = new Consumer(data, group);
        consumerRepository.save(consumer);

        return new ConsumerDataComplete(consumer);

    }

    public List<ConsumerDataComplete> getAll(Optional<String> group){
        List<ConsumerDataComplete> listConsumers;

        if(group.isPresent()){
            listConsumers = consumerRepository.findAllByGroupName(group.get()).stream().map(ConsumerDataComplete::new).toList();

        }

        else{
            listConsumers = consumerRepository.findAll().stream().map(ConsumerDataComplete::new).toList();
        }

        return listConsumers;
    }

    public ConsumerDataComplete update(ConsumerDataEditInput data, Long id){
        if(consumerRepository.existsById(id)){
            var consumer = consumerRepository.getReferenceById(id);
            consumer.actualizeData(data);

            if(data.group() != null){
                if(!groupRepository.existsById(data.group())){
                    throw new Validation(messages.getString("error.groupNotExists"));
                }
                consumer.setGroup(groupRepository.getReferenceById(data.group()));
            }

            consumer.setDtupdate(LocalDateTime.now());

            return new ConsumerDataComplete(consumer);
        } else{
            throw new Validation(messages.getString("error.idDontExists"));
        }
    }

    public void delete(Long id){
        if(!consumerRepository.existsById(id)){
            throw new Validation(messages.getString("error.raDontExists"));
        }
        else{
            consumerRepository.deleteById(id);
        }
    }

    public ConsumerDataComplete getById(Long id){
        if(!consumerRepository.existsById(id)){
            throw new Validation(messages.getString("error.raDontExists"));
        }

        return new ConsumerDataComplete(consumerRepository.getReferenceById(id));
    }
}
