package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.message.MessageDTO;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

import static com.pedro.sphynx.domain.utils.LanguageService.defineMessagesLanguage;

@Service
public class MessageService {
    public MessageDTO createMessage(int status, Object object, @Nullable String language){
        ResourceBundle messages = defineMessagesLanguage(language);

        return new MessageDTO(status, messages.getString("success." + String.valueOf(status)), object);
    }
}
