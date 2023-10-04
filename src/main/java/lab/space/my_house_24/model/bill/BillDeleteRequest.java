package lab.space.my_house_24.model.bill;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BillDeleteRequest(

        @NotNull(message = "{not.blank.message}")
        @Min(value = 1, message = "{price.min}" + " {value}")
        Long id,

        @NotNull(message = "not")
        @AssertTrue(message = "assert")
        Boolean value
) {
}
