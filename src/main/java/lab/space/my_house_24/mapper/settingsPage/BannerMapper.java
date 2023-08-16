package lab.space.my_house_24.mapper.settingsPage;

import lab.space.my_house_24.entity.settingsPage.Banner;
import lab.space.my_house_24.model.settingsPage.banner.BannerResponse;
import lab.space.my_house_24.model.settingsPage.banner.BannerResponseForService;

public class BannerMapper {

    public static BannerResponse entityToDtoForMainPage(Banner banner){
        return BannerResponse.builder()
                .block(banner.getImage())
                .title(banner.getName())
                .description(banner.getDescription())
                .build();
    }
    public static BannerResponseForService entityToDtoForServicePage(Banner banner){
        return BannerResponseForService.builder()
                .id(banner.getId())
                .image(banner.getImage())
                .title(banner.getName())
                .description(banner.getDescription())
                .build();
    }
}
