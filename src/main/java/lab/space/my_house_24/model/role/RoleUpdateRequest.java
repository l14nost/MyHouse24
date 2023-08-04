package lab.space.my_house_24.model.role;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoleUpdateRequest(

        @NotNull(message = "{not.blank.message}")
        PageResponse manager,
        @NotNull(message = "{not.blank.message}")
        PageResponse accountant,
        @NotNull(message = "{not.blank.message}")
        PageResponse electrician,
        @NotNull(message = "{not.blank.message}")
        PageResponse plumber
) {
}
