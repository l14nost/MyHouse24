package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.settingsPage.Banner;
import lab.space.my_house_24.repository.BannerRepository;
import lab.space.my_house_24.service.BannerService;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    @Override
    public void deleteById(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Banner by id "+id+" is not found"));
        if (banner.getImage()!=null){
            FileHandler.deleteFile(banner.getImage());
        }
        bannerRepository.deleteById(id);

    }
}
