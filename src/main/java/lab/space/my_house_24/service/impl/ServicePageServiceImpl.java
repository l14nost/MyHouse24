package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.Banner;
import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.entity.settingsPage.ServicePage;
import lab.space.my_house_24.mapper.ServicePageMapper;
import lab.space.my_house_24.model.settingsPage.service.ServicePageRequest;
import lab.space.my_house_24.model.settingsPage.service.ServicePageResponse;
import lab.space.my_house_24.repository.ServicePageRepository;
import lab.space.my_house_24.service.BannerService;
import lab.space.my_house_24.service.ServicePageService;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicePageServiceImpl implements ServicePageService {
    private final ServicePageRepository servicePageRepository;
    private final BannerService bannerService;
    @Override
    public ServicePage findById(Long id) {
        log.info("Try to find service page by id: "+id);
        return servicePageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Service by id "+id+" is not found"));
    }

    @Override
    public ServicePageResponse findByIdResponse(Long id) {
        log.info("Try to find service page dto by id: "+id);
        return ServicePageMapper.entityToResponseDto(servicePageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Service by id "+id+" is not found")));
    }

    @Override
    public void update(ServicePageRequest servicePageRequest) {
        log.info("Try to update service page");
        ServicePage servicePage = findById(1L);
        if (!servicePageRequest.deleteBlockList().isEmpty()){
            for (int i = 0 ; i< servicePageRequest.deleteBlockList().size();i++){
                bannerService.deleteById(servicePageRequest.deleteBlockList().get(i));
            }
        }
        if (servicePageRequest.imageList()!=null) {
            for (int i = 0; i < servicePageRequest.imageList().size(); i++) {
                if (!servicePageRequest.imageList().get(i).isEmpty()) {
                    if (i < servicePage.getBannerList().size()) {
                        servicePage.getBannerList().get(i).setImage(FileHandler.saveFile(servicePageRequest.imageList().get(i)));
                        servicePage.getBannerList().get(i).setName(servicePageRequest.blockTitleList().get(i));
                        servicePage.getBannerList().get(i).setDescription(servicePageRequest.blockDescriptionList().get(i));
                    } else {
                        servicePage.getBannerList().add(Banner.builder().image(FileHandler.saveFile(servicePageRequest.imageList().get(i))).name(servicePageRequest.blockTitleList().get(i)).description(servicePageRequest.blockDescriptionList().get(i)).servicePage(servicePage).build());
                    }
                } else {
                    if (i < servicePage.getBannerList().size()) {
                        servicePage.getBannerList().get(i).setName(servicePageRequest.blockTitleList().get(i));
                        servicePage.getBannerList().get(i).setDescription(servicePageRequest.blockDescriptionList().get(i));
                    } else {
                        servicePage.getBannerList().add(Banner.builder().image("").name(servicePageRequest.blockTitleList().get(i)).description(servicePageRequest.blockDescriptionList().get(i)).servicePage(servicePage).build());
                    }
                }
            }
        }

        servicePage.getSeo().setTitle(servicePageRequest.seoTitle());
        servicePage.getSeo().setKeyWords(servicePageRequest.seoKeyWords());
        servicePage.getSeo().setDescription(servicePageRequest.seoDescription());
        servicePageRepository.save(servicePage);
        log.info("Service page was update");
    }

    @Override
    public void save(ServicePage build) {
        log.info("Try to save new service page");
        servicePageRepository.save(build);
        log.info("Service page was save");
    }
}
