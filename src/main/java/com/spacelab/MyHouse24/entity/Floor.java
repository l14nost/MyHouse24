package com.spacelab.MyHouse24.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @ManyToOne
    private House house;

    @OneToMany(mappedBy = "floor")
    private List<Apartment> apartmentList = new ArrayList<>();

    @ManyToMany(mappedBy = "floorList")
    private List<Message> messageList = new ArrayList<>();
}
