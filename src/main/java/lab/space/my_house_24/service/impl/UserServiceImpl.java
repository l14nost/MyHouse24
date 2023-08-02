package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.mapper.UserMapper;
import lab.space.my_house_24.model.user.UserCardResponse;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lab.space.my_house_24.model.user.UserAddRequest;
import lab.space.my_house_24.model.user.UserResponse;
import lab.space.my_house_24.repository.UserRepository;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.specification.UserSpecification;
import lab.space.my_house_24.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()-> new EntityNotFoundException("User by email "+email+" is not found"));
    }

    @Override
    public Page<UserResponse> getAllUserDto(UserMainPageRequest userMainPageRequest) {
        UserSpecification userSpecification = UserSpecification.builder().userMainPageRequest(userMainPageRequest).build();
        return userRepository.findAll(userSpecification, PageRequest.of(userMainPageRequest.page(),10)).map(UserMapper::entityToMainPageDto);
    }

    @Override
    public UserCardResponse findById(Long id) {
        return UserMapper.entityToCardDto(userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User by id "+id+" is not found")));
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User by id "+id+" is not found"));
        FileHandler.deleteFile(user.getFilename());
        userRepository.deleteById(id);

    }

    @Override
    public void save(UserAddRequest userAddRequest) {
        User user = User.builder()
                .addDate(Instant.now())
                .date(userAddRequest.date().atStartOfDay(ZoneId.systemDefault()).toInstant())
                .filename(FileHandler.saveFile(userAddRequest.img()))
                .email(userAddRequest.email())
                .firstname(userAddRequest.firstname())
                .lastname(userAddRequest.lastname())
                .surname(userAddRequest.surname())
                .number(userAddRequest.number())
                .telegram(userAddRequest.telegram())
                .viber(userAddRequest.viber())
                .password(new BCryptPasswordEncoder().encode(userAddRequest.password()))
                .userStatus(userAddRequest.status())
                .notes(userAddRequest.notes())
                .duty(false)
                .build();
        userRepository.save(user);
    }


}
