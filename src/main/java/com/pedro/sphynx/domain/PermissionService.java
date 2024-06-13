package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataComplete;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import com.pedro.sphynx.application.dtos.permission.PermissionDataComplete;
import com.pedro.sphynx.application.dtos.permission.PermissionDataInput;
import com.pedro.sphynx.infrastructure.entities.Consumer;
import com.pedro.sphynx.infrastructure.entities.Permission;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository repository;

    public PermissionDataComplete createVerify(PermissionDataInput data, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        if(repository.existsByLevel(data.level()) || repository.existsByName(data.name())){
            throw new Validation(messages.getString("error.permissionAlreadyExists"));
        }

        else{
            Permission permission = new Permission(data);
            repository.save(permission);
            return new PermissionDataComplete(permission);
        }
    }

    public void deleteVerify(String level, String language) {
        ResourceBundle messages = defineMessagesLanguage(language);

        if(!repository.existsByLevel(Integer.parseInt(level))){
            throw new Validation(messages.getString("error.permissionNotExists"));
        }
        else{
            repository.deleteByLevel(Integer.parseInt(level));
        }
    }
}
