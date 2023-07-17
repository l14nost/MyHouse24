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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany
    @JoinTable(name = "message_apartment", joinColumns = {
            @JoinColumn(name = "message_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "apartment_id")
    })
    List<Apartment> apartmentList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "message_house", joinColumns = {
            @JoinColumn(name = "message_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "house_id")
            })
    List<House> houseList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "message_floor", joinColumns = {
            @JoinColumn(name = "message_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "floor_id")
            })
    List<Floor> floorList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "message_section", joinColumns = {
            @JoinColumn(name = "message_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "section_id")
            })
    List<Section> sectionList = new ArrayList<>();
}
