package lab.space.my_house_24.model.user;

import lombok.Builder;

@Builder
public record UserResponseForHeader(
        Long id,
        String fullName
) {
}
