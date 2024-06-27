package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.infrastructure.entities.Group;
import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.entities.LocalGroup;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.LocalGroupRepository;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import com.pedro.sphynx.infrastructure.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class LocalService {

    @Autowired
    private LocalRepository repository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LocalGroupRepository localGroupRepository;

    public LocalDataComplete createVerify(LocalDataInput data, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        if(repository.existsByName(data.name())){
            throw new Validation(messages.getString("error.localAlreadyExists"));
        }

        if(repository.existsByMac(data.mac())){
            throw new Validation(messages.getString("error.macAlreadyExists"));
        }

        if(!groupRepository.existsByName(data.group())){
            throw new Validation(messages.getString("error.groupNotExists"));
        }

        else{
            Local local = new Local(data);
            Group group = groupRepository.getReferenceByName(data.group());
            LocalGroup localGroup = new LocalGroup(null, local, group);

            repository.save(local);
            localGroupRepository.save(localGroup);

            return new LocalDataComplete(local);
        }
    }

    public LocalDataComplete updateVerify(LocalDataEditInput data, String name, String language) {
        ResourceBundle messages = defineMessagesLanguage(language);

        if(!repository.existsByName(data.mac())){
            Local local = repository.getReferenceByName(name);

            local.updateLocal(data);

            return new LocalDataComplete(local);
        }
        return null;
    }
}
