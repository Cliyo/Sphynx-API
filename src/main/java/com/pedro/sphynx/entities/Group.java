package com.pedro.sphynx.entities;

import com.pedro.sphynx.dtos.group.GroupDataInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "permission_groups")
@Entity(name = "Group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    @ManyToMany
    @JoinTable(name = "locals_groups", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "local_id"))
    private Set<Local> locals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToMany
    @JoinTable(name = "week_days_groups", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "week_day_id"))
    private Set<WeekDay> weekDays;

    public Group(GroupDataInput data, User user){
        this.id = null;
        this.name = data.name();
        this.user = user;
        this.unit = user.getUnit();
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
