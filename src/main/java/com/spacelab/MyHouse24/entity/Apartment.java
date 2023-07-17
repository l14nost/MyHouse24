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
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int area;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "apartmentList")
    private List<Message> messageList = new ArrayList<>();

    @ManyToMany(mappedBy = "apartmentList")
    private List<Application> applicationList = new ArrayList<>();

    @OneToMany(mappedBy = "apartment")
    private List<Receipt> receipt = new ArrayList<>();




}
