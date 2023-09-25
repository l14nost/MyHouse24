package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.controller.settingsPage.ServicePageController;
import lab.space.my_house_24.model.requites.RequisitesRequest;
import lab.space.my_house_24.model.requites.RequisitesResponse;
import lab.space.my_house_24.service.RequisitesService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RequisitesController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RequisitesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RequisitesService requisitesService;

    @Test
    void requisitesPage() throws Exception {
        when(requisitesService.findByIdResponse(1L)).thenReturn(RequisitesResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/payment-details"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/requisites/requisites-page"));
    }

    @Test
    void requisitesSave() throws Exception {
        RequisitesRequest requisitesRequest = RequisitesRequest.builder().info("info1").name("name1").build();
        mockMvc.perform(MockMvcRequestBuilders.put("/payment-details/payment-details-save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisitesRequest)))
                .andExpect(status().isOk());
        verify(requisitesService, times(1)).update(requisitesRequest);
    }

    @Test
    void requisitesSave_Valid() throws Exception {
        RequisitesRequest requisitesRequest = RequisitesRequest.builder().name("name1").build();
        mockMvc.perform(MockMvcRequestBuilders.put("/payment-details/payment-details-save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisitesRequest)))
                .andExpect(status().isBadRequest());
        verify(requisitesService, times(0)).update(requisitesRequest);
    }

}