package com.spacelab.MyHouse24.entity;

import com.spacelab.MyHouse24.enums.Role;
import com.spacelab.MyHouse24.enums.StaffStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String email;

    @Column(length = 25,nullable = false)
    private String firstname;

    @Column(length = 25,nullable = false)
    private String lastname;

    @Column(length = 55,nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private StaffStatus status;

    @Column(length = 50,nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "staffList")
    private List<SecurityLevel> securityLevelList = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    private List<Statement> statementList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "staff_house", joinColumns = {
            @JoinColumn(name = "staff_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "house_id")
            })
    private List<House> houseList = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    private List<Application> applicationList = new ArrayList<>();
}
