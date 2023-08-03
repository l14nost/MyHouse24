package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.enums.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByJobTitle(JobTitle jobTitle);
}
