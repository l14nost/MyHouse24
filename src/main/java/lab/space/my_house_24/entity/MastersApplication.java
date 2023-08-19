package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "masters_application")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MastersApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Instant createAt;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(length = 1000)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Master master;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private MastersApplicationStatus mastersApplicationStatus;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private User user;

    @ManyToOne
    private Apartment apartment;

}
