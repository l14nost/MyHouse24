package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bill")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private BillStatus status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(nullable = false)
    private Boolean autoPayed;

    @Column(nullable = false)
    private BigDecimal payedCashBox;

    @Column(nullable = false)
    private BigDecimal historyPayedCashBox;

    @Column(nullable = false)
    private BigDecimal payed;

    @Column(nullable = false)
    private Boolean draft;

    @ManyToOne
    @JoinColumn(name = "bank_book_id", nullable = false)
    private BankBook bankBook;

    @Column(nullable = false)
    private LocalDateTime periodOf;

    @Column(nullable = false)
    private LocalDateTime periodTo;

    @ManyToOne
    private Rate rate;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    List<ServiceBill> serviceBillList = new ArrayList<>();

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", createAt=" + createAt +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", isActive=" + isActive +
                ", autoPayed=" + autoPayed +
                ", payedCashBox=" + payedCashBox +
                ", payed=" + payed +
                ", draft=" + draft +
                ", bankBook=" + bankBook.getId() +
                ", periodOf=" + periodOf +
                ", periodTo=" + periodTo +
                ", rate=" + rate.getId() +
                '}';
    }
}
