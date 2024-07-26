package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.group.GroupDataInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "permission_groups")
@Entity(name = "Group")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    public Group(GroupDataInput data){
        this.id = null;
        this.name = data.name();
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
