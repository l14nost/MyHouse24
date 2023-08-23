package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "message")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String descriptionStyle;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "send_date")
    private Instant sendDate;

    @ManyToOne
    private Staff staff;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "message_apartment",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "apartment_id")
    )
    List<Apartment> apartmentList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "message_house",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    List<House> houseList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "message_floor",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "floor_id")
    )
    List<Floor> floorList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "message_section",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    List<Section> sectionList = new ArrayList<>();


    public void addHouse(House house){
        houseList.add(house);
        house.getMessageList().add(this);
    }

    public void addSection(Section section){
        sectionList.add(section);
        section.getMessageList().add(this);
    }

    public void addFloor(Floor floor){
        floorList.add(floor);
        floor.getMessageList().add(this);
    }

    public void addApartment(Apartment apartment){
        apartmentList.add(apartment);
        apartment.getMessageList().add(this);
    }
}
