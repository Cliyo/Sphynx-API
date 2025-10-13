package com.pedro.sphynx.services;

import com.pedro.sphynx.dtos.unit.UnitDataComplete;
import com.pedro.sphynx.dtos.unit.UnitDataEdit;
import com.pedro.sphynx.dtos.unit.UnitDataInput;
import com.pedro.sphynx.entities.Unit;
import com.pedro.sphynx.entities.User;
import com.pedro.sphynx.repositories.UnitRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class UnitService {

    @Autowired
    private UnitRepository repository;

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    public UnitDataComplete create(UnitDataInput data, User user){
        if(repository.existsByName(data.name())){
            throw new EntityExistsException(messages.getString("error.unitAlreadyExists"));
        }

        Unit unit = new Unit(data);

        repository.save(unit);

        return new UnitDataComplete(unit);
    }

    public UnitDataComplete update(UnitDataEdit data, Long id, User user) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.unitDontExists"));
        }

        if(repository.existsByName(data.name())){
            throw new EntityExistsException(messages.getString("error.unitAlreadyExists"));
        }

        Unit unit = repository.getReferenceById(id);
        unit.setName(data.name());

        return new UnitDataComplete(unit);
    }

    public void delete(Long id, User user) {
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.unitDontExists"));
        }
        else{
            repository.deleteById(id);
        }

    }

    public UnitDataComplete getById(Long id, User user){
        if(!repository.existsById(id)){
            throw new EntityNotFoundException(messages.getString("error.unitDontExists"));
        }

        return new UnitDataComplete(repository.getReferenceById(id));
    }

    public List<UnitDataComplete> getAll(User user){
        return repository.findAll()
                .stream()
                .map(unit -> new UnitDataComplete(unit))
                .sorted(Comparator.comparing(UnitDataComplete::id).reversed())
                .toList();
    }
}
