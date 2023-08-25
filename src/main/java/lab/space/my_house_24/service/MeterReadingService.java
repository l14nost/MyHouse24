package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.MeterReading;
import lab.space.my_house_24.model.meterReading.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MeterReadingService {
    Page<MeterReadingResponseForMain> findAllForMain(MeterReadingRequestForMainPage meterReadingRequestForMainPage);
    Page<MeterReadingResponseForApartment> findAllForApartment(MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage);

    void save(MeterReadingRequestForAdd meterReadingRequestForAdd);
    void update(MeterReadingRequestForEdit meterReadingRequestForEdit,Long id);

    Long count();

    MeterReadingResponseEdit findByIdEdit(Long id);
    MeterReading findById(Long id);

    void delete(Long id);
}
