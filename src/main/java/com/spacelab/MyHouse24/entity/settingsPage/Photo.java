package com.spacelab.MyHouse24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150,nullable = false)
    private String image;

    @Column(nullable = false)
    private boolean type;

    @ManyToOne
    private About about;



}
