package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.access.AccessDataComplete;
import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.infrastructure.entities.Access;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.AccessRepository;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import com.pedro.sphynx.infrastructure.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Service
public class AccessService {

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LocalRepository localRepository;

    private ResourceBundle messages = ResourceBundle.getBundle("messages");

    public AccessDataComplete validateCreation(AccessDataInput data){
        if(!consumerRepository.existsByTag(data.tag())){
            throw new Validation(messages.getString("error.tagDontExists"));
        }

        ConsumerDataComplete consumer = new ConsumerDataComplete(consumerRepository.findByTag(data.tag()));
        if(!personRepository.existsByRa(consumer.person().ra())){
            throw new Validation(messages.getString("error.raDontExistsInPerson"));
        }

        if(!localRepository.existsByName(data.local())){
            throw new Validation(messages.getString("error.localDontExists"));
        }

        //validacao se o consumer pode acessar o lugar, ainda falta criar tabela para os locais,
        //adicionar coluna de permissao no consumer

        var access = new Access(null, consumerRepository.findByTag(data.tag()), localRepository.findByName(data.local()), LocalDateTime.now());

        accessRepository.save(access);

        return new AccessDataComplete(access);
    }

}
