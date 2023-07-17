package com.spacelab.MyHouse24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 1000,nullable = false)
    private String description;

    @Column(length = 200,nullable = false)
    private String keyWords;

}
