package lab.space.my_house_24.model.meterReading;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.service.ServiceResponse;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MeterReadingResponseForBill (
        Long id,

        String number,

        String status,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate date,

        String month,

        String house,

        String section,

        String apartment,

        ServiceResponse service,

        Double count

){
}
