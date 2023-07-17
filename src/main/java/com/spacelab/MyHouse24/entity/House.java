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
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String image1;

    @Column(length = 150)
    private String image2;

    @Column(length = 150)
    private String image3;

    @Column(length = 150)
    private String image4;

    @Column(length = 150)
    private String image5;

    @OneToMany(mappedBy = "house")
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    private List<Section> sectionList = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    private List<Floor> floorList = new ArrayList<>();

    @ManyToMany(mappedBy = "houseList")
    private List<Staff> staffList = new ArrayList<>();


    @ManyToMany(mappedBy = "houseList")
    private List<Message> messageList = new ArrayList<>();


}
