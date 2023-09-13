package lab.space.my_house_24.controller.settingsPage;

import lab.space.my_house_24.model.settingsPage.about.AboutRequest;
import lab.space.my_house_24.model.settingsPage.about.AboutResponse;
import lab.space.my_house_24.service.AboutService;
import lab.space.my_house_24.service.impl.AboutServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AboutPageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AboutPageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AboutService aboutService;
    @Test
    void aboutPage() throws Exception {
        when(aboutService.findByIdResponse(1L)).thenReturn(AboutResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/site/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/pages/settingsPage/about/about-page"));

    }

    @Test
    void saveAbout() throws Exception {
        AboutRequest aboutRequest = AboutRequest.builder()
                .documentList(List.of(
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes())
                ))
                .addDescription("addDescr")
                .title("title")
                .description("descr")
                .seoTitle("seoTitle")
                .addTitle("addTitle")
                .addGalleryList(List.of(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                ))
                .galleryList(
                        List.of(
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                        )
                )
                .seoDescription("seoDescription")
                .seoKeyWords("seoKey")
                .directImage(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                )
                .deleteAddGalleryList(List.of(1L,2L))
                .deleteGalleryList(List.of(4L,5L))
                .deleteDocumentList(List.of(1L,3L))
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/site/about-save")
                        .flashAttr("aboutRequest",aboutRequest))
                .andExpect(status().isOk());
        verify(aboutService,times(1)).update(aboutRequest);
    }

    @Test
    void saveAbout_Valid() throws Exception {
        AboutRequest aboutRequest = AboutRequest.builder()
                .documentList(List.of(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes())
                ))
                .addDescription("addDescr")
                .title("title")
                .description("descr")
                .seoTitle("seoTitle")
                .addTitle("addTitle")
                .addGalleryList(List.of(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                ))
                .galleryList(
                        List.of(
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()),
                                new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                        )
                )
                .seoDescription("seoDescription")
                .seoKeyWords("seoKey")
                .directImage(
                        new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes())
                )
                .deleteAddGalleryList(List.of(1L,2L))
                .deleteGalleryList(List.of(4L,5L))
                .deleteDocumentList(List.of(1L,3L))
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/site/about-save")
                        .flashAttr("aboutRequest",aboutRequest))
                .andExpect(status().isBadRequest());
        verify(aboutService,times(0)).update(aboutRequest);
    }
}