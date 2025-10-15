package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.group.GroupDataComplete;
import com.pedro.sphynx.dtos.group.GroupDataEdit;
import com.pedro.sphynx.dtos.group.GroupDataInput;
import com.pedro.sphynx.entities.Group;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.repositories.GroupRepository;
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

    public GroupDataComplete create(GroupDataInput data, User user){
        if(repository.existsByNameAndUnitId(data.name(), user.getUnit().getId())){
            throw new EntityExistsException(messages.getString("error.groupAlreadyExists"));
        }

        Group group = new Group(data, user);
        repository.save(group);

        return new GroupDataComplete(group);
    }

    public GroupDataComplete update(GroupDataEdit data, Integer id, User user){
        if(!repository.existsByIdAndUnitId(id, user.getUnit().getId())){
            throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
        }

        if(repository.existsByNameAndUnitId(data.name(), user.getUnit().getId())){
            throw new EntityExistsException(messages.getString("error.groupAlreadyExists"));
        }

        Group group = repository.getReferenceById(id);
        group.setName(data.name());

        return new GroupDataComplete(group);
    }

    public void delete(Integer id, User user) {
        if(!repository.existsByIdAndUnitId(id, user.getUnit().getId())){
            throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
        }
        else{
            repository.deleteById(id);
        }

    }

    public GroupDataComplete getById(Integer id, User user) {
        if(!repository.existsByIdAndUnitId(id, user.getUnit().getId())){
            throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
        }

        return new GroupDataComplete(repository.getReferenceById(id));
    }

    public List<GroupDataComplete> getAll(User user){
        List<Group> groups;

        if (user.isAdmin()) {
            groups = repository.findAll();
        } else {
            groups = repository.findAllByUnitId(user.getUnit().getId());
        }

        return groups
            .stream()
            .map(group -> new GroupDataComplete(group))
            .sorted(Comparator.comparing(GroupDataComplete::id).reversed())
            .toList();
    }

    public List<GroupDataComplete> getAllByName(String name, User user){
        return repository.findAllByNameContainingAndUnitId(name, user.getUnit().getId())
                .stream()
                .map(group -> new GroupDataComplete(group))
                .sorted(Comparator.comparing(GroupDataComplete::id).reversed())
                .toList();
    }
}
