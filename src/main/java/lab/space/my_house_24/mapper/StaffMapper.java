package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.staff.StaffResponse;
import org.springframework.context.i18n.LocaleContextHolder;

public interface StaffMapper {
    static StaffResponse toSimpleDto(Staff staff) {
        return StaffResponse.builder()
                .name(staff.getFirstname() + " " + staff.getLastname())
                .email(staff.getEmail())
                .role(staff.getRole().getRole(LocaleContextHolder.getLocale()))
                .status(staff.getStaffStatus().getUserStatus(LocaleContextHolder.getLocale()))
                .build();
    }
}
