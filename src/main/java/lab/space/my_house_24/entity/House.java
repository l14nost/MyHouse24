package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "house")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 150)
    private String address;

    @Column(length = 150)
    private String image1;

    @Column(length = 150)
    private String image2;

    @Column(length = 150)
    private String image3;

    @Column(length = 150)
    private String image4;

    @Column(length = 150)
    private String image5;

    @OneToMany(mappedBy = "house")
    private List<Apartment> apartmentList = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sectionList = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Floor> floorList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        House house = (House) o;

        if (!Objects.equals(id, house.id)) return false;
        if (!Objects.equals(name, house.name)) return false;
        if (!Objects.equals(address, house.address)) return false;
        if (!Objects.equals(image1, house.image1)) return false;
        if (!Objects.equals(image2, house.image2)) return false;
        if (!Objects.equals(image3, house.image3)) return false;
        if (!Objects.equals(image4, house.image4)) return false;
        return Objects.equals(image5, house.image5);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (image1 != null ? image1.hashCode() : 0);
        result = 31 * result + (image2 != null ? image2.hashCode() : 0);
        result = 31 * result + (image3 != null ? image3.hashCode() : 0);
        result = 31 * result + (image4 != null ? image4.hashCode() : 0);
        result = 31 * result + (image5 != null ? image5.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "houseList")
    private Set<Staff> staffList = new HashSet<>();

    public void removeStaff(Staff staff){
        staffList.remove(staff);
        staff.getHouseList().remove(this);
    }
    public void addStaff(Staff staff){
        staffList.add(staff);
        staff.getHouseList().add(this);
    }

}
