package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.enums.Master;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lab.space.my_house_24.model.apartment.ApartmentResponseForMastersApplication;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationResponse;
import lab.space.my_house_24.model.masters_application.MastersApplicationSaveRequest;
import lab.space.my_house_24.model.masters_application.MastersApplicationUpdateRequest;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.user.UserResponseForMastersApplication;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.MastersApplicationService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MastersApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MastersApplicationControllerTest {

    @MockBean
    private MastersApplicationService mastersApplicationService;

    @MockBean
    private StaffService staffService;

    @MockBean
    private ApartmentService apartmentService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showMastersApplicationPage() throws Exception {
        mockMvc.perform(get("/master-call"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/masters_application/masters-application"));
    }

    @Test
    void showMastersApplicationCardById() throws Exception {
        mockMvc.perform(get("/master-call/card-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/masters_application/masters-application-card"));
    }

    @Test
    void showMastersApplicationSavePage() throws Exception {
        mockMvc.perform(get("/master-call/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/masters_application/masters-application-save"));
    }

    @Test
    void showMastersApplicationUpdatePage() throws Exception {
        mockMvc.perform(get("/master-call/update-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/masters_application/masters-application-save"));
    }

    @Test
    void getMastersApplicationById() throws Exception {
        MastersApplicationResponse mastersApplicationResponse = MastersApplicationResponse.builder().build();
        when(mastersApplicationService.getMastersApplicationResponseById(anyLong())).thenReturn(mastersApplicationResponse);
        mockMvc.perform(get("/master-call/get-masters-application-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(mastersApplicationResponse)));
        verify(mastersApplicationService, times(1)).getMastersApplicationResponseById(anyLong());
    }

    @Test
    void getMastersApplicationByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(mastersApplicationService)
                .getMastersApplicationResponseById(anyLong());
        mockMvc.perform(get("/master-call/get-masters-application-1"))
                .andExpect(status().isNotFound());
        verify(mastersApplicationService, times(1)).getMastersApplicationResponseById(anyLong());
    }

    @Test
    void getMastersApplicationByIdBadRequest() throws Exception {
        mockMvc.perform(get("/master-call/get-masters-application-0"))
                .andExpect(status().isBadRequest());
        verify(mastersApplicationService, times(0)).getMastersApplicationResponseById(anyLong());
    }

    @Test
    void getAllMastersApplication() throws Exception {
        Page<MastersApplicationResponse> mastersApplicationResponses = new PageImpl<>(List.of(
                MastersApplicationResponse.builder().build(),
                MastersApplicationResponse.builder().build(),
                MastersApplicationResponse.builder().build(),
                MastersApplicationResponse.builder().build()
        ));
        when(mastersApplicationService.getAllMastersApplication(any())).thenReturn(mastersApplicationResponses);
        mockMvc.perform(post("/master-call/get-all-master-call")
                        .content(objectMapper.writeValueAsString(MastersApplicationRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(mastersApplicationResponses)));
    }

    @Test
    void getAllTypeMaster() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(mastersApplicationService.getAllTypeMaster()).thenReturn(enumResponses);
        mockMvc.perform(get("/master-call/get-all-type-master"))
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
        when(mastersApplicationService.getAllStatus()).thenReturn(enumResponses);
        mockMvc.perform(get("/master-call/get-all-status"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllStaff() throws Exception {
        Page<StaffResponse> staffResponses = new PageImpl<>(List.of(
                StaffResponse.builder().build(),
                StaffResponse.builder().build(),
                StaffResponse.builder().build(),
                StaffResponse.builder().build()
        ));
        when(staffService.getAllStaffMaster(any())).thenReturn(staffResponses);
        mockMvc.perform(get("/master-call/get-all-staff").param("pageIndex", "1").param("search", "Test"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllApartments() throws Exception {
        Page<ApartmentResponseForMastersApplication> apartmentResponseForMastersApplications = new PageImpl<>(List.of(
                ApartmentResponseForMastersApplication.builder().build(),
                ApartmentResponseForMastersApplication.builder().build(),
                ApartmentResponseForMastersApplication.builder().build(),
                ApartmentResponseForMastersApplication.builder().build()
        ));
        when(apartmentService.getAllApartmentResponseByUserId(any())).thenReturn(apartmentResponseForMastersApplications);
        mockMvc.perform(get("/master-call/get-all-apartment").param("pageIndex", "1").param("search", "Test"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUser() throws Exception {
        Page<UserResponseForMastersApplication> userResponseForMastersApplications = new PageImpl<>(List.of(
                UserResponseForMastersApplication.builder().build(),
                UserResponseForMastersApplication.builder().build(),
                UserResponseForMastersApplication.builder().build(),
                UserResponseForMastersApplication.builder().build()
        ));
        when(userService.getAllUsersForMastersApplication(anyInt(), anyString())).thenReturn(userResponseForMastersApplications);
        mockMvc.perform(get("/master-call/get-all-user"))
                .andExpect(status().isOk());
    }

    @Test
    void saveMastersApplicationById() throws Exception {
        MastersApplicationSaveRequest request = MastersApplicationSaveRequest.builder()
                .description("Test")
                .comment("Test")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(1L)
                .userId(1L)
                .apartmentId(1L)
                .build();
        mockMvc.perform(post("/master-call/save-master-call")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mastersApplicationService, times(1)).saveMastersApplicationByRequest(any());
    }

    @Test
    void saveMastersApplicationByIdEntityNotFound() throws Exception {
        MastersApplicationSaveRequest request = MastersApplicationSaveRequest.builder()
                .description("Test")
                .comment("Test")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(1L)
                .userId(1L)
                .apartmentId(1L)
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(mastersApplicationService)
                .saveMastersApplicationByRequest(any());
        mockMvc.perform(post("/master-call/save-master-call")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(mastersApplicationService, times(1)).saveMastersApplicationByRequest(any());
    }

    @Test
    void saveMastersApplicationByIdBadRequest() throws Exception {
        MastersApplicationSaveRequest request = MastersApplicationSaveRequest.builder()
                .description("")
                .comment("")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(0L)
                .userId(0L)
                .apartmentId(0L)
                .build();
        mockMvc.perform(post("/master-call/save-master-call")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(mastersApplicationService, times(0)).saveMastersApplicationByRequest(any());
    }

    @Test
    void updateMastersApplicationById() throws Exception {
        MastersApplicationUpdateRequest request = MastersApplicationUpdateRequest.builder()
                .id(1L)
                .description("Test")
                .comment("Test")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(1L)
                .userId(1L)
                .apartmentId(1L)
                .build();
        mockMvc.perform(put("/master-call/update-master-call")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mastersApplicationService, times(1)).updateMastersApplicationByRequest(any());
    }

    @Test
    void updateMastersApplicationByIdEntityNotFound() throws Exception {
        MastersApplicationUpdateRequest request = MastersApplicationUpdateRequest.builder()
                .id(1L)
                .description("Test")
                .comment("Test")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(1L)
                .userId(1L)
                .apartmentId(1L)
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(mastersApplicationService)
                .updateMastersApplicationByRequest(any());
        mockMvc.perform(put("/master-call/update-master-call")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(mastersApplicationService, times(1)).updateMastersApplicationByRequest(any());
    }

    @Test
    void updateMastersApplicationByIdBadRequest() throws Exception {
        MastersApplicationUpdateRequest request = MastersApplicationUpdateRequest.builder()
                .id(0L)
                .description("")
                .comment("")
                .master(Master.ANY_MASTER)
                .mastersApplicationStatus(MastersApplicationStatus.NEW)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .staffId(0L)
                .userId(0L)
                .apartmentId(0L)
                .build();
        mockMvc.perform(put("/master-call/update-master-call")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(mastersApplicationService, times(0)).updateMastersApplicationByRequest(any());
    }

    @Test
    void deleteMastersApplicationById() throws Exception {
        mockMvc.perform(delete("/master-call/delete-master-call/1"))
                .andExpect(status().isOk());
        verify(mastersApplicationService, times(1)).deleteMastersApplicationById(anyLong());
    }

    @Test
    void deleteMastersApplicationByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(mastersApplicationService)
                .deleteMastersApplicationById(anyLong());
        mockMvc.perform(delete("/master-call/delete-master-call/1"))
                .andExpect(status().isNotFound());
        verify(mastersApplicationService, times(1)).deleteMastersApplicationById(anyLong());
    }

    @Test
    void deleteMastersApplicationByIdBadRequest() throws Exception {
        mockMvc.perform(delete("/master-call/delete-master-call/0"))
                .andExpect(status().isBadRequest());
        verify(mastersApplicationService, times(0)).deleteMastersApplicationById(anyLong());
    }

}
