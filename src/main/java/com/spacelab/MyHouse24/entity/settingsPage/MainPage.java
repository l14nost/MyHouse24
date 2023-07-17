package com.spacelab.MyHouse24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 1000,nullable = false)
    private String description;

    @Column(length = 150,nullable = false)
    private String slide1;

    @Column(length = 150,nullable = false)
    private String slide2;

    @Column(length = 150,nullable = false)
    private String slide3;

    @Column(nullable = false)
    private boolean links;

    @OneToOne
    private Seo seo;

    @OneToMany(mappedBy = "mainPage")
    private List<Banner> bannerList = new ArrayList<>();

}
