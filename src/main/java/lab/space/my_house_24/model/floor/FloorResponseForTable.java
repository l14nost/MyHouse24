package lab.space.my_house_24.model.floor;

import lombok.Builder;

@Builder
public record FloorResponseForTable(
        Long id,
        String name
) {
}
