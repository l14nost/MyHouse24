package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.settingsPage.ServicePage;
import lab.space.my_house_24.model.settingsPage.service.ServicePageRequest;
import lab.space.my_house_24.model.settingsPage.service.ServicePageResponse;

public interface ServicePageService {
    ServicePage findById(Long id);
    ServicePageResponse findByIdResponse(Long id);
    void update(ServicePageRequest servicePageRequest);

    void save(ServicePage build);
}
