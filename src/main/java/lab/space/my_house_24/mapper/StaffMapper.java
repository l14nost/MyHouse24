package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.enums_response.JobTitleResponse;
import lab.space.my_house_24.model.enums_response.StatusResponse;
import lab.space.my_house_24.model.staff.*;
import lab.space.my_house_24.service.RoleService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static java.util.Objects.nonNull;

public interface StaffMapper {
    static StaffResponse toSimpleDto(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(JobTitleResponse
                        .builder()
                        .name(staff.getRole().getJobTitle().name())
                        .value(staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale()))
                        .build()
                )
                .status(StatusResponse
                        .builder()
                        .name(staff.getStaffStatus().name())
                        .value(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                        .build()
                )
                .build();
    }

    static StaffResponse toCarDto(Staff staff) {
        return StaffResponse.builder()
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(JobTitleResponse
                        .builder()
                        .name(staff.getRole().getJobTitle().name())
                        .value(staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale()))
                        .build()
                )
                .status(StatusResponse
                        .builder()
                        .name(staff.getStaffStatus().name())
                        .value(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                        .build()
                )
                .build();
    }

    static StaffEditResponse toEditDto(Staff staff) {
        return StaffEditResponse.builder()
                .firstname(staff.getFirstname())
                .lastname(staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(JobTitleResponse
                        .builder()
                        .name(staff.getRole().getJobTitle().name())
                        .value(staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale()))
                        .build()
                )
                .status(StatusResponse
                        .builder()
                        .name(staff.getStaffStatus().name())
                        .value(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                        .build()
                )
                .build();
    }

    static Staff saveStaff(StaffSaveRequest staffSaveRequest, RoleService roleService) {
        return Staff.builder()
                .firstname(staffSaveRequest.firstname())
                .lastname(staffSaveRequest.lastname())
                .phone(staffSaveRequest.phone())
                .email(staffSaveRequest.email())
                .password(new BCryptPasswordEncoder().encode(staffSaveRequest.password()))
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

        if (nonNull(staffUpdateRequest.password()) && !staffUpdateRequest.password().equals("") && !new BCryptPasswordEncoder().matches(staffUpdateRequest.password(),staff.getPassword()) ) {
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
}
