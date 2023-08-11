package lab.space.my_house_24.model.staff;


import jakarta.validation.constraints.*;
import lab.space.my_house_24.enums.JobTitle;
import lombok.Builder;

@Builder
public record StaffSaveRequest(
        @Null
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$", message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}" + " {max}")
        String firstname,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[А-ЯЄІЇҐЁA-Z][а-яєіїґёa-z]*$", message = "{pattern.name.message}")
        @Size(max = 25, message = "{size.less.message}" + " {max}")
        String lastname,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[0-9]*$", message = "{pattern.number.message}")
        @Size(max = 20, min = 10, message = "{size.between.message}" + " {min} && {max}")
        String phone,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message} " + " {max}")
        @Email(regexp = "^[A-Za-z0-9._%+-]+@.+\\.\\w{2,3}$", message = "{pattern.email.message}")
        String email,
        @NotNull(message = "{not.blank.message}")
        JobTitle role
) {
}
