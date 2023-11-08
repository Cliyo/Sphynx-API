package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInputDTO;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Consumer(ConsumerDataInputDTO data){
        this.ra = data.ra();
        this.tag = data.tag();
    }

    public void actualizeData(ConsumerDataEditInputDTO data) {
        if(data.tag() != null){
            this.tag = data.tag();
        }
    }
}
