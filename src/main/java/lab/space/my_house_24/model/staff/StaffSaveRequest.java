package lab.space.my_house_24.model.staff;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24.enums.Role;
import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;

@Builder
public record StaffSaveRequest(
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
        Role role,
        @NotNull(message = "Must be specified")
        UserStatus userStatus
) {
}
