package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.UserStatus;
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

    @Column(length = 100,nullable = false)
    private String password;

    @Column(length = 100,nullable = false)
    private String email;

    @Column(length = 20)
    private String number;

    @Column(length = 20,nullable = false)
    private String viber;

    @Column(length = 20,nullable = false)
    private String telegram;

    @Enumerated(EnumType.STRING)
    @Column(length = 50,nullable = false, name = "status")
    private UserStatus userStatus;

    @Column(length = 1000)
    private String notes;

    @Column(length = 150)
    private String filename;

    @Column
    private Instant date;

    @Column(nullable = false)
    private Instant addDate;

    @Column(nullable = false)
    private Boolean duty;

    @Column(length = 250, name = "token")
    private String token;

    @Column(name = "token_usage")
    private Boolean tokenUsage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Token> tokenList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CashBox> cashBoxList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MastersApplication> applicationList = new ArrayList<>();

    @ManyToMany(mappedBy = "users",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Message> messageList = new ArrayList<>();

}
