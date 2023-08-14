package lab.space.my_house_24.model.staff;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ForgotPassEmailRequest(
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message} " + " {max}")
        @Email(regexp = "^[A-Za-z0-9._%+-]+@.+\\.\\w{2,3}$", message = "{pattern.email.message}")
        String email
) {
}
