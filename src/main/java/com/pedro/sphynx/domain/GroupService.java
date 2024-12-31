package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
import com.pedro.sphynx.application.dtos.group.GroupDataInput;
import com.pedro.sphynx.infrastructure.entities.Group;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;


    public GroupDataComplete create(GroupDataInput data, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        if(repository.existsByName(data.name())){
            throw new Validation(messages.getString("error.groupAlreadyExists"));
        }

        Group group = new Group(data);

        repository.save(group);

        return new GroupDataComplete(group);
    }

    public void delete(Integer id, String language) {
        ResourceBundle messages = defineMessagesLanguage(language);

        if(!repository.existsById(id)){
            throw new Validation(messages.getString("error.groupNotExists"));
        }
        else{
            repository.deleteById(id);
        }

    }

    public GroupDataComplete getById(Integer id, String language) {
        ResourceBundle messages = defineMessagesLanguage(language);

        if(!repository.existsById(id)){
            throw new Validation(messages.getString("error.groupNotExists"));
        }

        return new GroupDataComplete(repository.getReferenceById(id));
    }

    public List<GroupDataComplete> getAll(){
        return repository.findAll().stream().map(GroupDataComplete::new).toList();
    }
}
