package com.pedro.sphynx.infrastructure.entities;

import com.pedro.sphynx.application.dtos.consumer.ConsumerDataEditInputDTO;
import com.pedro.sphynx.application.dtos.consumer.ConsumerDataInputDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="persons")
@Entity(name="Person")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ra;
    private String name;
    private String gender;

}
