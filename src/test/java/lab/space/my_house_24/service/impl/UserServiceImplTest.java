package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.user.*;
import lab.space.my_house_24.repository.UserRepository;
import lab.space.my_house_24.service.JwtServiceForUser;
import lab.space.my_house_24.specification.UserSpecification;
import lab.space.my_house_24.specification.UserSpecificationForTable;
import lab.space.my_house_24.util.CustomMailSender;
import lab.space.my_house_24.util.FileHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomMailSender customMailSender;
    @Mock
    private JwtServiceForUser jwtServiceForUser;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    private static MockedStatic<FileHandler> fileHandlerMockedStatic;
    @BeforeAll
    public static void setUp() {
        fileHandlerMockedStatic = Mockito.mockStatic(FileHandler.class);
    }

    @AfterAll
    public static void tearDown() {
        fileHandlerMockedStatic.close();
    }
    @Test
    void getUserByEmail() {
        when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(Optional.of(User.builder().build()));
        assertEquals(User.builder().build(), userService.getUserByEmail("test@gmail.com"));
    }

    @Test
    void getAllUserDto() {
        Page<User> userPage = new PageImpl<>(
                List.of(
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build(),
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build(),
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build(),
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build()
                )
        );
        UserMainPageRequest userMainPageRequest = UserMainPageRequest.builder().page(1).build();
        UserSpecification userSpecification = UserSpecification.builder().userMainPageRequest(userMainPageRequest).build();
        when(userRepository.findAll(userSpecification, PageRequest.of(1,10))).thenReturn(userPage);
        Page<UserResponse> userResponsePage = userService.getAllUserDto(userMainPageRequest);
        assertEquals(4, userResponsePage.getTotalElements());
        assertEquals(UserResponse.class, userResponsePage.iterator().next().getClass());
    }

    @Test
    void findById() {
        User user = User.builder()
                .id(1L)
                .addDate(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .filename("filename")
                .notes("notes")
                .apartmentList(new ArrayList<>(List.of(
                        Apartment.builder()
                                .number(100)
                                .house(House.builder().name("name").build())
                                .build()
                )))
                .applicationList(new ArrayList<>(List.of(
                        MastersApplication.builder().build()
                )))
                .userStatus(UserStatus.ACTIVE)
                .token("token")
                .tokenUsage(true)
                .date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .messageList(new ArrayList<>(List.of(
                        Message.builder().build()
                )))
                .tokenList(new ArrayList<>())
                .build();
        when(userRepository.findById(1L)).thenReturn(
                Optional.of(user)
        );

        assertEquals(UserCardResponse.builder()
                .id(1L)
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .filename("filename")
                .notes("notes")
                .date(LocalDate.of(2021,12,12))
                .status(EnumResponse.builder().name(UserStatus.ACTIVE.name()).value(UserStatus.ACTIVE.getUserStatus(LocaleContextHolder.getLocale())).build())
                .build(), userService.findById(1L));
    }

    @Test
    void findByIdEdit() {
        User user = User.builder()
                .id(1L)
                .addDate(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .filename("filename")
                .notes("notes")
                .apartmentList(new ArrayList<>(List.of(
                        Apartment.builder()
                                .number(100)
                                .house(House.builder().name("name").build())
                                .build()
                )))
                .applicationList(new ArrayList<>(List.of(
                        MastersApplication.builder().build()
                )))
                .userStatus(UserStatus.ACTIVE)
                .token("token")
                .tokenUsage(true)
                .date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .messageList(new ArrayList<>(List.of(
                        Message.builder().build()
                )))
                .tokenList(new ArrayList<>())
                .build();
        when(userRepository.findById(1L)).thenReturn(
                Optional.of(user)
        );

        assertEquals(UserEditResponse.builder()
                .id(1L)
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .filename("filename")
                .notes("notes")
                .date(LocalDate.of(2021,12,12))
                .status(UserStatus.ACTIVE)
                .build(), userService.findByIdEdit(1L));
    }

    @Test
    void deleteById() {
        User user = User.builder()
                .id(1L)
                .addDate(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .filename("filename")
                .notes("notes")
                .apartmentList(new ArrayList<>(List.of(
                        Apartment.builder()
                                .number(100)
                                .house(House.builder().name("name").build())
                                .build()
                )))
                .applicationList(new ArrayList<>(List.of(
                        MastersApplication.builder().build()
                )))
                .userStatus(UserStatus.ACTIVE)
                .token("token")
                .tokenUsage(true)
                .date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .messageList(new ArrayList<>(List.of(
                        Message.builder().build()
                )))
                .tokenList(new ArrayList<>())
                .build();
        when(userRepository.findById(1L)).thenReturn(
                Optional.of(user)
        );
        userService.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findEntityById() {
        User user = User.builder()
                .id(1L)
                .addDate(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .filename("filename")
                .notes("notes")
                .apartmentList(new ArrayList<>(List.of(
                        Apartment.builder()
                                .number(100)
                                .house(House.builder().name("name").build())
                                .build()
                )))
                .applicationList(new ArrayList<>(List.of(
                        MastersApplication.builder().build()
                )))
                .userStatus(UserStatus.ACTIVE)
                .token("token")
                .tokenUsage(true)
                .date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .messageList(new ArrayList<>(List.of(
                        Message.builder().build()
                )))
                .tokenList(new ArrayList<>())
                .build();
        when(userRepository.findById(1L)).thenReturn(
                Optional.of(user)
        );

        assertEquals(user, userService.findEntityById(1L));


    }

    @Test
    void save() {
        UserAddRequest userAddRequest = UserAddRequest.builder()
                .status(UserStatus.ACTIVE)
                .img(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                .date(LocalDate.now())
                .email("test@gmail.com")
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .notes("notes")
                .password("pass")
                .build();
        when(passwordEncoder.encode("pass")).thenReturn("encodepass");
        userService.save(userAddRequest);
        verify(userRepository,times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode("pass");
    }

    @Test
    void save_SimpleEntity() {
        User user = User.builder().id(1L).build();
        userService.save(user);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    void update() {
        UserEditRequest userEditRequest = UserEditRequest.builder()
                .status(UserStatus.ACTIVE)
                .img(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                .date(LocalDate.now())
                .email("test@gmail.com")
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .notes("notes")
                .password("pass")
                .build();
        when(passwordEncoder.encode("pass")).thenReturn("encodepass");
        User user = User.builder()
                .id(1L)
                .addDate(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .surname("s")
                .lastname("l")
                .firstname("f")
                .viber("123123123")
                .telegram("123123123")
                .number("123123123")
                .email("test@gmail.com")
                .filename("filename")
                .notes("notes")
                .apartmentList(new ArrayList<>(List.of(
                        Apartment.builder()
                                .number(100)
                                .house(House.builder().name("name").build())
                                .build()
                )))
                .applicationList(new ArrayList<>(List.of(
                        MastersApplication.builder().build()
                )))
                .userStatus(UserStatus.ACTIVE)
                .token("token")
                .tokenUsage(true)
                .date(LocalDate.of(2021,12,12).atStartOfDay(ZoneId.systemDefault()).toInstant())
                .messageList(new ArrayList<>(List.of(
                        Message.builder().build()
                )))
                .tokenList(new ArrayList<>())
                .build();
        when(userRepository.findById(1L)).thenReturn(
                Optional.of(user)
        );

        userService.update(userEditRequest, 1L);

        verify(userRepository, times(1)).save(any(User.class));

        verify(customMailSender, times(1)).send(anyString(), anyString(), anyString());

    }

    @Test
    void inviteUser() {
        UserInviteRequest userInviteRequest = UserInviteRequest.builder()
                .email("test@gmail.com")
                .number("123123123")
                .build();
        userService.inviteUser(userInviteRequest);
        verify(customMailSender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test
    void userListForTable() {
        when(userRepository.findAll()).thenReturn(List.of(
                User.builder().build(),
                User.builder().build(),
                User.builder().build(),
                User.builder().build()
        ));

        assertEquals(4, userService.userListForTable().size());
        assertEquals(UserResponseForTable.class, userService.userListForTable().get(0).getClass());
    }

    @Test
    void userResponseForSelect() {
        Page<User> userPage = new PageImpl<>(
                List.of(
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build(),
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build(),
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build(),
                        User.builder().userStatus(UserStatus.NEW).addDate(Instant.now()).build()
                )
        );
        UserSpecificationForTable userSpecification = UserSpecificationForTable.builder().search("search").build();
        when(userRepository.findAll(userSpecification, PageRequest.of(1,5))).thenReturn(userPage);
        Page<UserResponseForTable> userResponsePage = userService.userResponseForSelect(1,"search");
        assertEquals(4, userResponsePage.getTotalElements());
        assertEquals(UserResponseForTable.class, userResponsePage.iterator().next().getClass());
    }

    @Test
    void getAllUsersForMastersApplication() {
        when(userRepository.findAll()).thenReturn(List.of(
                User.builder().build(),
                User.builder().build(),
                User.builder().build(),
                User.builder().build()
        ));

        assertEquals(4, userService.getAllUsersForMastersApplication().size());
        assertEquals(UserResponseForMastersApplication.class, userService.getAllUsersForMastersApplication().get(0).getClass());
    }

    @Test
    void countByStatus() {
        when(userRepository.countByUserStatus(UserStatus.ACTIVE)).thenReturn(10L);
        assertEquals(userService.countByStatus(UserStatus.ACTIVE), 10L);
    }

    @Test
    void usersByStatus() {
        when(userRepository.findAllByUserStatus(UserStatus.NEW)).thenReturn(List.of(
                User.builder().build(),
                User.builder().build(),
                User.builder().build(),
                User.builder().build()
        ));

        assertEquals(4, userService.usersByStatus(UserStatus.NEW).size());
        assertEquals(UserResponseForHeader.class, userService.usersByStatus(UserStatus.NEW).get(0).getClass());

    }

    @Test
    void sendActivateLetter() {
        when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(Optional.of(User.builder().email("test@gmail.com").build()));
        when(jwtServiceForUser.generateToken("test@gmail.com")).thenReturn("token");
        userService.sendActivateLetter("test@gmail.com");

        verify(userRepository, times(1)).save(User.builder().email("test@gmail.com").token("token").tokenUsage(false).build());
        verify(customMailSender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test
    void loadUserByToken() {
        when(jwtServiceForUser.extractUsername("token")).thenReturn("mail@gmail.com");
        assertEquals("mail@gmail.com",userService.loadUserByToken("token"));
    }

    @Test
    void activate() {
        when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(Optional.of(User.builder().email("test@gmail.com").build()));
        when(jwtServiceForUser.extractUsername("token")).thenReturn("test@gmail.com");
        when(passwordEncoder.encode("pass")).thenReturn("encodepass");
        userService.activate(ForgotPassRequest.builder().password("pass").build(), "token");
        verify(userRepository, times(1)).save(User.builder().email("test@gmail.com").userStatus(UserStatus.ACTIVE).tokenUsage(true).password("encodepass").build());
        verify(customMailSender, times(1)).send(anyString(),anyString(),anyString());
    }

    @Test
    void findAll() {
        List<User> users = List.of(
                User.builder().build(),
                User.builder().build(),
                User.builder().build(),
                User.builder().build()
        );

        when(userRepository.findAll()).thenReturn(users);


        assertEquals(4, userService.findAll().size());
    }
}