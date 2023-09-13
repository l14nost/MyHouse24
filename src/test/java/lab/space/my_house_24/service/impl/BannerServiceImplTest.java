package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.Banner;
import lab.space.my_house_24.entity.settingsPage.Document;
import lab.space.my_house_24.repository.BannerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BannerServiceImplTest {
    @Mock
    private BannerRepository bannerRepository;
    @InjectMocks
    private BannerServiceImpl bannerService;

    @Test
    void deleteById() {
        when(bannerRepository.findById(1L)).thenReturn(Optional.of(Banner.builder().image("img.doc").build()));
        bannerService.deleteById(1L);
        verify(bannerRepository,times(1)).deleteById(1L);
    }
}