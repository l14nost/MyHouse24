package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate,Long>, JpaSpecificationExecutor<Rate> {
    boolean existsByName(String name);
}
