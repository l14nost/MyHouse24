package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.ServiceBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceBillRepository extends JpaRepository<ServiceBill, Long> {

    List<ServiceBill> getAllByBillId(Long billId);
}
