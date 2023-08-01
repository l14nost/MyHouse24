package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.mapper.UserMapper;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lab.space.my_house_24.model.user.UserResponse;
import lab.space.my_house_24.repository.UserRepository;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()-> new EntityNotFoundException("User by email"+email+"is not found"));
    }

    @Override
    public Page<UserResponse> getAllUserDto(UserMainPageRequest userMainPageRequest) {
        UserSpecification userSpecification = UserSpecification.builder().userMainPageRequest(userMainPageRequest).build();
        return userRepository.findAll(userSpecification, PageRequest.of(userMainPageRequest.page(),10)).map(UserMapper::entityToDto);
    }

}
