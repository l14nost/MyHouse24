package lab.space.my_house_24.model.meterReading;

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
        String status,
        LocalDate date,
        String month
) {
}
