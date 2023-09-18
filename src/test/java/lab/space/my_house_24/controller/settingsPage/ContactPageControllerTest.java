package lab.space.my_house_24.controller.settingsPage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.model.settingsPage.about.AboutResponse;
import lab.space.my_house_24.model.settingsPage.contact.ContactRequest;
import lab.space.my_house_24.model.settingsPage.contact.ContactResponse;
import lab.space.my_house_24.repository.ContactRepository;
import lab.space.my_house_24.service.ContactService;
import lab.space.my_house_24.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ContactPageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ContactPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    @Test
    void contactPage() throws Exception {
        when(contactService.findByIdResponse(1L)).thenReturn(ContactResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/site/contact-page"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/pages/settingsPage/contact/contact-page"));
    }

    @Test
    void saveMainPage_Valid() throws Exception {
        ContactRequest contactRequest = ContactRequest.builder()
                .address("address")
                .email("mail@gmail.com")
                .number("123123123")
                .url("url")
                .description("descr")
                .location("location")
                .fullName("fullName")
                .seoTitle("title")
                .seoKeyWords("key")
                .seoDescription("descr")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/site/contact-page-save")
                        .content(objectMapper.writeValueAsString(contactRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void saveMainPage() throws Exception {
        ContactRequest contactRequest = ContactRequest.builder()
                .address("address")
                .email("mail@gmail.com")
                .number("1231231231")
                .url("http://url")
                .description("descr")
                .location("location")
                .fullName("fullName")
                .seoTitle("title")
                .seoKeyWords("key")
                .codeMap("codeMap")
                .title("title")
                .seoDescription("descr")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/site/contact-page-save")
                        .content(objectMapper.writeValueAsString(contactRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}