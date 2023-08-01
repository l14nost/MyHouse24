package lab.space.my_house_24.model.user;

import lab.space.my_house_24.enums.UserStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        Long id,
        String fullName,

        String number,

        String email,

        List<String> houseNames,

        List<Long> houseIds,

        List<Integer> apartmentNumbers,

        List<Long> apartmentIds,

        UserStatus status,

        boolean duty,

        String filename

) {
}
