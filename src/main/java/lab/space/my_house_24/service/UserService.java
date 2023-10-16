package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    Page<UserResponse> getAllUserDto(UserMainPageRequest userMainPageRequest);

    UserCardResponse findById(Long id);

    UserEditResponse findByIdEdit(Long id);

    void deleteById(Long id);

    User findEntityById(Long id);

    void save(UserAddRequest userAddRequest);

    void save(User user);

    void update(UserEditRequest userEditRequest, Long id);

    void inviteUser(UserInviteRequest userInviteRequest);

    List<UserResponseForTable> userListForTable();

    Page<UserResponseForTable> userResponseForSelect(Integer page, String search);

    Page<UserResponseForMastersApplication> getAllUsersForMastersApplication(Integer pageIndex, String search);

    Long countByStatus(UserStatus userStatus);

    void sendActivateLetter(String email);

    String loadUserByToken(String token);

    void activate(ForgotPassRequest forgotPassRequest, String token);

    List<UserResponseForHeader> usersByStatus(UserStatus userStatus);

    List<User> findAll();
}
