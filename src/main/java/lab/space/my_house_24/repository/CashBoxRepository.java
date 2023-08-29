package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.CashBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashBoxRepository extends JpaRepository<CashBox, Long>, JpaSpecificationExecutor<CashBox> {
    List<CashBox> findAllByType(Boolean type);
}
