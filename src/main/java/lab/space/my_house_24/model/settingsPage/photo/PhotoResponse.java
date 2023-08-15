package lab.space.my_house_24.model.settingsPage.photo;

import lombok.Builder;

@Builder
public record PhotoResponse(
        Long id,
        String image
) {
}
