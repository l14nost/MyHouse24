package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.Role;
import lab.space.my_house_24.enums.UserStatus;
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
    @Column(name = "status",length = 50,nullable = false)
    private UserStatus staffStatus;

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
    private List<MastersApplication> applicationList = new ArrayList<>();
}
