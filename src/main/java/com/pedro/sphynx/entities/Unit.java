package com.pedro.sphynx.entities;

import com.pedro.sphynx.dtos.unit.UnitDataInput;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Unit(UnitDataInput data, User user){
        this.id = null;
        this.name = data.name();
        this.user = user;
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
