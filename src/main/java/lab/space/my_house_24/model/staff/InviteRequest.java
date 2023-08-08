package lab.space.my_house_24.model.staff;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record InviteRequest(
        @NotBlank
        String token,
        String email,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message} " + " {max}")
        @Size(min = 4,message = "{size.greater.message} " + "{min}")
        String password,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message} " + " {max}")
        @Size(min = 4,message = "{size.greater.message} " + "{min}")
        String confirmPassword
) {
}
