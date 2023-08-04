package lab.space.my_house_24.model.role;

import lombok.Builder;

@Builder
public record RoleSimpleResponse(
        Long id,
        String jobTitle
) {
}
