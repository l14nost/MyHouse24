package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.staff.InviteRequest;
import lab.space.my_house_24.service.JwtService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.validator.StaffValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @MockBean
    private StaffService staffService;

    @MockBean
    private StaffValidator staffValidator;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showActivateStaffPage() throws Exception {
        when(staffService.loadUserByToken(any())).thenReturn(Staff.builder().email("test").build());
        when(!jwtService.isTokenValid(any(), any(), any(), any())).thenReturn(true);
        mockMvc.perform(get("/auth/activate-staff/Token"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff-activate"));
    }

    @Test
    void showActivateStaffPageInvalidToken() throws Exception {
        when(staffService.loadUserByToken(any())).thenReturn(Staff.builder().email("test").build());
        when(!jwtService.isTokenValid(any(), any(), any(), any())).thenReturn(false);
        mockMvc.perform(get("/auth/activate-staff/Token"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff-activate-error"));
    }

    @Test
    void activateStaff() throws Exception {
        InviteRequest request = InviteRequest.builder()
                .token("Test")
                .password("Test")
                .confirmPassword("Test")
                .email("Test")
                .build();
        mockMvc.perform(put("/auth/update-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(staffService, times(1)).activateStaff(any());
    }

    @Test
    void activateStaffBadRequest() throws Exception {
        InviteRequest request = InviteRequest.builder()
                .token("")
                .password("")
                .confirmPassword("")
                .email("")
                .build();
        mockMvc.perform(put("/auth/update-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(staffService, times(0)).activateStaff(any());
    }

    @Test
    void activateStaffEntityNotFound() throws Exception {
        InviteRequest request = InviteRequest.builder()
                .token("Test")
                .password("Test")
                .confirmPassword("Test")
                .email("Test")
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(staffService)
                .activateStaff(any());
        mockMvc.perform(put("/auth/update-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(staffService, times(1)).activateStaff(any());
    }
}
