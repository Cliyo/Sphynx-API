package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalService {

    @Autowired
    private LocalRepository repository;

    public LocalDataComplete createVerify(LocalDataInput data){
        if(!repository.existsByName(data.name()) || !repository.existsByMac(data.mac())){
            Local local = new Local(data);

            repository.save(local);

            return new LocalDataComplete(local);
        }
        return null;
    }
}
