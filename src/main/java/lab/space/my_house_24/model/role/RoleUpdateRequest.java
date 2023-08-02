package lab.space.my_house_24.model.role;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RoleUpdateRequest(

        @Valid
        @NotEmpty(message = "Must be specified")
        PageResponse manager,
        @Valid
        @NotEmpty(message = "Must be specified")
        PageResponse accountant,
        @Valid
        @NotEmpty(message = "Must be specified")
        PageResponse electrician,
        @Valid
        @NotEmpty(message = "Must be specified")
        PageResponse plumber
) {
}
