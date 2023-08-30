package lab.space.my_house_24.model.meterReading;

import lab.space.my_house_24.enums.MeterReadingStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MeterReadingRequestForApartmentPage(
        Integer page,
        Long id,
        String status,
        String date,
        Long house,
        Long section,
        Integer apartment,
        Long service,
        Long idApartment,
        Long idService



) {
}
