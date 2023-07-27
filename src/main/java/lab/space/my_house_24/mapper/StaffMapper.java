package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.staff.StaffSaveRequest;
import lab.space.my_house_24.model.staff.StaffUpdateRequest;
import org.springframework.context.i18n.LocaleContextHolder;

public interface StaffMapper {
    static StaffResponse toSimpleDto(Staff staff) {
        return StaffResponse.builder()
                .fullName(staff.getFirstname() + " " + staff.getLastname())
                .email(staff.getEmail())
                .role(staff.getRole().getRole(LocaleContextHolder.getLocale()))
                .status(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                .build();
    }

    static StaffResponse toDto(Staff staff) {
        return StaffResponse.builder()
                .firstname(staff.getFirstname())
                .lastname(staff.getLastname())
                .email(staff.getEmail())
                .role(staff.getRole().getRole(LocaleContextHolder.getLocale()))
                .status(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                .build();
    }

    static Staff saveStaff(StaffSaveRequest staffSaveRequest) {
        return Staff.builder()
                .firstname(staffSaveRequest.firstname())
                .lastname(staffSaveRequest.lastname())
                .email(staffSaveRequest.email())
                .role(staffSaveRequest.role())
                .staffStatus(staffSaveRequest.userStatus())
                .build();
    }

    static Staff saveStaff(StaffUpdateRequest staffUpdateRequest, Staff staff) {
        return staff
                .setFirstname(staffUpdateRequest.firstname())
                .setLastname(staffUpdateRequest.lastname())
                .setEmail(staffUpdateRequest.email())
                .setRole(staffUpdateRequest.role())
                .setStaffStatus(staffUpdateRequest.userStatus());
    }
}
