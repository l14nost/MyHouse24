package lab.space.my_house_24.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserInviteRequest(
        @NotBlank(message = "{not.blank.message}")
        @Pattern(regexp = "^[0-9]*$", message = "{pattern.number.message}")
        @Size(max = 20, min = 10, message = "{size.between.message}"+" 10 && 20")
        String number,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        @Pattern(message = "{pattern.email.message}", regexp = "^((([0-9A-Za-z]{1}[-0-9A-z\\.]{0,30}[0-9A-Za-z]?))@([-A-Za-z]{1,}\\.){1,}[-A-Za-z]{2,})$")
        String email
) {
}
