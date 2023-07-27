package lab.space.my_house_24.model.user;

import lombok.Builder;

@Builder
public record UserMainPageRequest(
        Long id,
        int page,
        String sort
) {

}
