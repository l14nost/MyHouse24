package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section,Long> {

    List<Section> findAllByHouse_Id(Long id);
}
