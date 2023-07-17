package com.spacelab.MyHouse24.entity;

import com.spacelab.MyHouse24.enums.BankBookStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50,nullable = false)
    private BankBookStatus bankBookStatus;

    @OneToOne
    private Apartment apartment;
}
