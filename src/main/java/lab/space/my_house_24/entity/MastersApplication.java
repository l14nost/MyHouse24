package lab.space.my_house_24.entity;

import jakarta.persistence.*;

import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "masters_application")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MastersApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 1000, nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    private Master master;

    @Column(length = 50, nullable = false)
    private MastersApplicationStatus mastersApplicationStatus;

    @Column(nullable = false)
    private Instant dateTime;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "masters_application_apartment",
            joinColumns = @JoinColumn(name = "masters_application_id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id")
    )
    private List<Apartment> apartmentList = new ArrayList<>();

}
