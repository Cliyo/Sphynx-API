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

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class LocalService {

    @Autowired
    private LocalRepository repository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LocalGroupRepository localGroupRepository;

    @Autowired
    private LocalRepository localRepository;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    public LocalDataComplete create(LocalDataInput data){
        if(repository.existsByName(data.name())){
            throw new EntityExistsException(messages.getString("error.localAlreadyExists"));
        }

        if(repository.existsByMac(data.mac())){
            throw new EntityExistsException(messages.getString("error.macAlreadyExists"));
        }

        for(int group : data.group()){
            if(!groupRepository.existsById(group)){
                throw new EntityNotFoundException(messages.getString("error.groupDontExists"));
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

    public LocalDataComplete update(LocalDataEditInput data, Integer id) {
        if(!repository.existsById(Long.parseLong(id.toString()))){
            Local local = repository.getReferenceById(Long.parseLong(id.toString()));

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

    public void deleteByName(String name) {
        if(!repository.existsByName(name)){
            throw new Validation(messages.getString("error.localNotExists"));
        }

        localRepository.deleteByName(name);
    }
}
