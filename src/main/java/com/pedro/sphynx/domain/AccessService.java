package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.access.AccessDataInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.infrastructure.entities.Access;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.repository.AccessRepository;
import com.pedro.sphynx.infrastructure.repository.ConsumerRepository;
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

    private ResourceBundle messages = ResourceBundle.getBundle("messages");

    public void validateCreation(AccessDataInput data){
        if(!consumerRepository.existsByTag(data.tag())){
            throw new RuntimeException(messages.getString("error.tagDontExists"));
        }

        //validacao para ver se o local realmente existe no banco de dados

        //validacao se o consumer pode acessar o lugar, ainda falta criar tabela para os locais,
        //adicionar coluna de permissao no consumer

        var consumer = new ConsumerDataComplete(consumerRepository.findByTag(data.tag()));
        var access = new Access(null, data.tag(), consumer.ra(), data.local(), LocalDateTime.now());

        accessRepository.save(access);
    }

}
