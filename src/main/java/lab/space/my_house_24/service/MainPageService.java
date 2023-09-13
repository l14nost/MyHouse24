package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.settingsPage.MainPage;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageRequest;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageResponse;

public interface MainPageService {

    MainPageResponse findByIdResponse(Long l);
    MainPage findById(Long id);

    void update(MainPageRequest mainPageRequest);

    void save(MainPage build);
}
