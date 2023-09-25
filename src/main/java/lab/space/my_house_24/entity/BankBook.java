package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.BankBookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_book")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BankBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private BankBookStatus bankBookStatus;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})

    private Apartment apartment;

    @OneToMany(mappedBy = "bankBook", cascade = CascadeType.ALL)
    private List<Bill> bill = new ArrayList<>();

    @OneToMany(mappedBy = "bankBook")
    private List<CashBox> cashBoxes = new ArrayList<>();

    @Override
    public String toString() {
        return "BankBook{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", totalPrice=" + totalPrice +
                ", bankBookStatus=" + bankBookStatus +
                ", apartment=" + apartment.getId() +
                '}';
    }
}
