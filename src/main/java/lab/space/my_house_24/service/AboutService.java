package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.settingsPage.About;
import lab.space.my_house_24.model.settingsPage.about.AboutRequest;
import lab.space.my_house_24.model.settingsPage.about.AboutResponse;

public interface AboutService {

    AboutResponse findByIdResponse(Long id);
    About findById(Long id);
    void update(AboutRequest aboutRequest);
}
