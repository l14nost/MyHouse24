package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.staff.StaffResponse;

import java.util.List;

public interface StaffService {
    Staff getStaffByEmail(String email);
    List<StaffResponse> getAllStaffDto();
}
