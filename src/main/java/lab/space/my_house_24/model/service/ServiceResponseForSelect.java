package lab.space.my_house_24.model.service;

import lombok.Builder;

@Builder
public record ServiceResponseForSelect(
        Long id,
        String name
) {
}
