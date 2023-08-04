package lab.space.my_house_24.model.staff;


import jakarta.validation.constraints.*;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;

@Builder
public record StaffUpdateRequest(
        @NotNull(message = "Must be specified")
        @Min(1)
        Long id,
        @NotBlank(message = "Must be specified")
        @Size(max = 25, message = "Must be no more than {max} symbols")
        String firstname,
        @NotBlank(message = "Must be specified")
        @Size(max = 25, message = "Must be no more than {max} symbols")
        String lastname,
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[0-9]*$", message = "{pattern.number.message}")
        @Size(max = 20, min = 10, message = "{size.between.message}" + " 10 && 20")
        String phone,
        @NotBlank(message = "Must be specified")
        @Size(max = 100, message = "Must be no more than {max} symbols")
        String email,
        @Size(max = 100, message = "Must be no more than {max} symbols")
        String password,
        @Size(max = 100, message = "Must be no more than {max} symbols")
        String confirmPassword,
        @NotNull(message = "Must be specified")
        JobTitle role,
        @NotNull(message = "Must be specified")
        UserStatus status
) {
}
