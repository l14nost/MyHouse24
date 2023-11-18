package lab.space.my_house_24.repository;


import lab.space.my_house_24.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor,Long>, JpaSpecificationExecutor<Floor> {
    List<Floor> findAllByHouse_Id(Long id);

}
