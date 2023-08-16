package lab.space.my_house_24.model.settingsPage.service;

import lab.space.my_house_24.model.settingsPage.banner.BannerResponse;
import lab.space.my_house_24.model.settingsPage.banner.BannerResponseForService;
import lombok.Builder;

import java.util.List;

@Builder
public record ServicePageResponse(
        String seoTitle,
        String seoDescription,
        String seoKeyWords,

        List<BannerResponseForService> bannerResponseList
) {


}
