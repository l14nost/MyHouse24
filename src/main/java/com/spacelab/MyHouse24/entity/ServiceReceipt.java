package com.spacelab.MyHouse24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double count;

    @Column(nullable = false)
    private  double totalPrice;

    @ManyToOne
    private Receipt receipt;

    @ManyToOne
    private Service service;

}
