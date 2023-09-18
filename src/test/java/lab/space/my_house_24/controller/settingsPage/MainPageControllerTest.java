package lab.space.my_house_24.controller.settingsPage;

import lab.space.my_house_24.entity.settingsPage.Banner;
import lab.space.my_house_24.model.settingsPage.about.AboutResponse;
import lab.space.my_house_24.model.settingsPage.banner.BannerResponse;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageRequest;
import lab.space.my_house_24.model.settingsPage.mainPage.MainPageResponse;
import lab.space.my_house_24.service.MainPageService;
import lab.space.my_house_24.service.impl.MainPageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MainPageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MainPageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MainPageService mainPageService;

    @Test
    void mainPage() throws Exception {
        when(mainPageService.findByIdResponse(1L)).thenReturn(MainPageResponse.builder()
                .blocks(List.of(
                        BannerResponse.builder().title("name").block("img").description("descr").build(),
                        BannerResponse.builder().title("name").block("img").description("descr").build(),
                        BannerResponse.builder().title("name").block("img").description("descr").build(),
                        BannerResponse.builder().title("name").block("img").description("descr").build(),
                        BannerResponse.builder().title("name").block("img").description("descr").build(),
                        BannerResponse.builder().title("name").block("img").description("descr").build()
                ))
                .build());
        mockMvc.perform(MockMvcRequestBuilders.get("/site/main-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/pages/settingsPage/mainPage/main-page"));
    }

    @Test
    void saveMainPage() throws Exception {
        MainPageRequest mainPageRequest = MainPageRequest.builder()
                .block(List.of(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())))
                .blockDescription(List.of("string"))
                .blockTitle(List.of("string"))
                .description("descr")
                .showLink(true)
                .seoDescription("descr")
                .seoTitle("title")
                .seoKeyWords("key")
                .slide1(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .slide2(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .slide3(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .title("title")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/site/main-page-save")
                        .flashAttr("mainPageRequest", mainPageRequest))
                .andExpect(status().isOk());
    }

    @Test
    void saveMainPage_Valid() throws Exception {
        MainPageRequest mainPageRequest = MainPageRequest.builder()
                .block(List.of(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())))
                .blockDescription(List.of("string"))
                .blockTitle(List.of("string"))
                .description("descr")
                .showLink(true)
                .seoDescription("descr")
                .seoTitle("title")
                .seoKeyWords("key")
                .slide1(new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()))
                .slide2(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .slide3(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .title("title")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/site/main-page-save")
                        .flashAttr("mainPageRequest", mainPageRequest))
                .andExpect(status().isBadRequest());
    }
}