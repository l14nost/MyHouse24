package lab.space.my_house_24.model.role;

import lombok.Builder;

@Builder
public record RoleResponse(
        PageResponse manager,
        PageResponse accountant,
        PageResponse electrician,
        PageResponse plumber
) {
}
