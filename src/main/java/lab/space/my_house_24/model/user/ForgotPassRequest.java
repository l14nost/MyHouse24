package lab.space.my_house_24.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ForgotPassRequest(
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message} " + 100)
        @Size(min = 4,message = "{size.greater.message} " + 4)
        String password,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message} " +100)
        @Size(min = 4,message = "{size.greater.message} " + 4)
        String confirmPassword
){
}
