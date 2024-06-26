package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.group.GroupDataComplete;
import com.pedro.sphynx.application.dtos.group.GroupDataInput;
import com.pedro.sphynx.infrastructure.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class GroupService {

    @Autowired
    private GroupRepository repository;

    public GroupDataComplete createVerify(GroupDataInput data, String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        return null;
    }

    public void deleteVerify(String level, String language) {
        ResourceBundle messages = defineMessagesLanguage(language);


    }
}
