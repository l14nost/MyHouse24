package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    Page<UserResponse> getAllUserDto(UserMainPageRequest userMainPageRequest);

    UserCardResponse findById(Long id);
    UserEditResponse findByIdEdit(Long id);
    void deleteById(Long id);

    User findEntityById(Long id);

    void save(UserAddRequest userAddRequest);

    void update(UserEditRequest userEditRequest,Long id);

    void inviteUser(UserInviteRequest userInviteRequest);

    List<UserResponseForTable> userListForTable();
    Page<UserResponseForTable> userResponseForTables(Integer page, String search);

    List<UserResponseForMastersApplication> getAllUsersForMastersApplication();

    Long countByStatus(UserStatus userStatus);

    void sendActivateLetter(String email);

    String loadUserByToken(String token);

    void activate(ForgotPassRequest forgotPassRequest, String token);

    List<UserResponseForHeader> usersByStatus(UserStatus userStatus);
}
