package lab.space.my_house_24.model.meterReading;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lab.space.my_house_24.enums.MeterReadingStatus;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record MeterReadingRequestForAdd(
        @NotNull(message = "{not.blank.message}")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate date,
        @NotNull(message = "{not.blank.message}")
        Long house,
        @NotNull(message = "{not.blank.message}")
        Long section,
        @NotNull(message = "{not.blank.message}")
        Long apartment,
        @NotNull(message = "{not.blank.message}")
        Long service,
        @NotNull(message = "{not.blank.message}")
        MeterReadingStatus status,
        @NotNull(message = "{not.blank.message}")
        Double count
) {
}
