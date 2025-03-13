package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
import com.pedro.sphynx.application.dtos.group.GroupDataEdit;
import com.pedro.sphynx.application.dtos.group.GroupDataInput;
import com.pedro.sphynx.infrastructure.entities.Group;
import com.pedro.sphynx.infrastructure.repository.GroupRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    public GroupDataComplete create(GroupDataInput data){
        if(repository.existsByName(data.name())){
            throw new EntityExistsException(messages.getString("error.groupAlreadyExists"));
        }

        Group group = new Group(data);

        repository.save(group);

        return new GroupDataComplete(group);
    }

    public GroupDataComplete update(GroupDataEdit data, Integer id){
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
        }

        if(repository.existsByName(data.name())){
            throw new EntityExistsException(messages.getString("error.groupAlreadyExists"));
        }

        Group group = repository.getReferenceById(id);
        group.setName(data.name());

        return new GroupDataComplete(group);
    }

    public void delete(Integer id) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
        }
        else{
            repository.deleteById(id);
        }

    }

    public GroupDataComplete getById(Integer id) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
        }

        return new GroupDataComplete(repository.getReferenceById(id));
    }

    public List<GroupDataComplete> getAll(){
        return repository.findAll()
                .stream()
                .map(GroupDataComplete::new)
                .sorted(Comparator.comparing(GroupDataComplete::id).reversed())
                .toList();
    }
}
