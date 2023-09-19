package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.Page;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "security_level")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SecurityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Page page;

    @ManyToMany(mappedBy = "securityLevelList")
    private List<Role> role;

    @Override
    public String toString() {
        return "SecurityLevel{" +
                "id=" + id +
                ", page=" + page +
                ", roles size=" + role.size() +
                '}';
    }
}
