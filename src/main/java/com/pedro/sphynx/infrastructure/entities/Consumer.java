package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="consumers")
@Entity(name="Consumer")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consumer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ra;
    private String tag;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    public Consumer(ConsumerDataInput data){
        this.id = null;
        this.name = data.name();
        this.ra = data.ra();
        this.tag = data.tag();
        this.dtcreate = LocalDateTime.now();
        this.dtupdate = null;
    }

    public void actualizeData(ConsumerDataEditInput data) {
        if(data.tag() != null){
            this.tag = data.tag();
            this.dtupdate = LocalDateTime.now();
        }
    }
}
