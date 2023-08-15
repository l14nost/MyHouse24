package lab.space.my_house_24.model.settingsPage.document;

import lombok.Builder;

@Builder
public record DocumentResponse(
        Long id,
        String name,
        String document
) {
}
