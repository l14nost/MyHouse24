package lab.space.my_house_24.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseForMastersApplication(
        Long id,
        String fullName,

        String phone

) {
}
