package com.pedro.sphynx.entities;

import com.pedro.sphynx.dtos.local.LocalDataEditInput;
import com.pedro.sphynx.dtos.local.LocalDataInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "locals")
@Entity(name = "Local")
@Getter
@Setter
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
    @JoinTable(name = "locals_groups", joinColumns = @JoinColumn(name = "local_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public Local(LocalDataInput data, User user){
        this.name = data.name();
        this.mac = data.mac();
        this.dtcreate = LocalDateTime.now();
        this.user = user;
        this.unit = user.getUnit();
        this.dtupdate = null;
    }

    public void updateLocal(LocalDataEditInput data){
        this.name = data.name();
    }
}
