package lab.space.my_house_24.controller.settingsPage;

import lab.space.my_house_24.model.settingsPage.service.ServicePageRequest;
import lab.space.my_house_24.model.settingsPage.service.ServicePageResponse;
import lab.space.my_house_24.service.ServicePageService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ServicePageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ServicePageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServicePageService servicePageService;

    @Test
    void siteServicePage() throws Exception {

        when(servicePageService.findByIdResponse(1L)).thenReturn(ServicePageResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/site/service-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/settingsPage/service/service-page"));
    }

    @Test
    void saveServicePage() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.put("/site/service-page-save").flashAttr("servicePageRequest",servicePageRequest))
                .andExpect(status().isOk());

    }

    @Test
    void saveServicePage_Valid() throws Exception {
        ServicePageRequest servicePageRequest = ServicePageRequest.builder()
                .blockDescriptionList(List.of(
                        "descr",
                        "descr",
                        "descr"
                ))
                .imageList(List.of(
                        new MockMultipartFile("file","example.doc","text/plain","Hello World".getBytes()),
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

        mockMvc.perform(MockMvcRequestBuilders.put("/site/service-page-save").flashAttr("servicePageRequest",servicePageRequest))
                .andExpect(status().isBadRequest());

    }
}