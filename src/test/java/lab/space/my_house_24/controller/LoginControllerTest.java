package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.staff.ForgotPassEmailRequest;
import lab.space.my_house_24.model.staff.ForgotPassRequest;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

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
    void showLoginRedirect() throws Exception {
        Staff user = Staff.builder().password("pass").email("mail@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, Collections.singletonList(authority));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void showLogin() throws Exception {
        Staff user = Staff.builder().password("pass").email("mail@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/login/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/login/login"));
    }

    @Test
    void showForgotPasswordPage() throws Exception {
        mockMvc.perform(get("/login/forgot-password"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/login/forgot-password"));
    }

    @Test
    void showForgotPasswordChangePage() throws Exception {
        when(staffService.loadUserByToken(any())).thenReturn(Staff.builder().email("test").build());
        when(!jwtService.isTokenValid(any(), any(), any(), any())).thenReturn(true);
        mockMvc.perform(get("/login/forgot-password/Token"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff-activate"));
    }

    @Test
    void showForgotPasswordChangePageInvalidToken() throws Exception {
        when(staffService.loadUserByToken(any())).thenReturn(Staff.builder().email("test").build());
        when(!jwtService.isTokenValid(any(), any(), any(), any())).thenReturn(false);
        mockMvc.perform(get("/login/forgot-password/Token"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/staff/staff-activate-error"));
    }

    @Test
    void sendForgotPasswordStaff() throws Exception {
        mockMvc.perform(post("/login/send-forgot-password-staff")
                        .content(objectMapper.writeValueAsString(ForgotPassEmailRequest.builder().email("test@gmail.com").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(staffService, times(1)).sendForgotPasswordUrl(anyString());
    }

    @Test
    void sendForgotPasswordStaffBadRequest() throws Exception {
        mockMvc.perform(post("/login/send-forgot-password-staff")
                        .content(objectMapper.writeValueAsString(ForgotPassEmailRequest.builder().email("test.com").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(staffService, times(0)).sendForgotPasswordUrl(anyString());
    }

    @Test
    void forgotPasswordStaff() throws Exception {
        mockMvc.perform(put("/login/forgot-password-staff-change")
                        .content(objectMapper.writeValueAsString(ForgotPassRequest.builder()
                                .token("qweqwe")
                                .password("Test")
                                .confirmPassword("Test")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(staffService, times(1)).forgotPasswordStaff(any());
    }

    @Test
    void forgotPasswordStaffEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(staffService)
                .forgotPasswordStaff(any());
        mockMvc.perform(put("/login/forgot-password-staff-change")
                        .content(objectMapper.writeValueAsString(ForgotPassRequest.builder()
                                .token("qweqwe")
                                .password("Test")
                                .confirmPassword("Test")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(staffService, times(1)).forgotPasswordStaff(any());
    }

    @Test
    void forgotPasswordStaffBadRequest() throws Exception {
        mockMvc.perform(put("/login/forgot-password-staff-change")
                        .content(objectMapper.writeValueAsString(ForgotPassRequest.builder()
                                .token("")
                                .password("")
                                .confirmPassword("")
                                .build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(staffService, times(0)).forgotPasswordStaff(any());
    }
}
