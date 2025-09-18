package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.local.LocalDataComplete;
import com.pedro.sphynx.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.dtos.local.LocalDataInput;
import com.pedro.sphynx.dtos.localGroup.LocalGroupDataComplete;
import com.pedro.sphynx.entities.Group;
import com.pedro.sphynx.entities.Local;
import com.pedro.sphynx.entities.LocalGroup;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.exceptions.Validation;
import com.pedro.sphynx.repositories.LocalGroupRepository;
import com.pedro.sphynx.repositories.LocalRepository;
import com.pedro.sphynx.repositories.GroupRepository;

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
            LocalGroup localGroup = new LocalGroup(null, local, group);
            localGroupRepository.save(localGroup);
        }

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

    public LocalGroupDataComplete getById(Long id) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.localNotExists"));
        }

        Local local = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messages.getString("error.localDontExists")));

        List<LocalGroup> localGroups = localGroupRepository.findByLocal(local);

        List<Group> groups = localGroups.stream()
                .map(LocalGroup::getGroup)
                .collect(Collectors.toList());

        return new LocalGroupDataComplete(local, groups);
    }

    public void deleteById(Long id) {
        if(!repository.existsById(id)){
            throw new Validation(messages.getString("error.localNotExists"));
        }

        localRepository.deleteById(id);
    }
}
