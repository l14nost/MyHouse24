package lab.space.my_house_24.model.settingsPage.mainPage;

import lab.space.my_house_24.model.settingsPage.banner.BannerResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MainPageResponse(
        String slide1,
        String slide2,
        String slide3,
        String title,
        String description,
        Boolean showLink,

        List<BannerResponse> blocks,
        String seoTitle,
        String seoDescription,
        String seoKeyWords

        ) {
}
