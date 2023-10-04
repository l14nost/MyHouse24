package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.model.role.PageResponse;
import lab.space.my_house_24.model.role.RoleResponse;
import lab.space.my_house_24.model.role.RoleUpdateRequest;
import lab.space.my_house_24.service.RoleService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @MockBean
    private RoleService roleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showLogin() throws Exception {
        mockMvc.perform(get("/role"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/role/role"));
    }

    @Test
    void getAllStaff() throws Exception {
        RoleResponse roleResponses = RoleResponse.builder().build();
        when(roleService.getAllRoleDto()).thenReturn(roleResponses);
        mockMvc.perform(get("/role/get-all-roles"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(roleResponses)));
    }

    @Test
    void updateStaffById() throws Exception {
        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .manager(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .accountant(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .electrician(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .plumber(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .build();
        mockMvc.perform(put("/role/update-all-roles")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(roleService, times(1)).updateAllRole(any());
    }

    @Test
    void updateStaffByIdEntityNotFound() throws Exception {
        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .manager(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .accountant(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .electrician(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .plumber(PageResponse.builder()
                        .statistics(true)
                        .cashBox(true)
                        .bills(true)
                        .bankBooks(true)
                        .apartments(true)
                        .apartmentsOwner(true)
                        .houses(true)
                        .messages(true)
                        .mastersApplications(true)
                        .meterReading(true)
                        .settingsPage(true)
                        .services(true)
                        .rates(true)
                        .staff(true)
                        .roles(true)
                        .requisites(true)
                        .build())
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(roleService)
                .updateAllRole(any());
        mockMvc.perform(put("/role/update-all-roles")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(roleService, times(1)).updateAllRole(any());
    }

    @Test
    void updateStaffByIdBadRequest() throws Exception {
        RoleUpdateRequest request = RoleUpdateRequest.builder()
                .manager(PageResponse.builder()
                        .statistics(null)
                        .cashBox(null)
                        .bills(null)
                        .bankBooks(null)
                        .apartments(null)
                        .apartmentsOwner(null)
                        .houses(null)
                        .messages(null)
                        .mastersApplications(null)
                        .meterReading(null)
                        .settingsPage(null)
                        .services(null)
                        .rates(null)
                        .staff(null)
                        .roles(null)
                        .requisites(null)
                        .build())
                .accountant(PageResponse.builder()
                        .statistics(null)
                        .cashBox(null)
                        .bills(null)
                        .bankBooks(null)
                        .apartments(null)
                        .apartmentsOwner(null)
                        .houses(null)
                        .messages(null)
                        .mastersApplications(null)
                        .meterReading(null)
                        .settingsPage(null)
                        .services(null)
                        .rates(null)
                        .staff(null)
                        .roles(null)
                        .requisites(null)
                        .build())
                .electrician(null)
                .plumber(null)
                .build();
        mockMvc.perform(put("/role/update-all-roles")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(roleService, times(0)).updateAllRole(any());
    }
}
