package com.spacelab.MyHouse24.entity;

import com.spacelab.MyHouse24.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25,nullable = false)
    private String firstname;

    @Column(length = 25,nullable = false)
    private String lastname;

    @Column(length = 55,nullable = false)
    private String password;

    @Column(length = 100,nullable = false)
    private String email;

    @Column(length = 20,nullable = false)
    private String number;

    @Column(length = 20,nullable = false)
    private String viber;

    @Column(length = 20,nullable = false)
    private String telegram;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(length = 1000,nullable = false)
    private String notes;

    @Column(length = 150,nullable = false)
    private String filename;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private boolean duty;

    @OneToMany(mappedBy = "user")
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Statement> statementList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Application> applicationList = new ArrayList<>();

}
