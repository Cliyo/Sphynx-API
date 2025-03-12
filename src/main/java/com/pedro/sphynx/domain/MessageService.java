package com.pedro.sphynx.domain;

import com.pedro.sphynx.application.dtos.message.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
public class MessageService {

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    public MessageDTO createMessage(int status, Object object){
        return new MessageDTO(status, messages.getString("success." + String.valueOf(status)), object);
    }
}
