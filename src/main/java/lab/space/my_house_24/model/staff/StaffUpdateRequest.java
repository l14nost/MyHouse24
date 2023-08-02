package lab.space.my_house_24.model.staff;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
        @NotBlank(message = "Must be specified")
        @Size(max = 100, message = "Must be no more than {max} symbols")
        String email,
        @NotBlank(message = "Must be specified")
        @Size(max = 100, message = "Must be no more than {max} symbols")
        String password,
        @NotNull(message = "Must be specified")
        JobTitle jobTitle,
        @NotNull(message = "Must be specified")
        UserStatus userStatus
) {
}
