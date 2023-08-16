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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicePageServiceImpl implements ServicePageService {
    private final ServicePageRepository servicePageRepository;
    private final BannerService bannerService;
    @Override
    public ServicePage findById(Long id) {
        return servicePageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Service by id "+id+" is not found"));
    }

    @Override
    public ServicePageResponse findByIdResponse(Long id) {
        return ServicePageMapper.entityToResponseDto(servicePageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Service by id "+id+" is not found")));
    }

    @Override
    public void update(ServicePageRequest servicePageRequest) {
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
                    if (i < servicePageRequest.imageList().size()) {
                        servicePage.getBannerList().get(i).setImage(null);
                        servicePage.getBannerList().get(i).setName(servicePageRequest.blockTitleList().get(i));
                        servicePage.getBannerList().get(i).setDescription(servicePageRequest.blockDescriptionList().get(i));
                    } else {
                        servicePage.getBannerList().add(Banner.builder().image(null).name(servicePageRequest.blockTitleList().get(i)).description(servicePageRequest.blockDescriptionList().get(i)).servicePage(servicePage).build());
                    }
                }
            }
        }

        servicePage.getSeo().setTitle(servicePageRequest.seoTitle());
        servicePage.getSeo().setKeyWords(servicePageRequest.seoKeyWords());
        servicePage.getSeo().setDescription(servicePageRequest.seoDescription());
        System.out.println(servicePage.getBannerList().size());
        servicePageRepository.save(servicePage);
    }
}
