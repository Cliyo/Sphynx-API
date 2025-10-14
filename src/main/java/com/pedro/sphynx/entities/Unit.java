package com.pedro.sphynx.entities;

import com.pedro.sphynx.dtos.unit.UnitDataInput;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "units")
@Entity(name = "Unit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<User> users;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<Local> locals;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<Group> groups;

    @OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
    private List<Access> accesses;

    public Unit(UnitDataInput data){
        this.id = null;
        this.name = data.name();
        this.users = null;
        this.locals = null;
        this.groups = null;
        this.accesses = null;
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }

    public Unit(UnitDataInput data, List<User> users){
        this.id = null;
        this.name = data.name();
        this.users = List.copyOf(users);
        this.locals = null;
        this.groups = null;
        this.accesses = null;
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
