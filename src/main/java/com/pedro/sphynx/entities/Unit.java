package com.pedro.sphynx.entities;

import com.pedro.sphynx.dtos.unit.UnitDataInput;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Set<User> users;

    public Unit(UnitDataInput data, Set<User> users){
        this.id = null;
        this.name = data.name();
        this.users = Set.copyOf(users);
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
