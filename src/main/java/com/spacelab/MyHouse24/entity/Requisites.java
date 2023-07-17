package com.spacelab.MyHouse24.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Requisites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String name;

    @Column(length = 1000,nullable = false)
    private String info;

}
