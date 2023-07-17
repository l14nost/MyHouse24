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
public class ServicePage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Seo seo;

    @OneToMany(mappedBy = "servicePage")
    List<Banner> bannerList = new ArrayList<>();

}
