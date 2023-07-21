package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_bill")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double count;

    @Column(nullable = false)
    private  double totalPrice;

    @ManyToOne
    private Bill bill;

    @ManyToOne
    private Service service;

}
