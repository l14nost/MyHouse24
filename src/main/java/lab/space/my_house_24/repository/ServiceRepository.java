package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    boolean existsByName(String name);

    List<Service> findAllByIsActiveOrderById(Boolean isActive);
}
