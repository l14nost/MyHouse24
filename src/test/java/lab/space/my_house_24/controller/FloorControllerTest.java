package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.service.FloorService;
import lab.space.my_house_24.service.SectionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FloorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class FloorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FloorService floorService;

//    @Test
//    void getFloorByHouse() throws Exception {
//        when(floorService.floorByHouse(1L)).thenReturn(List.of());
//        mockMvc.perform(MockMvcRequestBuilders.get("/get-floor/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
//    }
}