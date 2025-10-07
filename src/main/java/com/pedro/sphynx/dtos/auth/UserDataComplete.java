package com.pedro.sphynx.dtos.auth;

import com.pedro.sphynx.entities.User;

public record UserDataComplete (Long id, String name, String ra, String tag, String user, UserDataComplete userCreator){

    public UserDataComplete(User user){
        this(user.getId(), user.getName(), user.getRa(), user.getTag(), user.getUser(), user.getUserCreator() != null ? new UserDataComplete(user.getUserCreator().getId(), user.getUserCreator().getName(), user.getUserCreator().getRa(), user.getUserCreator().getTag(), user.getUserCreator().getUser(), null) : null);
    }
}
