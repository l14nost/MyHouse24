package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.model.service.ServiceRequest;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lab.space.my_house_24.model.service.ServiceSaveRequest;
import lab.space.my_house_24.model.unit.UnitRequest;
import lab.space.my_house_24.model.unit.UnitResponse;
import lab.space.my_house_24.model.unit.UnitSaveRequest;
import lab.space.my_house_24.service.ServiceService;
import lab.space.my_house_24.service.UnitService;
import lab.space.my_house_24.validator.ServiceValidator;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    @MockBean
    private UnitService unitService;

    @MockBean
    private ServiceService serviceService;

    @MockBean
    private ServiceValidator serviceValidator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showServicesPage() throws Exception {
        mockMvc.perform(get("/system-service"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/system-service/service"));
    }

    @Test
    void getAllServiceDto() throws Exception {
        List<ServiceResponse> serviceResponses = List.of(
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build()
        );
        when(serviceService.getAllServicesDto()).thenReturn(serviceResponses);
        mockMvc.perform(get("/system-service/get-all-services"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(serviceResponses)));
    }

    @Test
    void saveListService() throws Exception {
        ServiceSaveRequest request = ServiceSaveRequest.builder()
                .serviceRequestList(
                        List.of(
                                ServiceRequest.builder()
                                        .id(1L)
                                        .name("Test1")
                                        .isActiv(false)
                                        .unitId(1L)
                                        .build(),
                                ServiceRequest.builder()
                                        .name("Test2")
                                        .isActiv(false)
                                        .unitId(1L)
                                        .build()
                        )
                )
                .build();
        mockMvc.perform(post("/system-service/save-services")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(serviceService, times(1)).saveServiceByRequest(any());
    }

    @Test
    void saveListServiceEntityNotFound() throws Exception {
        ServiceSaveRequest request = ServiceSaveRequest.builder()
                .serviceRequestList(
                        List.of(
                                ServiceRequest.builder()
                                        .id(1L)
                                        .name("Test1")
                                        .isActiv(false)
                                        .unitId(1L)
                                        .build(),
                                ServiceRequest.builder()
                                        .name("Test2")
                                        .isActiv(false)
                                        .unitId(1L)
                                        .build()
                        )
                )
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(serviceService)
                .saveServiceByRequest(any());
        mockMvc.perform(post("/system-service/save-services")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(serviceService, times(1)).saveServiceByRequest(any());
    }

    @Test
    void saveListServiceBadRequest() throws Exception {
        ServiceSaveRequest request = ServiceSaveRequest.builder()
                .serviceRequestList(
                        List.of(
                                ServiceRequest.builder()
                                        .id(0L)
                                        .name("")
                                        .isActiv(false)
                                        .unitId(0L)
                                        .build(),
                                ServiceRequest.builder()
                                        .name("")
                                        .isActiv(false)
                                        .unitId(0L)
                                        .build()
                        )
                )
                .build();
        mockMvc.perform(post("/system-service/save-services")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(serviceService, times(0)).saveServiceByRequest(any());
    }

    @Test
    void deleteServiceById() throws Exception {
        mockMvc.perform(delete("/system-service/delete-service/1"))
                .andExpect(status().isOk());
        verify(serviceService, times(1)).deleteServiceById(anyLong());
    }

    @Test
    void deleteServiceByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(serviceService)
                .deleteServiceById(anyLong());
        mockMvc.perform(delete("/system-service/delete-service/1"))
                .andExpect(status().isNotFound());
        verify(serviceService, times(1)).deleteServiceById(anyLong());
    }

    @Test
    void deleteServiceByIdBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(serviceService)
                .deleteServiceById(anyLong());
        mockMvc.perform(delete("/system-service/delete-service/1"))
                .andExpect(status().isBadRequest());
        verify(serviceService, times(1)).deleteServiceById(anyLong());
    }

    @Test
    void getAllUnitDto() throws Exception {
        Page<UnitResponse> serviceResponses = new PageImpl<>(List.of(
                UnitResponse.builder().build(),
                UnitResponse.builder().build(),
                UnitResponse.builder().build(),
                UnitResponse.builder().build()
        ));
        when(unitService.getAllUnitDtoForSelect(anyInt(),anyString())).thenReturn(serviceResponses);
        mockMvc.perform(get("/system-service/get-all-unit"))
                .andExpect(status().isOk());
    }

    @Test
    void saveListUnit() throws Exception {
        UnitSaveRequest request = UnitSaveRequest.builder()
                .unitRequestList(
                        List.of(
                                UnitRequest.builder()
                                        .id(1L)
                                        .name("Test1")
                                        .build(),
                                UnitRequest.builder()
                                        .name("Test2")
                                        .build()
                        )
                )
                .build();
        mockMvc.perform(post("/system-service/save-unit")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(unitService, times(1)).saveUnitByRequest(any());
    }

    @Test
    void saveListUnitEntityNotFound() throws Exception {
        UnitSaveRequest request = UnitSaveRequest.builder()
                .unitRequestList(
                        List.of(
                                UnitRequest.builder()
                                        .id(1L)
                                        .name("Test1")
                                        .build(),
                                UnitRequest.builder()
                                        .name("Test2")
                                        .build()
                        )
                )
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(unitService)
                .saveUnitByRequest(any());
        mockMvc.perform(post("/system-service/save-unit")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(unitService, times(1)).saveUnitByRequest(any());
    }

    @Test
    void saveListUnitBadRequest() throws Exception {
        UnitSaveRequest request = UnitSaveRequest.builder()
                .unitRequestList(
                        List.of(
                                UnitRequest.builder()
                                        .id(0L)
                                        .name("")
                                        .build(),
                                UnitRequest.builder()
                                        .name("")
                                        .build()
                        )
                )
                .build();
        mockMvc.perform(post("/system-service/save-unit")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(unitService, times(0)).saveUnitByRequest(any());
    }

    @Test
    void deleteUnitById() throws Exception {
        mockMvc.perform(delete("/system-service/delete-unit/1"))
                .andExpect(status().isOk());
        verify(unitService, times(1)).deleteUnitById(anyLong());
    }

    @Test
    void deleteUnitByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(unitService)
                .deleteUnitById(anyLong());
        mockMvc.perform(delete("/system-service/delete-unit/1"))
                .andExpect(status().isNotFound());
        verify(unitService, times(1)).deleteUnitById(anyLong());
    }

    @Test
    void deleteUnitByIdBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(unitService)
                .deleteUnitById(anyLong());
        mockMvc.perform(delete("/system-service/delete-unit/1"))
                .andExpect(status().isBadRequest());
        verify(unitService, times(1)).deleteUnitById(anyLong());
    }

    @Test
    void serviceForSelect() throws Exception {
        Page<ServiceResponseForSelect> serviceResponseForSelects = new PageImpl<>(List.of(
                ServiceResponseForSelect.builder().build(),
                ServiceResponseForSelect.builder().build(),
                ServiceResponseForSelect.builder().build(),
                ServiceResponseForSelect.builder().build()
        ));
        when(serviceService.serviceResponseForSelect(anyInt(), anyString())).thenReturn(serviceResponseForSelects);
        mockMvc.perform(get("/system-service/get-services-for-select")
                        .param("page", "1")
                        .param("search", "Test"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(serviceResponseForSelects)));
    }
}
