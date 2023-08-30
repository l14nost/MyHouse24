package lab.space.my_house_24.repository;

import lab.space.my_house_24.entity.MeterReading;
import lab.space.my_house_24.entity.settingsPage.About;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Long>, JpaSpecificationExecutor<MeterReading> {
    List<MeterReading> findAllByApartment_IdAndService_id(Long idApartment, Long idService);
    List<MeterReading> findAllByApartment_Id(Long idApartment);
}
