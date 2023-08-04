package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.staff.StaffEditResponse;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.staff.StaffSaveRequest;
import lab.space.my_house_24.model.staff.StaffUpdateRequest;
import lab.space.my_house_24.service.RoleService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface StaffMapper {
    static StaffResponse toSimpleDto(Staff staff) {
        return StaffResponse.builder()
                .id(staff.getId())
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale()))
                .status(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                .build();
    }

    static StaffResponse toCarDto(Staff staff) {
        return StaffResponse.builder()
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(staff.getRole().getJobTitle().getJobTitle(LocaleContextHolder.getLocale()))
                .status(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                .build();
    }

    static StaffEditResponse toEditDto(Staff staff) {
        return StaffEditResponse.builder()
                .firstname(staff.getFirstname())
                .lastname(staff.getLastname())
                .phone(staff.getPhone())
                .email(staff.getEmail())
                .role(staff.getRole().getJobTitle())
                .status(staff.getStaffStatus())
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
                .staffStatus(staffSaveRequest.status())
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

        if (staffUpdateRequest.password() != null) {
            staff.setPassword(new BCryptPasswordEncoder().encode(staffUpdateRequest.password()));
        }
        return staff;
    }
}
