package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "cash_box")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(length = 1000, nullable = false)
    private String comment;

    @Column(nullable = false)
    private Boolean type;

    @Column(nullable = false)
    private Boolean held;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private Long sum;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private Article articles;

    @ManyToOne
    private User user;

}
