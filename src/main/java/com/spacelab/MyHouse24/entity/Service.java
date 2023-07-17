package com.spacelab.MyHouse24.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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
    private List<ServiceReceipt> serviceReceiptList = new ArrayList<>();


}
