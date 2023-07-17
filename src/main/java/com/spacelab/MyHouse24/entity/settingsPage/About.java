package com.spacelab.MyHouse24.entity.settingsPage;

import com.spacelab.MyHouse24.entity.ServiceReceipt;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,nullable = false)
    private String title;

    @Column(length = 1000,nullable = false)
    private String description;

    @Column(length = 150,nullable = false)
    private String imageDirector;

    @Column(length = 100,nullable = false)
    private String titleAdd;

    @Column(length = 1000,nullable = false)
    private String descriptionAdd;

    @OneToOne
    private Seo seo;

    @OneToMany(mappedBy = "about")
    private List<Photo> photoList = new ArrayList<>();

    @OneToMany(mappedBy = "about")
    private List<Document> documentList = new ArrayList<>();

}
