package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Long>, JpaSpecificationExecutor<Apartment> {
}
