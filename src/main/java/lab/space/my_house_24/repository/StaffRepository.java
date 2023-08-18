package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>, JpaSpecificationExecutor<Staff> {
    Optional<Staff> findByEmail(String email);

    boolean existsByEmail(String email);
}
