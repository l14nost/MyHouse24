package lab.space.my_house_24.model.user;

import lombok.Builder;

@Builder
public record UserMainPageRequest(
        Integer page,
        Integer size
) {

}
