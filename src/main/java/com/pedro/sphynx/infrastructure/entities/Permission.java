package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.permission.PermissionDataInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "permissions")
@Entity(name = "Permission")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    @Id
    private Integer level;

    private String name;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    public Permission(PermissionDataInput data){
        this.level = data.level();
        this.name = data.name();
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
