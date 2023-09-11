package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_box")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CashBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(nullable = false)
    private Boolean draft;

    @Column(nullable = false)
    private Boolean type;

    private Boolean isActive;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 1000, nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article articles;

    @ManyToOne
    private BankBook bankBook;

}
