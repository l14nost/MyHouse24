package lab.space.my_house_24.model.staff;

import lombok.Builder;

@Builder
public record StaffResponseForHeader(
        Long id,
        String fullName,
        String email
) {
}
