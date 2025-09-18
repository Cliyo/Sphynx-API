package com.pedro.sphynx.entities;

import com.pedro.sphynx.dtos.group.GroupDataInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Group(GroupDataInput data, User user){
        this.id = null;
        this.name = data.name();
        this.user = user;
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }
}
