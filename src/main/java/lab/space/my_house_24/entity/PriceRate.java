package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Table(name = "price_rate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PriceRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "rate_id", nullable = false)
    private Rate rate;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Override
    public String toString() {
        return "PriceRate{" +
                "id=" + id +
                ", price=" + price +
                ", rate=" + rate.getId() +
                ", service=" + service.getId() +
                '}';
    }
}
