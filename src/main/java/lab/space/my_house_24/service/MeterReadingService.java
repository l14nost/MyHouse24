package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.MeterReading;
import lab.space.my_house_24.model.meterReading.*;
import org.springframework.data.domain.Page;

public interface MeterReadingService {
    Page<MeterReadingResponseForMain> findAllForMain(MeterReadingRequestForMainPage meterReadingRequestForMainPage);
    Page<MeterReadingResponseForApartment> findAllForApartment(MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage);

    void save(MeterReadingRequestForAdd meterReadingRequestForAdd);
    void update(MeterReadingRequestForEdit meterReadingRequestForEdit,Long id);

    Long count();

    MeterReadingResponseEdit findByIdEdit(Long id);
    MeterReadingResponseEdit findByIdForApartmentAdd(Long id, Long idService);
    MeterReading findById(Long id);

    void delete(Long id);

    Page<MeterReadingResponseForBill> getAllMeterReadingResponseByRequest(MeterReadingRequestForBill request);
}
