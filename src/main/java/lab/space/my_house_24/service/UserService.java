package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.User;

import lab.space.my_house_24.model.user.UserCardResponse;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lab.space.my_house_24.model.user.UserAddRequest;
import lab.space.my_house_24.model.user.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    User getUserByEmail(String email);

    Page<UserResponse> getAllUserDto(UserMainPageRequest userMainPageRequest);

    UserCardResponse findById(Long id);
    void deleteById(Long id);

    void save(UserAddRequest userAddRequest);
}
