package lab.space.my_house_24.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.validator.annotation.FileExtension;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
public record UserEditResponse(
        Long id,

        String filename,


        String lastname,


        String firstname,


        String surname,

        String number,


        String telegram,


        String viber,
        String email,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        UserStatus status,

        String notes



){
}
