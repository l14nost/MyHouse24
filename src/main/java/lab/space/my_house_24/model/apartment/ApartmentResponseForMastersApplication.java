package lab.space.my_house_24.model.apartment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.user.UserResponseForMastersApplication;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApartmentResponseForMastersApplication(
        Long id,

        String name,

        Long houseId,

        String house,

        String section,

        String floor,

        UserResponseForMastersApplication owner
) {
}
