package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.*;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.validator.StaffValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StaffController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StaffControllerTest {

    @MockBean
    private StaffService staffService;

    @MockBean
    private StaffValidator staffValidator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showStaff() throws Exception {
        mockMvc.perform(get("/staff"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff"));
    }

    @Test
    void showStaffCardById() throws Exception {
        mockMvc.perform(get("/staff/card-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff-card"));
    }

    @Test
    void showStaffEdit() throws Exception {
        mockMvc.perform(get("/staff/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff-save"));
    }

    @Test
    void showStaffSave() throws Exception {
        mockMvc.perform(get("/staff/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff-save"));
    }

    @Test
    void getAllStaff() throws Exception {
        Page<StaffResponse> staffResponses = new PageImpl<>(List.of(
                StaffResponse.builder().build(),
                StaffResponse.builder().build(),
                StaffResponse.builder().build(),
                StaffResponse.builder().build()
        ));
        when(staffService.getAllStaffDto(any())).thenReturn(staffResponses);
        mockMvc.perform(post("/staff/get-all-staff")
                        .content(objectMapper.writeValueAsString(StaffRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(staffResponses)));
    }

    @Test
    void getAllJobTitle() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(staffService.getAllJobTitle()).thenReturn(enumResponses);
        mockMvc.perform(get("/staff/get-all-job-title"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllStatus() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(staffService.getAllStatus()).thenReturn(enumResponses);
        mockMvc.perform(get("/staff/get-all-status"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void sendInvite() throws Exception {
        mockMvc.perform(post("/staff/send-invite")
                        .content(objectMapper.writeValueAsString(InviteRequest.builder().build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(staffService, times(1)).sendInvite(any());
    }

    @Test
    void getStaffEditDtoById() throws Exception {
        StaffEditResponse staffResponse = StaffEditResponse.builder().build();
        when(staffService.getStaffByIdWithEditDto(anyLong())).thenReturn(staffResponse);
        mockMvc.perform(get("/staff/get-staff-edit-dto/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(staffResponse)));
    }

    @Test
    void getStaffEditDtoByIdBadRequest() throws Exception {
        mockMvc.perform(get("/staff/get-staff-edit-dto/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStaffDtoById() throws Exception {
        StaffResponse staffResponse = StaffResponse.builder().build();
        when(staffService.getStaffByIdWithCardDto(anyLong())).thenReturn(staffResponse);
        mockMvc.perform(get("/staff/get-staff-card-dto/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(staffResponse)));
    }

    @Test
    void getStaffDtoByIdBadRequest() throws Exception {
        mockMvc.perform(get("/staff/get-staff-card-dto/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveStaffById() throws Exception {
        StaffSaveRequest request = StaffSaveRequest.builder()
                .id(null)
                .firstname("Test")
                .lastname("Test")
                .phone("0123456789")
                .email("test@gmail.com")
                .role(JobTitle.ACCOUNTANT)
                .build();
        mockMvc.perform(post("/staff/save-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(staffService, times(1)).saveStaff((StaffSaveRequest) any());
    }

    @Test
    void saveStaffByIdBadRequest() throws Exception {
        StaffSaveRequest request = StaffSaveRequest.builder()
                .id(1L)
                .firstname("")
                .lastname("")
                .phone("")
                .email("test.com")
                .role(JobTitle.ACCOUNTANT)
                .build();
        mockMvc.perform(post("/staff/save-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(staffService, times(0)).saveStaff((StaffSaveRequest) any());
    }

    @Test
    void updateStaffById() throws Exception {
        StaffUpdateRequest request = StaffUpdateRequest.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .phone("0123456789")
                .email("test@gmail.com")
                .password("test")
                .confirmPassword("test")
                .role(JobTitle.ACCOUNTANT)
                .status(UserStatus.NEW)
                .build();
        mockMvc.perform(put("/staff/update-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(staffService, times(1)).updateStaff(any());
    }

    @Test
    void updateStaffByIdEntityNotFound() throws Exception {
        StaffUpdateRequest request = StaffUpdateRequest.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .phone("0123456789")
                .email("test@gmail.com")
                .password("test")
                .confirmPassword("test")
                .role(JobTitle.ACCOUNTANT)
                .status(UserStatus.NEW)
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(staffService)
                .updateStaff(any());
        mockMvc.perform(put("/staff/update-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(staffService, times(1)).updateStaff(any());
    }

    @Test
    void updateStaffByIdBadRequest() throws Exception {
        StaffUpdateRequest request = StaffUpdateRequest.builder()
                .id(0L)
                .firstname("")
                .lastname("")
                .phone("")
                .email("test.com")
                .password("")
                .confirmPassword("")
                .role(JobTitle.ACCOUNTANT)
                .status(UserStatus.NEW)
                .build();
        mockMvc.perform(put("/staff/update-staff")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(staffService, times(0)).updateStaff(any());

        StaffUpdateRequest request1 = StaffUpdateRequest.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .phone("0123456789")
                .email("test@gmail.com")
                .password("test")
                .confirmPassword("test")
                .role(JobTitle.ACCOUNTANT)
                .status(UserStatus.NEW)
                .build();
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(staffService)
                .updateStaff(any());
        mockMvc.perform(put("/staff/update-staff")
                        .content(objectMapper.writeValueAsString(request1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(staffService, times(1)).updateStaff(any());
    }

    @Test
    void deleteStaffById() throws Exception {
        mockMvc.perform(delete("/staff/delete-staff/1"))
                .andExpect(status().isOk());
        verify(staffService, times(1)).deleteStaff(any());
    }

    @Test
    void deleteStaffByIdBadRequest() throws Exception {
        mockMvc.perform(delete("/staff/delete-staff/0"))
                .andExpect(status().isBadRequest());
        verify(staffService, times(0)).deleteStaff(any());

        doThrow(new IllegalArgumentException("Bad Request"))
                .when(staffService)
                .deleteStaff(anyLong());
        mockMvc.perform(delete("/staff/delete-staff/1"))
                .andExpect(status().isBadRequest());
        verify(staffService, times(1)).deleteStaff(any());
    }

    @Test
    void deleteStaffByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(staffService)
                .deleteStaff(anyLong());
        mockMvc.perform(delete("/staff/delete-staff/1"))
                .andExpect(status().isNotFound());
        verify(staffService, times(1)).deleteStaff(any());
    }

    @Test
    void getAllStaffForHousePage() throws Exception {
        List<StaffResponseForHouseAdd> staffResponseForHouseAdds = List.of(
                StaffResponseForHouseAdd.builder().build(),
                StaffResponseForHouseAdd.builder().build(),
                StaffResponseForHouseAdd.builder().build(),
                StaffResponseForHouseAdd.builder().build()
        );
        when(staffService.getAllStaffDtoForHouse()).thenReturn(staffResponseForHouseAdds);
        mockMvc.perform(get("/staff/get-all-staff-for-house"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(staffResponseForHouseAdds)));
    }

    @Test
    void getRoleByStaff() throws Exception {
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().role(Role.builder().jobTitle(JobTitle.ACCOUNTANT).build()).build());
        mockMvc.perform(get("/staff/get-role-by-staff/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(JobTitle.ACCOUNTANT.getJobTitle(LocaleContextHolder.getLocale())));
    }

    @Test
    void getCurrentStaff() throws Exception {
        when(staffService.getCurrentStaff()).thenReturn(1L);
        mockMvc.perform(get("/staff/get-current-staff"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(1L)));
    }
}
