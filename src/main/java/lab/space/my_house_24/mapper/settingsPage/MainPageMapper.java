package lab.space.my_house_24.mapper.settingsPage;

import lab.space.my_house_24.entity.settingsPage.MainPage;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageResponse;

import java.util.ArrayList;
import java.util.List;

public class MainPageMapper {

    public static MainPageResponse entityToResponse(MainPage mainPage){
//        List<String> blockImg = new ArrayList<>();
//        List<String> blockTitle = new ArrayList<>();
//        List<String> blockDescription = new ArrayList<>();
//        for (int i = 0; i<mainPage.getBannerList().size(); i++){
//            blockImg.add(mainPage.getBannerList().get(i).getImage());
//            blockTitle.add(mainPage.getBannerList().get(i).getName());
//            blockDescription.add(mainPage.getBannerList().get(i).getDescription());
//        }
        return MainPageResponse.builder()
                .blocks(mainPage.getBannerList().stream().map(BannerMapper::entityToDtoForMainPage).toList())
                .seoDescription(mainPage.getSeo().getDescription())
                .seoTitle(mainPage.getSeo().getTitle())
                .seoKeyWords(mainPage.getSeo().getKeyWords())
                .showLink(mainPage.getLinks())
                .title(mainPage.getTitle())
                .description(mainPage.getDescription())
                .slide1(mainPage.getSlide1())
                .slide2(mainPage.getSlide2())
                .slide3(mainPage.getSlide3())
                .build();
    }
}
