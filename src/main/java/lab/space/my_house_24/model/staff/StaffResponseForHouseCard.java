package lab.space.my_house_24.model.staff;

import lombok.Builder;

@Builder
public record StaffResponseForHouseCard(
        Long id,

        String fullName,

        String role
) {
}
