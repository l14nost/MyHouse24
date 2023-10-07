package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.staff.*;
import lab.space.my_house_24.service.RoleService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static java.util.Objects.nonNull;

public interface StaffMapper {
    static StaffResponse toSimpleDto(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(
                        EnumMapper.toSimpleDto(
                                staff.getRole().getJobTitle().name(),
                                staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale())
                        )
                )
                .status(
                        EnumMapper.toSimpleDto(
                                staff.getStaffStatus().name(),
                                staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale())
                        )
                )
                .build();
    }

    static StaffResponse toStaffResponse(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .role(
                        EnumMapper.toSimpleDto(
                                staff.getRole().getJobTitle().name(),
                                staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale())
                        )
                )
                .build();
    }

    static StaffResponse toCarDto(Staff staff) {
        return StaffResponse.builder()
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(
                        EnumMapper.toSimpleDto(
                                staff.getRole().getJobTitle().name(),
                                staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale())
                        )
                )
                .status(
                        EnumMapper.toSimpleDto(
                                staff.getStaffStatus().name(),
                                staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale())
                        )
                )
                .build();
    }

    static StaffEditResponse toEditDto(Staff staff) {
        return StaffEditResponse.builder()
                .firstname(staff.getFirstname())
                .lastname(staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(
                        EnumMapper.toSimpleDto(
                                staff.getRole().getJobTitle().name(),
                                staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale())
                        )
                )
                .status(
                        EnumMapper.toSimpleDto(
                                staff.getStaffStatus().name(),
                                staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale())
                        )
                )
                .build();
    }

    static Staff saveStaff(StaffSaveRequest staffSaveRequest, RoleService roleService) {
        return Staff.builder()
                .firstname(staffSaveRequest.firstname())
                .lastname(staffSaveRequest.lastname())
                .phone(staffSaveRequest.phone())
                .email(staffSaveRequest.email())
                .password(new BCryptPasswordEncoder().encode(String.valueOf(UUID.randomUUID())))
                .role(roleService.getRoleByJobTitle(staffSaveRequest.role()))
                .staffStatus(UserStatus.NEW)
                .build();
    }

    static Staff saveStaff(StaffUpdateRequest staffUpdateRequest, Staff staff, RoleService roleService) {
        staff
                .setFirstname(staffUpdateRequest.firstname())
                .setLastname(staffUpdateRequest.lastname())
                .setPhone(staffUpdateRequest.phone())
                .setEmail(staffUpdateRequest.email())
                .setRole(roleService.getRoleByJobTitle(staffUpdateRequest.role()))
                .setStaffStatus(staffUpdateRequest.status());

        if (nonNull(staffUpdateRequest.password()) && !staffUpdateRequest.password().equals("") && !new BCryptPasswordEncoder().matches(staffUpdateRequest.password(), staff.getPassword())) {
            staff.setPassword(new BCryptPasswordEncoder().encode(staffUpdateRequest.password()));
        }
        return staff;
    }

    static Staff activateStaff(InviteRequest inviteRequest, Staff staff) {
        return staff
                .setPassword(new BCryptPasswordEncoder().encode(inviteRequest.password()))
                .setTokenUsage(true)
                .setStaffStatus(UserStatus.ACTIVE);
    }

    static Staff forgotPasswordStaff(ForgotPassRequest request, Staff staff) {
        return staff
                .setPassword(new BCryptPasswordEncoder().encode(request.password()))
                .setForgotTokenUsage(true);
    }

    static StaffResponseForHouseCard entityToDtoForHouseCard(Staff staff) {
        return StaffResponseForHouseCard.builder()
                .id(staff.getId())
                .fullName(staff.getLastname() + " " + staff.getFirstname())
                .role(staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale()))
                .build();
    }

    static StaffResponseForHouseAdd entityToDtoForHouseAdd(Staff staff) {
        return StaffResponseForHouseAdd.builder()
                .id(staff.getId())
                .fullName(staff.getLastname() + " " + staff.getFirstname())
                .build();
    }

    static StaffResponseForHeader entityToDtoForHeader(Staff staff) {
        return StaffResponseForHeader.builder()
                .id(staff.getId())
                .fullName(staff.getLastname() + " " + staff.getFirstname())
                .email(staff.getEmail())
                .permission(staff.getRole().getSecurityLevelList().stream().map(securityLevel -> securityLevel.getPage().name()).toList())
                .build();
    }
}
