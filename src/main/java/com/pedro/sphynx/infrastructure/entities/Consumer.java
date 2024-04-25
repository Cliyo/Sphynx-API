package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInput;
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

    private String ra;
    private String tag;
    private int permission;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    public void actualizeData(ConsumerDataEditInput data) {
        if(data.tag() != null){
            this.tag = data.tag();
            this.dtupdate = LocalDateTime.now();
        }
    }
}
