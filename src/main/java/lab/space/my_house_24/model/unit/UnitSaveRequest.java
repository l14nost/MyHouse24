package lab.space.my_house_24.model.unit;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record UnitSaveRequest(
        @NotNull(message = "{not.blank.message}")
        List<UnitRequest> unitRequestList
) {
}
