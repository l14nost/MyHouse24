package lab.space.my_house_24.mapper.settingsPage;

import lab.space.my_house_24.entity.settingsPage.Banner;
import lab.space.my_house_24.model.settingsPage.banner.BannerResponse;

public class BannerMapper {

    public static BannerResponse entityToDtoForMainPage(Banner banner){
        return BannerResponse.builder()
                .block(banner.getImage())
                .title(banner.getName())
                .description(banner.getDescription())
                .build();
    }
}
