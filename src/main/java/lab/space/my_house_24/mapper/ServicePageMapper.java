package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.settingsPage.ServicePage;
import lab.space.my_house_24.mapper.settingsPage.BannerMapper;
import lab.space.my_house_24.model.settingsPage.service.ServicePageResponse;

public class ServicePageMapper {

    public static ServicePageResponse entityToResponseDto(ServicePage servicePage) {
        return ServicePageResponse.builder()
                .seoKeyWords(servicePage.getSeo().getKeyWords())
                .seoTitle(servicePage.getSeo().getTitle())
                .seoDescription(servicePage.getSeo().getDescription())
                .bannerResponseList(servicePage.getBannerList().stream().map(BannerMapper::entityToDtoForServicePage).toList())
                .build();
    }
}
