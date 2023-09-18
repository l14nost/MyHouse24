package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.MainPage;
import lab.space.my_house_24.mapper.settingsPage.MainPageMapper;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageRequest;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageResponse;
import lab.space.my_house_24.repository.MainPageRepository;
import lab.space.my_house_24.service.MainPageService;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainPageServiceImpl implements MainPageService {
    private final MainPageRepository mainPageRepository;

    @Override
    public MainPageResponse findByIdResponse(Long id) {
        log.info("Try to find main page dto by id: "+id);
        return MainPageMapper.entityToResponse(mainPageRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Main page by id "+id+" is not found")));
    }

    @Override
    public MainPage findById(Long id) {
        log.info("Try to find main page by id: "+id);
        return mainPageRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Main page by id "+id+" is not found"));
    }

    @Override
    public void update(MainPageRequest mainPageRequest) {
        log.info("Try to update main page");
        MainPage mainPage = findById(1L);
        mainPage.getSeo().setDescription(mainPageRequest.seoDescription());
        mainPage.getSeo().setTitle(mainPageRequest.seoTitle());
        mainPage.getSeo().setKeyWords(mainPageRequest.seoKeyWords());
        mainPage.setDescription(mainPageRequest.description());
        mainPage.setTitle(mainPageRequest.title());
        if (!mainPageRequest.slide3().isEmpty()){
            FileHandler.deleteFile(mainPage.getSlide3());
            mainPage.setSlide3(FileHandler.saveFile(mainPageRequest.slide3()));
        }
        if (!mainPageRequest.slide2().isEmpty()){
            FileHandler.deleteFile(mainPage.getSlide2());
            mainPage.setSlide2(FileHandler.saveFile(mainPageRequest.slide2()));
        }
        if (!mainPageRequest.slide1().isEmpty()){
            FileHandler.deleteFile(mainPage.getSlide1());
            mainPage.setSlide1(FileHandler.saveFile(mainPageRequest.slide1()));
        }
        if (mainPageRequest.showLink()!=null) {
            mainPage.setLinks(mainPageRequest.showLink());
        }
        else mainPage.setLinks(false);
        for (int i =0; i<mainPage.getBannerList().size(); i++){
            mainPage.getBannerList().get(i).setDescription(mainPageRequest.blockDescription().get(i));
            mainPage.getBannerList().get(i).setName(mainPageRequest.blockTitle().get(i));
            if (!mainPageRequest.block().get(i).isEmpty()){
                FileHandler.deleteFile(mainPage.getBannerList().get(i).getImage());
                mainPage.getBannerList().get(i).setImage(FileHandler.saveFile(mainPageRequest.block().get(i)));
            }

        }
        mainPageRepository.save(mainPage);
        log.info("Main page was update");
    }

    @Override
    public void save(MainPage build) {
        log.info("Try to save new main page");
        mainPageRepository.save(build);
        log.info("Main page was save");
    }


}
