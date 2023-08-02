package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.staff.StaffSaveRequest;
import lab.space.my_house_24.model.staff.StaffUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StaffService {
    Staff getStaffById(Long id);
    StaffResponse getStaffByIdWithSimpleDto(Long id);
    StaffResponse getStaffByIdWithDto(Long id);

    Staff getStaffByEmail(String email);
    Staff getMainDirector();

    List<Staff> getAllStaff();

    List<StaffResponse> getAllStaffDto();

    ResponseEntity<?> updateStaff(StaffUpdateRequest staffUpdateRequest);

    void saveStaff(StaffSaveRequest staffSaveRequest);
    void saveStaff(Staff staff);

    ResponseEntity<?> deleteStaff(Long id);

}
