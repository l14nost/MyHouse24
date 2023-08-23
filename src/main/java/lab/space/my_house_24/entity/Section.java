package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "section")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @ManyToOne
    private House house;

    @ManyToMany(mappedBy = "sectionList",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Message> messageList = new ArrayList<>();

    @OneToMany(mappedBy = "section")
    private List<Apartment> apartmentList = new ArrayList<>();

}
