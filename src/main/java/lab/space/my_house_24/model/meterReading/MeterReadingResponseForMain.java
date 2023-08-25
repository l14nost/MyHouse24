package lab.space.my_house_24.model.meterReading;

import lombok.Builder;

@Builder
public record MeterReadingResponseForMain(
        Long id,
        String house,
        String section,
        String apartment,
        String service,
        Double count,
        String unit,
        Long idApartment,
        Long idService
) {
}
