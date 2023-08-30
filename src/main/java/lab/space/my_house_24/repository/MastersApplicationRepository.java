package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.MastersApplication;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MastersApplicationRepository extends JpaRepository<MastersApplication, Long>, JpaSpecificationExecutor<MastersApplication> {
    Long countByMastersApplicationStatus(MastersApplicationStatus mastersApplicationStatus);
}
