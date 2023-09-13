package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.settingsPage.*;
import lab.space.my_house_24.model.settingsPage.banner.BannerResponse;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageRequest;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageResponse;
import lab.space.my_house_24.repository.MainPageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainPageServiceImplTest {
    @Mock
    private MainPageRepository mainPageRepository;
    @InjectMocks
    private MainPageServiceImpl mainPageService;

    @Test
    void findByIdResponse() {
        MainPage mainPage = MainPage.builder()
                .id(1L)
                .bannerList(List.of(Banner.builder()
                        .name("name")
                        .image("img")
                        .description("descr")
                        .build()))
                .description("descr")
                .links(true)
                .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                .slide1("slide1")
                .slide2("slide2")
                .slide3("slide3")
                .title("title")
                .build();
        when(mainPageRepository.findById(1L)).thenReturn(Optional.of(mainPage));
        MainPageResponse mainPageResponse = mainPageService.findByIdResponse(1L);
        MainPageResponse mainPageResponse1 = MainPageResponse.builder()
                .blocks(List.of(BannerResponse.builder()
                        .title("name")
                        .block("img")
                        .description("descr")
                        .build()))
                .description("descr")
                .showLink(true)
                .seoDescription("descr")
                .seoTitle("title")
                .seoKeyWords("key")
                .slide1("slide1")
                .slide2("slide2")
                .slide3("slide3")
                .title("title")
                .build();
        assertEquals(mainPageResponse1, mainPageResponse);
    }

    @Test
    void findById() {
        MainPage mainPage = MainPage.builder()
                .id(1L)
                .bannerList(List.of(Banner.builder()
                                .name("name")
                                .image("img")
                                .description("descr")
                        .build()))
                .description("descr")
                .links(true)
                .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                .slide1("slide1")
                .slide2("slide2")
                .slide3("slide3")
                .title("title")
                .build();
        when(mainPageRepository.findById(1L)).thenReturn(Optional.of(mainPage));

        MainPage mainPage1 = mainPageService.findById(1L);

        assertEquals(mainPage, mainPage1);
    }

    @Test
    void update() {
        MainPageRequest mainPageRequest = MainPageRequest.builder()
                .block(List.of(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes())))
                .blockDescription(List.of("string"))
                .blockTitle(List.of("string"))
                .description("descr")
                .showLink(true)
                .seoDescription("descr")
                .seoTitle("title")
                .seoKeyWords("key")
                .slide1(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()))
                .slide2(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()))
                .slide3(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()))
                .title("title")
                .build();
        MainPage mainPage = MainPage.builder()
                .id(1L)
                .bannerList(List.of(Banner.builder()
                        .name("name")
                        .image("img")
                        .description("descr")
                        .build()))
                .description("descr")
                .links(true)
                .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                .slide1("slide1")
                .slide2("slide2")
                .slide3("slide3")
                .title("title")
                .build();
        when(mainPageRepository.findById(1L)).thenReturn(Optional.of(mainPage));

        mainPageService.update(mainPageRequest);
        verify(mainPageRepository).save(any(MainPage.class));
    }

    @Test
    void update_linkNull() {
        MainPageRequest mainPageRequest = MainPageRequest.builder()
                .block(List.of(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes())))
                .blockDescription(List.of("string"))
                .blockTitle(List.of("string"))
                .description("descr")
                .seoDescription("descr")
                .seoTitle("title")
                .seoKeyWords("key")
                .slide1(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()))
                .slide2(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()))
                .slide3(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()))
                .title("title")
                .build();
        MainPage mainPage = MainPage.builder()
                .id(1L)
                .bannerList(List.of(Banner.builder()
                        .name("name")
                        .image("img")
                        .description("descr")
                        .build()))
                .description("descr")
                .links(true)
                .seo(Seo.builder().description("descr").title("title").keyWords("key").build())
                .slide1("slide1")
                .slide2("slide2")
                .slide3("slide3")
                .title("title")
                .build();
        when(mainPageRepository.findById(1L)).thenReturn(Optional.of(mainPage));

        mainPageService.update(mainPageRequest);
        verify(mainPageRepository).save(any(MainPage.class));
    }

    @Test
    void save() {
        mainPageService.save(MainPage.builder().build());
        verify(mainPageRepository).save(MainPage.builder().build());
    }
}