package lab.space.my_house_24.model.service;

import lombok.Builder;

@Builder
public record ServiceRequest(
        Long id,
        String name,
        Boolean isActiv,
        Long unitId
) {
}
