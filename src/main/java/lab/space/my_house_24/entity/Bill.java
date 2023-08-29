package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private BillStatus status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private Boolean draft;

    @Column(nullable = false)
    private Instant createAt;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private Rate rate;

    @OneToMany(mappedBy = "bill")
    List<ServiceBill> serviceBillList = new ArrayList<>();

}
