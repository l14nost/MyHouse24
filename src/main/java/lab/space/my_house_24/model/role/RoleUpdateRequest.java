package lab.space.my_house_24.model.role;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoleUpdateRequest(

        @NotNull(message = "Must be specified")
        PageResponse manager,
        @NotNull(message = "Must be specified")
        PageResponse accountant,
        @NotNull(message = "Must be specified")
        PageResponse electrician,
        @NotNull(message = "Must be specified")
        PageResponse plumber
) {
}
