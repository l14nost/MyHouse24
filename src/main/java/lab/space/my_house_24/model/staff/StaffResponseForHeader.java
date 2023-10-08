package lab.space.my_house_24.model.staff;

import lombok.Builder;

import java.util.List;

@Builder
public record StaffResponseForHeader(
        Long id,
        String fullName,
        String email,

        List<String> permission
) {
}
