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
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String description;

    @OneToMany(mappedBy = "rate")
    private List<Receipt> receiptList = new ArrayList<>();

    @OneToMany(mappedBy = "rate")
    private List<PriceRate> priceRateList = new ArrayList<>();
}
