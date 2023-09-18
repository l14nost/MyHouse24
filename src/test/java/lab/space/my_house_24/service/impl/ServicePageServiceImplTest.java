package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.Banner;
import lab.space.my_house_24.entity.settingsPage.Seo;
import lab.space.my_house_24.entity.settingsPage.ServicePage;
import lab.space.my_house_24.model.settingsPage.banner.BannerResponseForService;
import lab.space.my_house_24.model.settingsPage.service.ServicePageRequest;
import lab.space.my_house_24.model.settingsPage.service.ServicePageResponse;
import lab.space.my_house_24.repository.ServicePageRepository;
import lab.space.my_house_24.service.BannerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicePageServiceImplTest {
    @Mock
    private ServicePageRepository servicePageRepository;
    @Mock
    private BannerService bannerService;
    @InjectMocks
    private ServicePageServiceImpl servicePageService;

    @Test
    void findById() {
        ServicePage servicePage = ServicePage.builder()
                .id(1L)
                .bannerList(List.of(Banner.builder()
                                .description("descr")
                                .image("img")
                                .name("name")
                        .build()))
                .seo(Seo.builder().keyWords("key").title("title").description("descr").build())
                .build();
        when(servicePageRepository.findById(1L)).thenReturn(Optional.of(servicePage));
        ServicePage servicePage1 = servicePageService.findById(1L);

        assertEquals(servicePage, servicePage1);
    }

    @Test
    void findByIdResponse() {
        ServicePage servicePage = ServicePage.builder()
                .id(1L)
                .bannerList(List.of(Banner.builder()
                        .description("descr")
                        .image("img")
                        .name("name")
                        .build()))
                .seo(Seo.builder().keyWords("key").title("title").description("descr").build())
                .build();
        when(servicePageRepository.findById(1L)).thenReturn(Optional.of(servicePage));

        ServicePageResponse servicePageResponse = servicePageService.findByIdResponse(1L);

        ServicePageResponse servicePageResponse1 = ServicePageResponse.builder()
                .seoDescription("descr")
                .seoTitle("title")
                .seoKeyWords("key")
                .bannerResponseList(List.of(
                        BannerResponseForService.builder().image("img").title("name").description("descr").build()
                ))
                .build();

        assertEquals(servicePageResponse1, servicePageResponse);
    }

    @Test
    void update() {
        ServicePageRequest servicePageRequest = ServicePageRequest.builder()
                .blockDescriptionList(List.of(
                        "descr",
                        "descr",
                        "descr"
                ))
                .imageList(List.of(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                ))
                .deleteBlockList(List.of(
                        1L
                ))
                .blockTitleList(List.of(
                        "title",
                        "title",
                        "title"
                ))
                .seoTitle("title")
                .seoDescription("descr")
                .seoKeyWords("key")
                .build();

        ServicePage servicePage = ServicePage.builder()
                .id(1L)
                .bannerList(
                        new ArrayList<>(
                        List.of(
                                Banner.builder().description("descr").id(1L).image("img").name("name").build(),
                                Banner.builder().description("descr").id(2L).image("img").name("name").build()
                        ))
                )
                .seo(Seo.builder().keyWords("key").title("title").description("descr").build())
                .build();
        when(servicePageRepository.findById(1L)).thenReturn(Optional.of(servicePage));
        servicePageService.update(servicePageRequest);
        verify(bannerService,times(1)).deleteById(1L);
        verify(servicePageRepository, times(1)).save(any(ServicePage.class));


    }

    @Test
    void update_emptyFiles() throws IOException {
        ServicePageRequest servicePageRequest = ServicePageRequest.builder()
                .blockDescriptionList(List.of(
                        "descr",
                        "descr",
                        "descr"
                ))
                .imageList(List.of(
                        new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream()),
                        new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream()),
                        new MockMultipartFile("file","example.txt","text/plain",new ByteArrayResource(new byte[0]).getInputStream())
                ))
                .deleteBlockList(List.of(
                        1L
                ))
                .blockTitleList(List.of(
                        "title",
                        "title",
                        "title"
                ))
                .seoTitle("title")
                .seoDescription("descr")
                .seoKeyWords("key")
                .build();

        ServicePage servicePage = ServicePage.builder()
                .id(1L)
                .bannerList(
                        new ArrayList<>(
                                List.of(
                                        Banner.builder().description("descr").id(1L).image("img").name("name").build(),
                                        Banner.builder().description("descr").id(2L).image("img").name("name").build()
                                ))
                )
                .seo(Seo.builder().keyWords("key").title("title").description("descr").build())
                .build();
        when(servicePageRepository.findById(1L)).thenReturn(Optional.of(servicePage));
        servicePageService.update(servicePageRequest);
        verify(bannerService,times(1)).deleteById(1L);
        verify(servicePageRepository, times(1)).save(any(ServicePage.class));


    }
    @Test
    void save() {
        servicePageService.save(ServicePage.builder().build());
        verify(servicePageRepository).save(ServicePage.builder().build());
    }
}