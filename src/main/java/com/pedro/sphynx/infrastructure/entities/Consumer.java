package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInputDTO;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    private String tag;
    private LocalDateTime dtcreate;
    private LocalDateTime dtupdate;

    public void actualizeData(ConsumerDataEditInputDTO data) {
        if(data.tag() != null){
            this.tag = data.tag();
            this.dtupdate = LocalDateTime.now();
        }
    }
}
