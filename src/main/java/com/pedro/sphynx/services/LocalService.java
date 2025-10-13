package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.local.LocalDataComplete;
import com.pedro.sphynx.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.dtos.local.LocalDataInput;
import com.pedro.sphynx.entities.Group;
import com.pedro.sphynx.entities.Local;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.exceptions.Validation;
import com.pedro.sphynx.repositories.LocalRepository;
import com.pedro.sphynx.repositories.GroupRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class LocalService {

    @Autowired
    private LocalRepository repository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LocalRepository localRepository;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    public LocalDataComplete create(LocalDataInput data, User user){
        if(repository.existsByName(data.name())){
            throw new EntityExistsException(messages.getString("error.localAlreadyExists"));
        }

        if(repository.existsByMac(data.mac())){
            throw new EntityExistsException(messages.getString("error.macAlreadyExists"));
        }

        for(int group : data.groups()){
            if(!groupRepository.existsById(group)){
                throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
            }
        }

        Local local = new Local(data, user);
        repository.save(local);

        for(int groupElement : data.groups()){
            Group group = groupRepository.getReferenceById(groupElement);
            local.getGroups().add(group);
        }

        localRepository.save(local);
        return new LocalDataComplete(local);
    }

    public LocalDataComplete update(LocalDataEditInput data, Integer id) {
        if(repository.existsById(Long.parseLong(id.toString()))){
            Local local = repository.getReferenceById(Long.parseLong(id.toString()));

            local.updateLocal(data);

            return new LocalDataComplete(local);
        }
        throw new EntityExistsException(messages.getString("error.localDontExists"));
    }
    
    public List<LocalDataComplete> getAll(User user) {
        List<Local> locals = repository.findAllByUserId(user.getId());

        return locals.stream()
                .map(local -> new LocalDataComplete(local))
                .collect(Collectors.toList());
    }

    public LocalDataComplete getById(Long id) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.localNotExists"));
        }

        Local local = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getString("error.localDontExists")));

        return new LocalDataComplete(local);
    }

    public void deleteById(Long id) {
        if(!repository.existsById(id)){
            throw new Validation(messages.getString("error.localNotExists"));
        }

        localRepository.deleteById(id);
    }
}
