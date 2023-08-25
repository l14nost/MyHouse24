package lab.space.my_house_24.model.meterReading;

import lombok.Builder;

@Builder
public record MeterReadingRequestForMainPage(
        Integer page,
        Long house,
        Long section,
        Long apartment,
        Long service

) {
}
