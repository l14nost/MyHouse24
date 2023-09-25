package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ThemeChangeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ThemeChangeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StaffService staffService;

    @Test
    void getTheme() throws Exception {
        when(staffService.getCurrentStaff()).thenReturn(1L);
        when(staffService.getStaffById(1L)).thenReturn(Staff.builder().theme(true).build());
        mockMvc.perform(MockMvcRequestBuilders.get("/get-theme"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(true)));
    }
    @Test
    void changeTheme() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/change-theme")
                        .param("theme", "false"))
                .andExpect(status().isOk());
        verify(staffService, times(1)).changeTheme(false);
    }
}