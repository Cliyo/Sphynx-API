package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.application.dtos.localGroup.LocalGroupDataComplete;
import com.pedro.sphynx.infrastructure.entities.Group;
import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.entities.LocalGroup;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.LocalGroupRepository;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import com.pedro.sphynx.infrastructure.repository.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

        for(int group : data.group()){
            if(!groupRepository.existsById(group)){
                throw new Validation(messages.getString("error.groupNotExists"));
            }
        }

        Local local = new Local(data);
        repository.save(local);

        for(int groupElement : data.group()){
            Group group = groupRepository.getReferenceById(groupElement);
            LocalGroup localGroup = new LocalGroup(null, local, group);
            localGroupRepository.save(localGroup);
        }

        return new LocalDataComplete(local);
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
    
    public List<LocalGroupDataComplete> getAllLocalsWithGroups() {
        List<LocalGroup> localGroups = localGroupRepository.findAll();

        //mapping Local entity and Group entity based on the localGroups list
        //will group a local with all its permission_groups without duplicates
        Map<Local,List<Group>> localsWithGroups = localGroups.stream()
            .collect(Collectors.groupingBy(
                LocalGroup::getLocal,
                Collectors.mapping(LocalGroup::getGroup,Collectors.toList())
            ));
        
                                                    //will convert the Map to and localGroupDataComplete object then to list
        return localsWithGroups.entrySet().stream().map(entry -> new LocalGroupDataComplete(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }
}
