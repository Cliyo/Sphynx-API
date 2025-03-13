package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.entities.Consumer;
import com.pedro.sphynx.entities.Group;
import com.pedro.sphynx.exceptions.Validation;
import com.pedro.sphynx.repositories.ConsumerRepository;
import com.pedro.sphynx.repositories.GroupRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
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
            throw new EntityExistsException(messages.getString("error.raAlreadyExists"));
        }

        if(consumerRepository.existsByTag(data.tag())){
            throw new EntityExistsException(messages.getString("error.tagAlreadyExists"));
        }

        if(!groupRepository.existsById(data.group())){
            throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
        }

        Group group = groupRepository.getReferenceById(data.group());

        Consumer consumer = new Consumer(data, group);
        consumerRepository.save(consumer);

        return new ConsumerDataComplete(consumer);

    }

    public List<ConsumerDataComplete> getAll(Optional<String> group){
        List<ConsumerDataComplete> listConsumers;

        listConsumers = consumerRepository.findAll()
                .stream()
                .map(ConsumerDataComplete::new)
                .sorted(Comparator.comparing(ConsumerDataComplete::id).reversed())
                .toList();

        return listConsumers;
    }

    public ConsumerDataComplete update(ConsumerDataEditInput data, Long id){
        if(!consumerRepository.existsById(id)) {
            throw new EntityNotFoundException(messages.getString("error.idDontExists"));
        }

        if(consumerRepository.existsByRa(data.ra())){
            throw new EntityExistsException(messages.getString("error.raAlreadyExists"));
        }

        if(consumerRepository.existsByTag(data.tag())){
            throw new EntityExistsException(messages.getString("error.tagAlreadyExists"));
        }

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
    }

    public void delete(Long id){
        if(!consumerRepository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.raDontExists"));
        }
        else{
            consumerRepository.deleteById(id);
        }
    }

    public ConsumerDataComplete getById(Long id){
        if(!consumerRepository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.raDontExists"));
        }

        return new ConsumerDataComplete(consumerRepository.getReferenceById(id));
    }
}
