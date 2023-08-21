package lab.space.my_house_24.model.masters_application;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record MastersApplicationUpdateRequest(
        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long id,

        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}" + " {max}")
        String description,

        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}" + " {max}")
        String comment,

        @NotNull(message = "{not.blank.message}")
        Master master,

        @NotNull(message = "{not.blank.message}")
        MastersApplicationStatus mastersApplicationStatus,

        @NotNull(message = "{not.blank.message}")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate date,

        @NotNull(message = "{not.blank.message}")
        @JsonDeserialize(using = LocalTimeDeserializer.class)
        LocalTime time,

        @Min(1)
        Long staffId,

        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long userId,

        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long apartmentId

) {
}
