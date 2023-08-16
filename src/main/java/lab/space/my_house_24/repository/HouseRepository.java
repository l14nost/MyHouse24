package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {
}
