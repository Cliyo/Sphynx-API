package com.pedro.sphynx.domain.utils;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
public class LanguageService {
    private static ResourceBundle messages = ResourceBundle.getBundle("messagesEn");

    static public ResourceBundle defineMessagesLanguage(@Nullable String language){
        if(language != null){
            if(language.equals("pt-BR")){
                messages = ResourceBundle.getBundle("messagesPt");
            }
            else if(language.equals("en-US")){
                messages = ResourceBundle.getBundle("messagesEn");
            }
        }
        return messages;
    }
}
