package com.spacelab.MyHouse24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String name;

    @Column(length = 1000,nullable = false)
    private String description;

    @Column(length = 150,nullable = false)
    private String image;

    @ManyToOne
    private ServicePage servicePage;

    @ManyToOne
    private MainPage mainPage;

}
