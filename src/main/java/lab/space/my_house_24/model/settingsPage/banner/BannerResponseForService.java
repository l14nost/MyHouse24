package lab.space.my_house_24.model.settingsPage.banner;

import lombok.Builder;

@Builder
public record BannerResponseForService(
        Long id,
        String image,
        String title,
        String description
) {
}
