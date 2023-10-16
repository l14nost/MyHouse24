package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface StaffService {

    void sendInvite(InviteRequest inviteRequest);

    void sendForgotPasswordUrl(String email);

    void sendUpdatePasswordWarning(String email);

    Staff getStaffById(Long id);

    StaffResponse getStaffByIdWithCardDto(Long id);

    StaffEditResponse getStaffByIdWithEditDto(Long id);

    Staff getStaffByEmail(String email);

    List<EnumResponse> getAllJobTitle();

    List<EnumResponse> getAllStatus();

    Staff getMainDirector();

    List<Staff> getAllStaff();

    Page<StaffResponse> getAllStaffMaster(StaffMasterRequest request);

    Page<StaffResponse> getAllStaffManager(Integer pageIndex, String fullNameStaff);

    Page<StaffResponse> getAllStaffDto(StaffRequest request);

    void updateStaff(StaffUpdateRequest staffUpdateRequest);

    void saveStaff(StaffSaveRequest staffSaveRequest);

    void saveStaff(Staff staff);

    void activateStaff(InviteRequest request);

    void forgotPasswordStaff(ForgotPassRequest request);

    void deleteStaff(Long id);

    UserDetails loadUserByToken(String token);

    List<StaffResponseForHouseAdd> getAllStaffDtoForHouse(String search);

    Long getCurrentStaff();

    StaffResponseForHeader getCurrentStaffForHeader();

    void changeTheme(Boolean theme);
}
