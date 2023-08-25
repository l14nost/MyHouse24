package lab.space.my_house_24.model.meterReading;

import com.fasterxml.jackson.annotation.JsonFormat;
import lab.space.my_house_24.enums.MeterReadingStatus;
import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MeterReadingResponseEdit(
        String number,
        HouseResponseForTable house,
        ApartmentResponseForTable apartment,
        SectionResponseForTable section,
        ServiceResponseForSelect service,
        MeterReadingStatus status,
        Double count,
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate date

) {
}
