package lab.space.my_house_24.model.requites;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequisitesRequest(
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 100, message = "{size.less.message}"+" 100")
        String name,
        @NotBlank(message = "{not.blank.message}")
        @Size(max = 1000, message = "{size.less.message}"+" 1000")
        String info
) {
}
