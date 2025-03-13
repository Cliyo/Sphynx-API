package com.pedro.sphynx.dtos.localGroup;

import java.util.List;

import com.pedro.sphynx.dtos.group.GroupDataComplete;
import com.pedro.sphynx.dtos.local.LocalDataComplete;
import com.pedro.sphynx.entities.Group;
import com.pedro.sphynx.entities.Local;
import com.pedro.sphynx.entities.LocalGroup;

public record LocalGroupDataComplete(LocalDataComplete local, List<GroupDataComplete> groups) {
    //constructor overload to handle different ways to call the record
    public LocalGroupDataComplete(Local local, Group group){
        this(new LocalDataComplete(local), List.of(new GroupDataComplete(group)));
    }
    public LocalGroupDataComplete(LocalGroup data){
        this(new LocalDataComplete(data.getLocal()), List.of(new GroupDataComplete(data.getGroup())));
    }
    public LocalGroupDataComplete(Local local, List<Group> groups){
        this(new LocalDataComplete(local), groups.stream().map(GroupDataComplete::new).toList());
    }
}
