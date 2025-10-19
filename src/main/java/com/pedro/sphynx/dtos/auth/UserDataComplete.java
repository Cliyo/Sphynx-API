package com.pedro.sphynx.dtos.auth;

import com.pedro.sphynx.dtos.group.GroupDataComplete;
import com.pedro.sphynx.dtos.unit.UnitDataComplete;
import com.pedro.sphynx.entities.User;

public record UserDataComplete (Long id, String name, String ra, String tag, String user, GroupDataComplete group, UserDataComplete userCreator, UnitDataComplete unit){

    public UserDataComplete(User user){
        this(
            user.getId(),
            user.getName(), 
            user.getRa(), 
            user.getTag() != null ? user.getTag() : null, 
            user.getUser(), 
            user.getGroup() != null ?
                new GroupDataComplete(
                    user.getGroup()
                ) : null
            ,
            user.getUserCreator() != null ? 
                new UserDataComplete(
                    user.getUserCreator()
                ) : null, 
            user.getUnit() != null ? 
                new UnitDataComplete(
                    user.getUnit()
                ) : null
        );
    }
}
