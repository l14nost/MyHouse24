package lab.space.my_house_24.model.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24.model.unit.UnitResponse;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ServiceResponse(
        Long id,
        String name,
        Boolean isActiv,
        UnitResponse unit
) {
}
