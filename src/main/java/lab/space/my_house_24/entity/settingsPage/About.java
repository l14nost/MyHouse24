package lab.space.my_house_24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "about")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 150, nullable = false)
    private String imageDirector;

    @Column(length = 100, nullable = false)
    private String titleAdd;

    @Column(length = 1000, nullable = false)
    private String descriptionAdd;

    @OneToOne(cascade = CascadeType.ALL)
    private Seo seo;

    @OneToMany(mappedBy = "about", cascade = CascadeType.ALL)
    private List<Photo> photoList = new ArrayList<>();

    @OneToMany(mappedBy = "about", cascade = CascadeType.ALL)
    private List<Document> documentList = new ArrayList<>();

}
