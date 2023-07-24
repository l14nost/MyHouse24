package com.spacelab.MyHouse24.entity;

import com.spacelab.MyHouse24.enums.ReceiptStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    private ReceiptStatus status;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private boolean draft;

    @ManyToOne
    private Apartment apartment;

    @ManyToOne
    private Rate rate;

    @OneToMany(mappedBy = "receipt")
    List<ServiceReceipt> serviceReceiptList = new ArrayList<>();
}
