package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor,Long> {
}
