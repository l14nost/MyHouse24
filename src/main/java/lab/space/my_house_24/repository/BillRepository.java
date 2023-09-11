package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.enums.BillStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>, JpaSpecificationExecutor<Bill> {
    boolean existsByNumber(String number);

    List<Bill> findAllByStatus(BillStatus status);

}
