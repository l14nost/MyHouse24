package lab.space.my_house_24.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.*;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.JwtServiceForUser;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private HouseService houseService;
    @MockBean
    private UserValidator userValidator;
    @MockBean
    private JwtServiceForUser jwtServiceForUser;

    @Test
    void userMainPage() throws Exception {
        when(houseService.houseListForTable()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/users/user-main"));
    }

    @Test
    void getAllUserSpecification() throws Exception {
        UserMainPageRequest userMainPageRequest = UserMainPageRequest.builder().page(1).build();
        when(userService.getAllUserDto(userMainPageRequest)).thenReturn(new PageImpl<>(List.of()));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/get-all-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userMainPageRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));
    }

    @Test
    void userCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/user-card/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/users/user-card"));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete-user/1"))
                .andExpect(status().isOk());
        verify(userService,times(1)).deleteById(1L);
    }

    @Test
    void addUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/add-user"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/users/user-add"));
    }

    @Test
    void addUserPost() throws Exception {
        UserAddRequest userAddRequest = UserAddRequest.builder()
                .status(UserStatus.ACTIVE)
                .img(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .date(LocalDate.now())
                .email("test@gmail.com")
                .surname("Sur")
                .lastname("Last")
                .firstname("First")
                .viber("1231231231")
                .telegram("1231231231")
                .number("1231231231")
                .email("test@gmail.com")
                .notes("notes")
                .password("pass")
                .confirmPassword("pass")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/add-user")
                        .flashAttr("userAddRequest", userAddRequest))
                .andExpect(status().isOk());

        verify(userValidator, times(1)).ageValid(any(LocalDate.class), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).passwordMatch(anyString(), anyString(), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).uniqueEmail(anyString(), anyLong(), any(BindingResult.class), anyString());
        verify(userService, times(1)).save(userAddRequest);

    }

    @Test
    void addUserPost_Valid() throws Exception {
        UserAddRequest userAddRequest = UserAddRequest.builder()
                .status(UserStatus.ACTIVE)
                .img(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                .date(LocalDate.now())
                .email("test@gmail.com")
                .surname("Sur")
                .lastname("Last")
                .firstname("First")
                .viber("1231231231")
                .telegram("1231231231")
                .number("1231231231")
                .email("test@gmail.com")
                .notes("notes")
                .password("pass")
                .confirmPassword("pass")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/users/add-user")
                        .flashAttr("userAddRequest", userAddRequest))
                .andExpect(status().isBadRequest());

        verify(userValidator, times(1)).ageValid(any(LocalDate.class), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).passwordMatch(anyString(), anyString(), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).uniqueEmail(anyString(), anyLong(), any(BindingResult.class), anyString());
        verify(userService, times(0)).save(userAddRequest);

    }

    @Test
    void editUser() throws Exception {
        when(userService.findByIdEdit(1L)).thenReturn(UserEditResponse.builder().status(UserStatus.ACTIVE).build());
        mockMvc.perform(MockMvcRequestBuilders.get("/users/edit-user/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/users/user-edit"));
    }

    @Test
    void editUserPut() throws Exception {
        UserEditRequest userEditRequest = UserEditRequest.builder()
                .status(UserStatus.ACTIVE)
                .img(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .date(LocalDate.now())
                .email("test@gmail.com")
                .surname("Sur")
                .lastname("Last")
                .firstname("First")
                .viber("1231231231")
                .telegram("1231231231")
                .number("1231231231")
                .email("test@gmail.com")
                .notes("notes")
                .password("pass")
                .confirmPassword("pass")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/edit-user/1")
                        .flashAttr("userEditRequest",userEditRequest))
                .andExpect(status().isOk());

        verify(userValidator, times(1)).ageValid(any(LocalDate.class), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).passwordMatch(anyString(), anyString(), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).uniqueEmail(anyString(), anyLong(), any(BindingResult.class), anyString());
        verify(userService, times(1)).update(userEditRequest, 1L);
    }

    @Test
    void editUserPut_Valid() throws Exception {
        UserEditRequest userEditRequest = UserEditRequest.builder()
                .status(UserStatus.ACTIVE)
                .img(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                .date(LocalDate.now())
                .email("test@gmail.com")
                .surname("Sur")
                .lastname("Last")
                .firstname("First")
                .viber("1231231231")
                .telegram("1231231231")
                .number("1231231231")
                .email("test@gmail.com")
                .notes("notes")
                .password("pass")
                .confirmPassword("pass")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/edit-user/1")
                        .flashAttr("userEditRequest",userEditRequest))
                .andExpect(status().isBadRequest());

        verify(userValidator, times(1)).ageValid(any(LocalDate.class), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).passwordMatch(anyString(), anyString(), any(BindingResult.class), anyString());
        verify(userValidator, times(1)).uniqueEmail(anyString(), anyLong(), any(BindingResult.class), anyString());
        verify(userService, times(0)).update(userEditRequest, 1L);
    }

    @Test
    void inviteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/invite-user"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/users/user-invite"));
    }

    @Test
    void testInviteUser() throws Exception {
        UserInviteRequest userInviteRequest = UserInviteRequest.builder().number("1231231231").email("test@gmail.com").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/invite-user")
                        .content(objectMapper.writeValueAsString(userInviteRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userValidator, times(1)).uniqueEmail(anyString(), anyLong(), any(BindingResult.class), anyString());
        verify(userService, times(1)).inviteUser(userInviteRequest);
    }
    @Test
    void testInviteUser_Valid() throws Exception {
        UserInviteRequest userInviteRequest = UserInviteRequest.builder().number("123123123").email("test@gmail.com").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/invite-user")
                        .content(objectMapper.writeValueAsString(userInviteRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userValidator, times(1)).uniqueEmail(anyString(), anyLong(), any(BindingResult.class), anyString());
        verify(userService, times(0)).inviteUser(userInviteRequest);
    }

    @Test
    void findUserById() throws Exception {
        when(userService.findById(1L)).thenReturn(UserCardResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get-user-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(UserCardResponse.builder().build())));
    }

    @Test
    void userForApartmentTable() throws Exception {
        when(userService.userResponseForSelect(1, "search")).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/get-users/apartment-table")
                        .param("page", "1")
                        .param("search", "search"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));
    }

    @Test
    void sendForgot() throws Exception {
        when(userValidator.existByEmail("test@gmail.com")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/invite-send")
                        .param("email", "test@gmail.com"))
                .andExpect(status().isOk());
        verify(userService, times(1)).sendActivateLetter("test@gmail.com");

    }

    @Test
    void sendForgot_Valid() throws Exception {
        when(userValidator.existByEmail("test@gmail.com")).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/invite-send")
                        .param("email", "test@gmail.com"))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).sendActivateLetter("test@gmail.com");

    }

    @Test
    void activePage() throws Exception {
        when(userService.loadUserByToken("token")).thenReturn("mail@gmail.com");
        when(userService.getUserByEmail("mail@gmail.com")).thenReturn(User.builder().build());
        when(jwtServiceForUser.isTokenValid("token", "mail@gmail.com", User.builder().build())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/activate/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/users/activate"));
    }

    @Test
    void activePage_TokenInvalid() throws Exception {
        when(userService.loadUserByToken("token")).thenReturn("mail@gmail.com");
        when(userService.getUserByEmail("mail@gmail.com")).thenReturn(User.builder().build());
        when(jwtServiceForUser.isTokenValid("token", "mail@gmail.com", User.builder().build())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/activate/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/users/activate-error"));
    }

    @Test
    void active() throws Exception {
        ForgotPassRequest forgotPassRequest = ForgotPassRequest.builder().password("pass").confirmPassword("pass").build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/activate/token")
                        .content(objectMapper.writeValueAsString(forgotPassRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userValidator, times(1)).passwordMatch(anyString(), anyString(), any(BindingResult.class), anyString());
        verify(userService, times(1)).activate(forgotPassRequest, "token");
    }

    @Test
    void active_Valid() throws Exception {
        ForgotPassRequest forgotPassRequest = ForgotPassRequest.builder().confirmPassword("pass").build();
        mockMvc.perform(MockMvcRequestBuilders.put("/users/activate/token")
                        .content(objectMapper.writeValueAsString(forgotPassRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userValidator, times(0)).passwordMatch(anyString(), anyString(), any(BindingResult.class), anyString());
        verify(userService, times(0)).activate(forgotPassRequest, "token");
    }

    @Test
    void getUserForHeader() throws Exception {
        when(userService.usersByStatus(UserStatus.NEW)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/get-users-for-header"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }
}