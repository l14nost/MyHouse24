package lab.space.my_house_24.model.meterReading;

import lombok.Builder;

@Builder
public record MeterReadingRequestForBill(
        Integer pageIndex,
        Long apartmentId,
        Long sectionId,
        Long houseId
) {
}
