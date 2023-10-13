package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.staff.StaffResponseForHeader;
import lab.space.my_house_24.service.StaffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminPanelController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminPanelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StaffService staffService;

    @Test
    void redirectAdmin() throws Exception {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").role(Role.builder().securityLevelList(List.of(SecurityLevel.builder().id(1L).page(lab.space.my_house_24.enums.Page.ROLES).build())).build()).build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                staff.getUsername(),
                staff.getPassword(),
                Collections.singletonList(authority)
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, Collections.singletonList(authority));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getCurrentStaffForHeader() throws Exception {
        when(staffService.getCurrentStaffForHeader()).thenReturn(StaffResponseForHeader.builder().build());
        mockMvc.perform(get("/get-current-staff-for-header"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(StaffResponseForHeader.builder().build())));
    }
}
