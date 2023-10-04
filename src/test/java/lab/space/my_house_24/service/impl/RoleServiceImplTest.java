package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.Page;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.role.PageResponse;
import lab.space.my_house_24.model.role.RoleUpdateRequest;
import lab.space.my_house_24.repository.RoleRepository;
import lab.space.my_house_24.repository.StaffRepository;
import lab.space.my_house_24.service.SecurityLevelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private SecurityLevelService securityLevelService;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void getRoleByJobTitle() {
        when(roleRepository.findByJobTitle(any())).thenReturn(Optional.of(Role.builder().build()));
        assertEquals(Role.builder().build(), roleService.getRoleByJobTitle(JobTitle.MANAGER));
    }

    @Test
    void getAllRole() {
        when(roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(List.of(Role.builder().build()));
        assertEquals(List.of(Role.builder().build()), roleService.getAllRole());
    }

    @Test
    void getAllRoleDto() {
        when(roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(
                List.of(
                        Role.builder()
                                .jobTitle(JobTitle.ACCOUNTANT)
                                .securityLevelList(List.of())
                                .build(),
                        Role.builder()
                                .jobTitle(JobTitle.MANAGER)
                                .securityLevelList(List.of())
                                .build(),
                        Role.builder()
                                .jobTitle(JobTitle.ELECTRIC)
                                .securityLevelList(List.of())
                                .build(),
                        Role.builder()
                                .jobTitle(JobTitle.PLUMBER)
                                .securityLevelList(List.of())
                                .build()
                )
        );
        roleService.getAllRoleDto();
        verify(roleRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void saveRole() {
        roleService.saveRole(Role.builder().build());
        verify(roleRepository, times(1)).save(any());
    }

    @Test
    void updateRole() {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").role(Role.builder().securityLevelList(List.of(SecurityLevel.builder().id(1L).page(Page.ROLES).build())).build()).build();

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

        when(staffRepository.findByEmail(any())).thenReturn(Optional.of(staff));
        when(roleRepository.findByJobTitle(any())).thenReturn(Optional.of(Role.builder().securityLevelList(List.of()).build()));
        roleService.updateRole(
                PageResponse.builder()
                        .apartments(false)
                        .roles(false)
                        .apartmentsOwner(false)
                        .settingsPage(false)
                        .bills(false)
                        .bankBooks(false)
                        .cashBox(false)
                        .houses(false)
                        .mastersApplications(false)
                        .messages(false)
                        .meterReading(false)
                        .rates(false)
                        .requisites(false)
                        .services(false)
                        .staff(false)
                        .statistics(false)
                        .build(),
                JobTitle.MANAGER
        );
        verify(roleRepository, times(1)).save(any());
        verify(staffRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void updateAllRole() {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").role(Role.builder().securityLevelList(List.of(SecurityLevel.builder().id(1L).page(Page.ROLES).build())).build()).build();

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

        when(staffRepository.findByEmail(any())).thenReturn(Optional.of(staff));
        when(roleRepository.findByJobTitle(any())).thenReturn(Optional.of(Role.builder().securityLevelList(List.of()).build()));

        roleService.updateAllRole(RoleUpdateRequest.builder()
                .manager(PageResponse.builder()
                        .apartments(false)
                        .roles(false)
                        .apartmentsOwner(false)
                        .settingsPage(false)
                        .bills(false)
                        .bankBooks(false)
                        .cashBox(false)
                        .houses(false)
                        .mastersApplications(false)
                        .messages(false)
                        .meterReading(false)
                        .rates(false)
                        .requisites(false)
                        .services(false)
                        .staff(false)
                        .statistics(false)
                        .build())
                .accountant(PageResponse.builder()
                        .apartments(false)
                        .roles(false)
                        .apartmentsOwner(false)
                        .settingsPage(false)
                        .bills(false)
                        .bankBooks(false)
                        .cashBox(false)
                        .houses(false)
                        .mastersApplications(false)
                        .messages(false)
                        .meterReading(false)
                        .rates(false)
                        .requisites(false)
                        .services(false)
                        .staff(false)
                        .statistics(false)
                        .build())
                .electrician(PageResponse.builder()
                        .apartments(false)
                        .roles(false)
                        .apartmentsOwner(false)
                        .settingsPage(false)
                        .bills(false)
                        .bankBooks(false)
                        .cashBox(false)
                        .houses(false)
                        .mastersApplications(false)
                        .messages(false)
                        .meterReading(false)
                        .rates(false)
                        .requisites(false)
                        .services(false)
                        .staff(false)
                        .statistics(false)
                        .build())
                .plumber(PageResponse.builder()
                        .apartments(false)
                        .roles(false)
                        .apartmentsOwner(false)
                        .settingsPage(false)
                        .bills(false)
                        .bankBooks(false)
                        .cashBox(false)
                        .houses(false)
                        .mastersApplications(false)
                        .messages(false)
                        .meterReading(false)
                        .rates(false)
                        .requisites(false)
                        .services(false)
                        .staff(false)
                        .statistics(false)
                        .build())
                .build());
        verify(roleRepository, times(4)).save(any());
    }
}