package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.enums.BankBookStatus;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    private BankBookStatus status;

    @OneToOne
    private Apartment apartment;
}
