package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.UserStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
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

    @Column(length = 25,nullable = false)
    private String surname;

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
    @Column(length = 50,nullable = false, name = "status")
    private UserStatus userStatus;

    @Column(length = 1000,nullable = false)
    private String notes;

    @Column(length = 150,nullable = false)
    private String filename;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private Instant addDate;

    @Column(nullable = false)
    private Boolean duty;

    @OneToMany(mappedBy = "user")
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Statement> statementList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MastersApplication> applicationList = new ArrayList<>();

}
