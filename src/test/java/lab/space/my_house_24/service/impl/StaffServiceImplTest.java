package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.staff.*;
import lab.space.my_house_24.repository.StaffRepository;
import lab.space.my_house_24.service.JwtService;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.specification.StaffSpecification;
import lab.space.my_house_24.util.CustomMailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private StaffSpecification staffSpecification;

    @Mock
    private CustomMailSender customMailSender;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MessageSource message;

    @InjectMocks
    private StaffServiceImpl staffService;

    @Test
    void sendInvite() {
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder().email("Test").build()));
        when(jwtService.generateToken(any())).thenReturn("TokenTest");
        staffService.sendInvite(InviteRequest.builder().email("Test").build());
        verify(staffRepository, times(1)).save(any());
        verify(staffRepository, times(1)).findByEmail(anyString());
        verify(customMailSender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test
    void sendForgotPasswordUrl() {
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder().email("Test").build()));
        when(jwtService.generateToken(any())).thenReturn("TokenTest");
        staffService.sendForgotPasswordUrl("Test");
        verify(staffRepository, times(1)).save(any());
        verify(staffRepository, times(1)).findByEmail(anyString());
        verify(customMailSender, times(1)).send(anyString(), anyString(), anyString());
    }

    @Test
    void sendUpdatePasswordWarning() {
        staffService.sendUpdatePasswordWarning("Test");
        verify(customMailSender, times(1)).send(anyString(), any(), any());
    }

    @Test
    void getStaffById() {
        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(Staff.builder().build()));
        staffService.getStaffById(1L);
        verify(staffRepository, times(1)).findById(anyLong());
    }

    @Test
    void getStaffByIdWithCardDto() {
        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(Staff.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .phone("1234567890")
                .email("test")
                .role(Role.builder().jobTitle(JobTitle.DIRECTOR).build())
                .staffStatus(UserStatus.NEW)

                .build()));
        assertEquals("test", staffService.getStaffByIdWithCardDto(1L).email());
        verify(staffRepository, times(1)).findById(anyLong());
    }

    @Test
    void getStaffByIdWithEditDto() {
        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(Staff.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .phone("1234567890")
                .email("test")
                .role(Role.builder().jobTitle(JobTitle.DIRECTOR).build())
                .staffStatus(UserStatus.NEW)

                .build()));
        assertEquals("Test", staffService.getStaffByIdWithEditDto(1L).firstname());
        verify(staffRepository, times(1)).findById(anyLong());
    }

    @Test
    void getStaffByEmail() {
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder().build()));
        staffService.getStaffByEmail("Test");
        verify(staffRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getAllJobTitle() {
        assertEquals(5, staffService.getAllJobTitle().size());
    }

    @Test
    void getAllStatus() {
        assertEquals(3, staffService.getAllStatus().size());
    }

    @Test
    void getMainDirector() {
        Staff staff = Staff.builder().id(1L).role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build();
        when(staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(staff));
        assertEquals(staff, staffService.getMainDirector());
        verify(staffRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));

    }

    @Test
    void getAllStaff() {
        List<Staff> staffList = List.of(Staff.builder().id(1L).role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build());
        when(staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(staffList);
        assertEquals(staffList, staffService.getAllStaff());
        verify(staffRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void getAllStaffMaster() {
        List<Staff> staffList = List.of(Staff.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .role(Role.builder()
                        .jobTitle(JobTitle.DIRECTOR)
                        .build()
                )
                .build());
        when(staffRepository.findAll((Specification<Staff>) any())).thenReturn(staffList);
        assertEquals(1, staffService.getAllStaffMaster(StaffMasterRequest.builder().build()).size());
        verify(staffRepository, times(1)).findAll((Specification<Staff>) any());
    }

    @Test
    void getAllStaffManager() {
        Page<Staff> staffList = new PageImpl<>(List.of(Staff.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .role(Role.builder()
                        .jobTitle(JobTitle.DIRECTOR)
                        .build()
                )
                .build()));
        when(staffRepository.findAll((Specification<Staff>) any(), any(PageRequest.class))).thenReturn(staffList);
        assertEquals(1, staffService.getAllStaffManager(1, "Test").getTotalElements());
        verify(staffRepository, times(1)).findAll((Specification<Staff>) any(), any(PageRequest.class));
    }

    @Test
    void getAllStaffDto() {
        Page<Staff> staffList = new PageImpl<>(List.of(Staff.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .phone("1234567890")
                .email("test")
                .role(Role.builder()
                        .jobTitle(JobTitle.DIRECTOR)
                        .build())
                .staffStatus(UserStatus.NEW)
                .build()));
        when(staffRepository.findAll((Specification<Staff>) any(), any(PageRequest.class))).thenReturn(staffList);
        assertEquals(1, staffService.getAllStaffDto(StaffRequest.builder().pageIndex(1).build()).getTotalElements());
        verify(staffRepository, times(1)).findAll((Specification<Staff>) any(), any(PageRequest.class));
    }

    @Test
    void updateStaff() {
        Staff contextStaff = Staff.builder()
                .password("pass")
                .email("test@gmail.com")
                .role(Role.builder()
                        .securityLevelList(List.of(SecurityLevel.builder()
                                .id(1L).page(lab.space.my_house_24.enums.Page.ROLES)
                                .build()))
                        .build())
                .build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                contextStaff.getUsername(),
                contextStaff.getPassword(),
                Collections.singletonList(authority)
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, Collections.singletonList(authority));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);

        Staff staffDirector = Staff.builder()
                .id(1L)
                .email("Test")
                .role(Role.builder()
                        .id(1L)
                        .jobTitle(JobTitle.DIRECTOR)
                        .securityLevelList(
                                List.of(
                                        SecurityLevel.builder()
                                                .id(1L)
                                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(2L)
                                                .page(lab.space.my_house_24.enums.Page.METER_READING)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(3L)
                                                .page(lab.space.my_house_24.enums.Page.APARTMENTS)
                                                .build()
                                )
                        )
                        .build())
                .build();
        Staff staff = Staff.builder()
                .id(1L)
                .email("Test")
                .password("Test")
                .role(Role.builder()
                        .id(1L)
                        .jobTitle(JobTitle.DIRECTOR)
                        .securityLevelList(
                                List.of(
                                        SecurityLevel.builder()
                                                .id(1L)
                                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(2L)
                                                .page(lab.space.my_house_24.enums.Page.METER_READING)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(3L)
                                                .page(lab.space.my_house_24.enums.Page.APARTMENTS)
                                                .build()
                                )
                        )
                        .build())
                .build();
        when(staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(staffDirector));
        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(staffDirector));
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(staff));

        staffService.updateStaff(StaffUpdateRequest.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .phone("1234567890")
                .email("test@gmail.com")
                .role(JobTitle.DIRECTOR)
                .status(UserStatus.NEW)
                .password("Test")
                .confirmPassword("Test")
                .build());
        verify(staffRepository, times(1)).save(any());
        verify(staffRepository, times(1)).findById(anyLong());
        verify(staffRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void updateStaffNotDirector() {
        Staff contextStaff = Staff.builder()
                .password("pass")
                .email("test@gmail.com")
                .role(Role.builder()
                        .securityLevelList(List.of(SecurityLevel.builder()
                                .id(1L).page(lab.space.my_house_24.enums.Page.ROLES)
                                .build()))
                        .build())
                .build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                contextStaff.getUsername(),
                contextStaff.getPassword(),
                Collections.singletonList(authority)
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, Collections.singletonList(authority));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);

        Staff staffDirector = Staff.builder()
                .id(1L)
                .email("Test")
                .role(Role.builder()
                        .id(1L)
                        .jobTitle(JobTitle.DIRECTOR)
                        .securityLevelList(
                                List.of(
                                        SecurityLevel.builder()
                                                .id(1L)
                                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(2L)
                                                .page(lab.space.my_house_24.enums.Page.METER_READING)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(3L)
                                                .page(lab.space.my_house_24.enums.Page.APARTMENTS)
                                                .build()
                                )
                        )
                        .build())
                .build();
        when(staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(staffDirector));
        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(staffDirector));

        staffService.updateStaff(StaffUpdateRequest.builder()
                .id(2L)
                .firstname("Test")
                .lastname("Test")
                .phone("1234567890")
                .email("test@gmail.com")
                .role(JobTitle.DIRECTOR)
                .status(UserStatus.NEW)
                .password("Test")
                .confirmPassword("Test")
                .build());
        verify(staffRepository, times(1)).save(any());
        verify(staffRepository, times(1)).findById(anyLong());
        verify(staffRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void updateStaffBadRequest() {
        Staff contextStaff = Staff.builder()
                .password("pass")
                .email("test@gmail.com")
                .role(Role.builder()
                        .securityLevelList(List.of(SecurityLevel.builder()
                                .id(1L).page(lab.space.my_house_24.enums.Page.ROLES)
                                .build()))
                        .build())
                .build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                contextStaff.getUsername(),
                contextStaff.getPassword(),
                Collections.singletonList(authority)
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, Collections.singletonList(authority));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);

        Staff staffDirector = Staff.builder()
                .id(1L)
                .email("Test")
                .role(Role.builder()
                        .id(1L)
                        .jobTitle(JobTitle.DIRECTOR)
                        .securityLevelList(
                                List.of(
                                        SecurityLevel.builder()
                                                .id(1L)
                                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(2L)
                                                .page(lab.space.my_house_24.enums.Page.METER_READING)
                                                .build(),
                                        SecurityLevel.builder()
                                                .id(3L)
                                                .page(lab.space.my_house_24.enums.Page.APARTMENTS)
                                                .build()
                                )
                        )
                        .build())
                .build();
        when(staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(staffDirector));
        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(staffDirector));

        assertThrows(IllegalArgumentException.class, () -> {
            staffService.updateStaff(StaffUpdateRequest.builder()
                    .id(1L)
                    .firstname("Test")
                    .lastname("Test")
                    .phone("1234567890")
                    .email("test@gmail.com")
                    .role(JobTitle.MANAGER)
                    .status(UserStatus.NEW)
                    .password("Test")
                    .confirmPassword("Test")
                    .build());
        });

        verify(staffRepository, times(0)).save(any());
        verify(staffRepository, times(1)).findById(anyLong());
        verify(staffRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void saveStaff() {
        staffService.saveStaff(StaffSaveRequest.builder().firstname("Test").lastname("Test").phone("1234567890").email("test@gmail.com").role(JobTitle.DIRECTOR).build());
        verify(staffRepository, times(1)).save(any());
    }

    @Test
    void testSaveStaff() {
        staffService.saveStaff(Staff.builder().build());
        verify(staffRepository, times(1)).save(any());
    }

    @Test
    void activateStaff() {
        InviteRequest request = InviteRequest.builder().token("Test").email("Test").password("Test").confirmPassword("Test").build();
        when(jwtService.extractUsername(anyString())).thenReturn("Test");
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder()
                .password("pass")
                .email("test@gmail.com")
                .role(Role.builder()
                        .securityLevelList(List.of(SecurityLevel.builder()
                                .id(1L)
                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                .build()))
                        .build())
                .build()));

        staffService.activateStaff(request);
        verify(staffRepository, times(2)).findByEmail(anyString());
        verify(jwtService, times(1)).extractUsername(anyString());
    }

    @Test
    void forgotPasswordStaff() {
        ForgotPassRequest request = ForgotPassRequest.builder().token("Test").password("Test").confirmPassword("Test").build();
        when(jwtService.extractUsername(anyString())).thenReturn("Test");
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder()
                .password("pass")
                .email("test@gmail.com")
                .role(Role.builder()
                        .securityLevelList(List.of(SecurityLevel.builder()
                                .id(1L)
                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                .build()))
                        .build())
                .build()));

        staffService.forgotPasswordStaff(request);
        verify(staffRepository, times(2)).findByEmail(anyString());
        verify(jwtService, times(1)).extractUsername(anyString());
        verify(customMailSender, times(1)).send(anyString(), any(), any());
    }

    @Test
    void deleteStaff() {
        Staff staff = Staff.builder().id(1L).role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build();
        when(staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(staff));
        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(Staff.builder().build()));
        staffService.deleteStaff(2L);
        verify(staffRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
        verify(staffRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteStaffBadRequest() {
        Staff staff = Staff.builder().id(1L).role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build();
        when(staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(staff));
        assertThrows(IllegalArgumentException.class, () -> {
            staffService.deleteStaff(1L);
        });
        verify(staffRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
        verify(staffRepository, times(0)).findById(anyLong());
    }

    @Test
    void loadUserByUsername() {
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder()
                .password("pass")
                .email("test@gmail.com")
                .role(Role.builder()
                        .securityLevelList(List.of(SecurityLevel.builder()
                                .id(1L)
                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                .build()))
                        .build())
                .build()));
        staffService.loadUserByUsername("Test");
        verify(staffRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void loadUserByToken() {
        when(jwtService.extractUsername(anyString())).thenReturn("Test");
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder()
                .password("pass")
                .email("test@gmail.com")
                .role(Role.builder()
                        .securityLevelList(List.of(SecurityLevel.builder()
                                .id(1L)
                                .page(lab.space.my_house_24.enums.Page.ROLES)
                                .build()))
                        .build())
                .build()));
        staffService.loadUserByToken("Test");
        verify(staffRepository, times(1)).findByEmail(anyString());
        verify(jwtService, times(1)).extractUsername(anyString());
    }

    @Test
    void getAllStaffDtoForHouse() {
        List<Staff> staffList = List.of(Staff.builder()
                .id(1L)
                .firstname("Test")
                .lastname("Test")
                .role(Role.builder()
                        .jobTitle(JobTitle.DIRECTOR)
                        .build()
                )
                .build());
        when(staffRepository.findAll()).thenReturn(staffList);
        assertEquals(1, staffService.getAllStaffDtoForHouse().size());
        verify(staffRepository, times(1)).findAll();
    }

    @Test
    void getCurrentStaff() {
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

        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder().id(1L).build()));
        staffService.getCurrentStaff();
        verify(staffRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void getCurrentStaffForHeader() {
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

        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder().id(1L).firstname("Test").lastname("Test").email("test").role(Role.builder().securityLevelList(List.of(SecurityLevel.builder().page(lab.space.my_house_24.enums.Page.ROLES).build())).build()).build()));
        staffService.getCurrentStaffForHeader();
        verify(staffRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void changeTheme() {
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

        when(staffRepository.findById(anyLong())).thenReturn(Optional.of(Staff.builder().build()));
        when(staffRepository.findByEmail(anyString())).thenReturn(Optional.of(Staff.builder().id(1L).build()));
        staffService.changeTheme(true);
        verify(staffRepository, times(1)).findById(anyLong());
        verify(staffRepository, times(1)).findByEmail(anyString());
        verify(staffRepository, times(1)).save(any());
    }
}