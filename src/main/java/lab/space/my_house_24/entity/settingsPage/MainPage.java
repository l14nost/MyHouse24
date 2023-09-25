package lab.space.my_house_24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "main_page")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 150, nullable = false)
    private String slide1;

    @Column(length = 150, nullable = false)
    private String slide2;

    @Column(length = 150, nullable = false)
    private String slide3;

    @Column(nullable = false)
    private Boolean links;

    @OneToOne(cascade = CascadeType.ALL)
    private Seo seo;

    @OneToMany(mappedBy = "mainPage", cascade = CascadeType.ALL)
    private List<Banner> bannerList = new ArrayList<>();


    public void addBanner(Banner banner){
        bannerList.add(banner);
        banner.setMainPage(this);
    }
}
