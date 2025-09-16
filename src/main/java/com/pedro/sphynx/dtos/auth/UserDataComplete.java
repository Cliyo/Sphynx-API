package com.pedro.sphynx.dtos.auth;

import com.pedro.sphynx.entities.User;

public record UserDataComplete (Long id, String name, String ra, String user){

    public UserDataComplete(User user){
        this(user.getId(), user.getName(), user.getRa(), user.getUser());
    }
}
