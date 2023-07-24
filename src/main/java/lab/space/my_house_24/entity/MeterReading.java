package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.MeterReadingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meter_reading")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MeterReadingStatus status;

    @Column(nullable = false)
    private Long number;

    @Column(nullable = false)
    private Long count;

    @Column(nullable = false)
    private Instant date;
    @ManyToOne
    private Apartment apartment;

    @ManyToMany
    @JoinTable(
            name = "meter_reading_service",
            joinColumns = @JoinColumn(name = "meter_reading_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> serviceList = new ArrayList<>();

}
