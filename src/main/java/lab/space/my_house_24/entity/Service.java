package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    private Unit unit;

    @ManyToMany(mappedBy = "serviceList")
    private List<MeterReading> meterReadingList = new ArrayList<>();

    @OneToMany(mappedBy = "service")
    private List<ServiceBill> serviceBillList = new ArrayList<>();

}
