package lab.space.my_house_24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_page")
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
