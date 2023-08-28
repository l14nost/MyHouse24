package lab.space.my_house_24.model.meterReading;

import lab.space.my_house_24.model.enums_response.EnumResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MeterReadingResponseForApartment(
        String house,
        String section,
        String apartment,
        String service,
        Double count,
        String unit,
        Long id,
        EnumResponse status,
        LocalDate date,
        String month
) {
}
