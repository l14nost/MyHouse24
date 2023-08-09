package lab.space.my_house_24.model.enums_response;

import lombok.Builder;

@Builder
public record EnumResponse(
        String name,
        String value
) {
}
