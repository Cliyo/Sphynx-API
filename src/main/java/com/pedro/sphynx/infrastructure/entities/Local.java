package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.local.LocalDataInputDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "locals")
@Entity(name = "Local")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Local {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mac;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    public Local(LocalDataInputDTO data){
        this.name = data.nome();
        this.mac = data.mac();
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
