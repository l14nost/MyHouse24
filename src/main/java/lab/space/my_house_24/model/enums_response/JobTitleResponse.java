package lab.space.my_house_24.model.enums_response;

import lombok.Builder;

@Builder
public record JobTitleResponse(
        String name,
        String value
) {
}
