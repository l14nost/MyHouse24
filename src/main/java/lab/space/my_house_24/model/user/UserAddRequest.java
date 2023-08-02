package lab.space.my_house_24.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.validator.annotation.FileExtension;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Builder
public record UserAddRequest(
        @FileExtension(value = {"jpg", "png", "jpeg"}, message = "{file.message}"+"(.jpg,.png,.jpeg)")
        MultipartFile img,

        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$",message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}"+" 25")
        String lastname,

        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$",message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}"+" 25")
        String firstname,

        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$",message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}"+" 25")
        String surname,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[0-9]*$", message = "{pattern.number.message}")
        @Size(max = 20, min = 10, message = "{size.between.message}"+" 10 && 20")
        String number,

        @Pattern(regexp = "^[0-9]*$", message = "{pattern.number.message}")
        @Size(max = 20, min = 10, message = "{size.between.message}"+" 10 && 20")
        String telegram,

        @Pattern(regexp = "^[0-9]*$", message = "{pattern.number.message}")
        @Size(max = 20, min = 10, message = "{size.between.message}"+" 10 && 20")
        String viber,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        @Pattern(message = "{pattern.email.message}", regexp = "^((([0-9A-Za-z]{1}[-0-9A-z\\.]{0,30}[0-9A-Za-z]?))@([-A-Za-z]{1,}\\.){1,}[-A-Za-z]{2,})$")
        String email,
        @NotNull(message = "{not.blank.message}")
        LocalDate date,
        @NotNull(message = "{not.blank.message}")
        UserStatus status,
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String notes,

        @NotBlank(message = "{not.blank.message}")
        @Size(max = 20, min = 4 , message = "{size.between.message}"+" 4 && 20")
        String password,

        @NotBlank(message = "{not.blank.message}")
        @Size(max = 20, min = 4, message = "{size.between.message}"+" 4 && 20")
        String confirmPassword


){
}
