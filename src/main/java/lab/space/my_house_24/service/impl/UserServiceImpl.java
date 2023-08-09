package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.User;
import lab.space.my_house_24.mapper.UserMapper;
import lab.space.my_house_24.model.user.*;
import lab.space.my_house_24.repository.UserRepository;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.specification.UserSpecification;
import lab.space.my_house_24.specification.UserSpecificationForTable;
import lab.space.my_house_24.util.FileHandler;
import lab.space.my_house_24.util.CustomMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CustomMailSender customMailSender;
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
    public UserEditResponse findByIdEdit(Long id) {
        return UserMapper.entityToEditDto(userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User by id "+id+" is not found")));
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User by id "+id+" is not found"));
        FileHandler.deleteFile(user.getFilename());
        userRepository.deleteById(id);

    }

    @Override
    public User findEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User by id "+id+" is not found"));
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

    @Override
    public void update(UserEditRequest userEditRequest,Long id) {
        boolean changePassword = false;
        User user = findEntityById(id);
        String filenameDelete = user.getFilename();

        user.setDate(userEditRequest.date().atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setFirstname(userEditRequest.firstname());
        user.setLastname(userEditRequest.lastname());
        user.setSurname(userEditRequest.surname());
        user.setNumber(userEditRequest.number());
        user.setEmail(userEditRequest.email());
        user.setTelegram(userEditRequest.telegram());
        user.setViber(userEditRequest.viber());
        user.setNotes(userEditRequest.notes());
        user.setUserStatus(userEditRequest.status());
        if (!userEditRequest.img().isEmpty()) {
            user.setFilename(FileHandler.saveFile(userEditRequest.img()));
            FileHandler.deleteFile(filenameDelete);
        }
        if (!userEditRequest.password().isEmpty()){
            user.setPassword(new BCryptPasswordEncoder().encode(userEditRequest.password()));
            changePassword = true;
        }
        userRepository.save(user);
        if (changePassword) {
            String textForSend = "Dear " + user.getLastname() + " " + user.getFirstname() + ", your password has been changed!\n" +
                    "For detail information contact our support team";
            customMailSender.send(user.getEmail(), textForSend, "Password Change Notification");
        }
    }

    @Override
    public void inviteUser(UserInviteRequest userInviteRequest) {
        String textForSend = "Dear friend,\n" +
                "We cordially invite you to join us.\n" +
                "We will be glad to see you in our application!";
        customMailSender.send(userInviteRequest.email(),textForSend,"Invite");
    }

    @Override
    public List<UserResponseForTable> userListForTable() {
        return userRepository.findAll().stream().map(UserMapper::entityToDtoForTable).toList();
    }


    public Page<UserResponseForTable> userResponseForTables(Integer page, String search){
        UserSpecificationForTable userSpecificationForTable = UserSpecificationForTable.builder().search(search).build();
        return userRepository.findAll(userSpecificationForTable, PageRequest.of(page,5)).map(UserMapper::entityToDtoForTable);
    }


}
