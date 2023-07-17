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
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @ManyToOne
    private House house;

    @ManyToMany(mappedBy = "sectionList")
    private List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "section")
    private List<Apartment> apartmentList = new ArrayList<>();
}
