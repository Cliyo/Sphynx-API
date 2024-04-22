package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.local.LocalDataComplete;
import com.pedro.sphynx.application.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import com.pedro.sphynx.infrastructure.entities.Local;
import com.pedro.sphynx.infrastructure.exceptions.Validation;
import com.pedro.sphynx.infrastructure.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
public class LocalService {

    @Autowired
    private LocalRepository repository;

    private ResourceBundle messages = ResourceBundle.getBundle("messagesEn");

    public void defineMessagesLanguage(String language){
        if(language.equals("pt-BR")){
            messages = ResourceBundle.getBundle("messagesPt");
        }
        else if(language.equals("en-US")){
            messages = ResourceBundle.getBundle("messagesEn");
        }
    }

    public LocalDataComplete createVerify(LocalDataInput data, String language){
        defineMessagesLanguage(language);

        if(repository.existsByName(data.name())){
            throw new Validation(messages.getString("error.localAlreadyExists"));
        }

        if(repository.existsByMac(data.mac())){
            throw new Validation(messages.getString("error.macAlreadyExists"));
        }

        else{
            Local local = new Local(data);

            repository.save(local);

            return new LocalDataComplete(local);
        }
    }

    public LocalDataComplete updateVerify(LocalDataEditInput data, String name, String language) {
        defineMessagesLanguage(language);

        if(!repository.existsByName(data.mac())){
            Local local = repository.getReferenceByName(name);

            local.updateLocal(data);

            return new LocalDataComplete(local);
        }
        return null;
    }
}
