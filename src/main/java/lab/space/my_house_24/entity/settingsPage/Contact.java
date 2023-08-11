package lab.space.my_house_24.entity.settingsPage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contact")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 100, nullable = false)
    private String url;

    @Column(length = 1000, nullable = false)
    private String codeMap;

    @Column(length = 100, nullable = false)
    private String fullName;

    @Column(length = 80, nullable = false)
    private String location;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 20, nullable = false)
    private String number;

    @Column(length = 100, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Seo seo;

}
