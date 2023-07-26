package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.Page;
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
@Table(name = "security_level")
public class SecurityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50,nullable = false)
    private Page page;

    @ManyToMany
    @JoinTable(name = "security_level_staff", joinColumns = {
            @JoinColumn(name = "security_level_id")
    },
            inverseJoinColumns = {
                    @JoinColumn(name = "staff_id")
            })
    private List<Staff> staffList = new ArrayList<>();


}
