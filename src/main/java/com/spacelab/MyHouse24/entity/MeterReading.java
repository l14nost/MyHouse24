package com.spacelab.MyHouse24.entity;

import com.spacelab.MyHouse24.enums.MeterReadingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meter_reading")
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MeterReadingStatus meterReadingStatus;

    @Column(nullable = false)
    private Long number;

    @Column(nullable = false)
    private double count;

    @Column(nullable = false)
    private Instant date;
    @ManyToOne
    private Apartment apartment;

    @ManyToMany
    @JoinTable(name = "meter_reading_service",
    joinColumns = {
            @JoinColumn(name = "meter_reading_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "service_id")
    })

    private List<Service> serviceList = new ArrayList<>();
}
