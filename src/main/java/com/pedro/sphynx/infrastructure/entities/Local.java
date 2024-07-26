package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.application.dtos.local.LocalDataInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
        name = "locals_groups",
        joinColumns = @JoinColumn(name = "local_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    public Local(LocalDataInput data){
        this.name = data.name();
        this.mac = data.mac();
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }

    public void updateLocal(LocalDataEditInput data){
        this.mac = data.mac();
    }
}
