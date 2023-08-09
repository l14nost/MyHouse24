package lab.space.my_house_24.model.section;

import lombok.Builder;

@Builder
public record SectionResponseForTable(
        Long id,
        String name
) {
}
