package lab.space.my_house_24.model.unit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record UnitResponse(
        Long id,
        String name
) {
}
