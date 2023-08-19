package lab.space.my_house_24.model.masters_application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.apartment.ApartmentResponseForMastersApplication;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.StaffResponse;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MastersApplicationResponse(

        Long id,

        @JsonFormat(pattern = "dd.MM.yyyy - HH:mm")
        LocalDateTime createAt,

        String description,

        String comment,

        EnumResponse master,

        EnumResponse mastersApplicationStatus,

        @JsonFormat(pattern = "dd.MM.yyyy - HH:mm", timezone = "Europe/Kiev")
        LocalDateTime fullDate,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDateTime date,

        @JsonFormat(pattern = "HH:mm")
        LocalDateTime time,

        StaffResponse staff,

        ApartmentResponseForMastersApplication apartment

) {
}
