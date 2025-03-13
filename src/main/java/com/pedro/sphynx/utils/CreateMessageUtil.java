package com.pedro.sphynx.utils;

import com.pedro.sphynx.dtos.message.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@Service
public class CreateMessageUtil {

    private final ResourceBundle messages = ResourceBundle.getBundle("messagesPt");

    public MessageDTO createMessage(int status, Object object){
        return new MessageDTO(status, messages.getString("success." + String.valueOf(status)), object);
    }
}
