package com.spacelab.MyHouse24.entity;

import com.spacelab.MyHouse24.enums.ApplicationStatus;
import com.spacelab.MyHouse24.enums.Master;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 1000, nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Master master;

    @Column(length = 50, nullable = false)
    private ApplicationStatus applicationStatus;

    @Column(nullable = false)
    private Instant dateTime;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(name = "application_apartment", joinColumns = {
            @JoinColumn(name = "application_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "apartment_id")
            })
    private List<Apartment> apartmentList = new ArrayList<>();
}
