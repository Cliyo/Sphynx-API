package com.pedro.sphynx.entities;

import com.pedro.sphynx.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.dtos.consumer.ConsumerDataInput;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="consumers")
@Entity(name="Consumer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consumer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ra;
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    public Consumer(ConsumerDataInput data, Group group){
        this.id = null;
        this.name = data.name();
        this.ra = data.ra();
        this.tag = data.tag();
        this.group = group;
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }

    public void actualizeData(ConsumerDataEditInput data) {
        if(data.tag() != null){
            this.tag = data.tag();
        }
        if(data.ra() != null){
            this.ra = data.ra();
        }
        if(data.name() != null){
            this.name = data.name();
        }
    }
}
