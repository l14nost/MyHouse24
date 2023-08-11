package lab.space.my_house_24.model.settingsPage.banner;

import lombok.Builder;

@Builder
public record BannerResponse(
        String block,
        String title,
        String description
) {
}
