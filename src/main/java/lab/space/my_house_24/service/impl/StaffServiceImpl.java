package lab.space.my_house_24.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.mapper.EnumMapper;
import lab.space.my_house_24.mapper.StaffMapper;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.*;
import lab.space.my_house_24.repository.StaffRepository;
import lab.space.my_house_24.service.JwtService;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.service.StaffService;
import lab.space.my_house_24.specification.StaffSpecification;
import lab.space.my_house_24.util.CustomMailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService, UserDetailsService {

    private final StaffRepository staffRepository;
    private final RoleService roleService;
    private final StaffSpecification staffSpecification;
    private final CustomMailSender customMailSender;
    private final JwtService jwtService;
    private final String url = "http://localhost:7575/admin/";

    @Override
    public void sendInvite(InviteRequest inviteRequest) {
        log.info("Try to send invite by email " + inviteRequest.email());
        Staff staff = getStaffByEmail(inviteRequest.email());
        String token = jwtService.generateToken(staff);
        staff.setToken(token);
        staff.setTokenUsage(false);
        staff.setForgotTokenUsage(true);
        saveStaff(staff);
        customMailSender.send(staff.getEmail(), url + "auth/activate-staff/" + token, "Activate Account");
    }


    @Override
    public void sendForgotPasswordUrl(String email) {
        log.info("Try to send Forgot Pass url by email " + email);
        Staff staff = getStaffByEmail(email);
        String token = jwtService.generateToken(staff);
        staff.setForgotToken(token);
        staff.setTokenUsage(true);
        staff.setForgotTokenUsage(false);
        saveStaff(staff);
        customMailSender.send(staff.getEmail(), url + "login/forgot-password/" + token, "Forgot Password");
    }

    @Override
    public void sendUpdatePasswordWarning(String email, Locale locale) {
        log.info("Try to send update pass warn by email " + email);
        String text;
        String subject;
        if (locale.toLanguageTag().equals("uk")) {
            text = "Ваш пароль був змінений.";
            subject = "Зміна пароля";
        } else {
            text = "Your password has been changed.";
            subject = "Change Password";
        }
        customMailSender.send(email, text, subject);
    }

    @Override
    public Staff getStaffById(Long id) {
        log.info("Try to search Staff by id " + id);
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found by id " + id));
    }

    @Override
    public StaffResponse getStaffByIdWithCardDto(Long id) {
        log.info("Try convert to Card Dto Staff by id " + id);
        return StaffMapper.toCarDto(getStaffById(id));
    }

    @Override
    public StaffEditResponse getStaffByIdWithEditDto(Long id) {
        log.info("Try convert to Edit Dto Staff by id " + id);
        return StaffMapper.toEditDto(getStaffById(id));
    }

    @Override
    public Staff getStaffByEmail(String email) {
        log.info("Try to search Staff by email " + email);
        return staffRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found by email " + email));
    }

    @Override
    public List<EnumResponse> getAllJobTitle() {
        log.info("Try to get all jobTitle");
        return Arrays.stream(JobTitle.values())
                .map(jobTitle -> EnumMapper.toSimpleDto(
                                jobTitle.name(),
                                jobTitle.getJobTitle(LocaleContextHolder.getLocale())
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<EnumResponse> getAllStatus() {
        log.info("Try to get all status");
        return Arrays.stream(UserStatus.values())
                .map(status -> EnumMapper.toSimpleDto(
                        status.name(),
                        status.getUserStatus(LocaleContextHolder.getLocale())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Staff getMainDirector() throws EntityNotFoundException {
        log.info("Try to search main Director");
        return getAllStaff()
                .stream()
                .filter((staffList) -> staffList.getRole().getJobTitle() == JobTitle.DIRECTOR)
                .min(Comparator.comparing(Staff::getId))
                .orElseThrow(() -> new EntityNotFoundException("Staff with role Director not found"));
    }

    @Override
    public List<Staff> getAllStaff() {
        log.info("Search all Staff");
        return staffRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<StaffResponse> getAllStaffDto(StaffRequest request) {
        log.info("Search all Staff and convert in DTO");
        final int DEFAULT_PAGE_SIZE = 10;
        return staffRepository.findAll(
                staffSpecification.getStaffByRequest(request),
                PageRequest.of(request.pageIndex(), DEFAULT_PAGE_SIZE)).map(StaffMapper::toSimpleDto);
    }

    @Override
    public ResponseEntity<?> updateStaff(StaffUpdateRequest staffUpdateRequest) {
        log.info("Try to update Staff by id " + staffUpdateRequest.id());
        try {
            Staff director = getMainDirector();
            Staff staff = getStaffById(staffUpdateRequest.id());
            if (director.getId() != staffUpdateRequest.id().longValue()) {
                if (nonNull(staffUpdateRequest.password()) &&
                        !staffUpdateRequest.password().equals("") &&
                        !new BCryptPasswordEncoder().matches(staffUpdateRequest.password(), staff.getPassword())) {
                    sendUpdatePasswordWarning(
                            staff.getEmail(),
                            LocaleContextHolder.getLocale()
                    );
                }
                staffRepository.save(
                        StaffMapper.saveStaff(
                                staffUpdateRequest,
                                staff,
                                roleService
                        )
                );

                log.info("Success update Staff by id " + staffUpdateRequest.id());
                return ResponseEntity.ok().build();
            } else if (director.getId() == staffUpdateRequest.id().longValue()
                    && director.getRole().getJobTitle().equals(staffUpdateRequest.role())) {
                if (nonNull(staffUpdateRequest.password()) &&
                        !staffUpdateRequest.password().equals("") &&
                        !new BCryptPasswordEncoder().matches(staffUpdateRequest.password(), staff.getPassword())) {
                    sendUpdatePasswordWarning(
                            staff.getEmail(),
                            LocaleContextHolder.getLocale()
                    );
                }
                staffRepository.save(
                        StaffMapper.saveStaff(
                                staffUpdateRequest,
                                staff,
                                roleService
                        )
                );
                log.warn("Success update Main Director. id ->" + staffUpdateRequest.id());
                return ResponseEntity.ok().build();
            } else {
                log.error("Error update Staff with id " + staffUpdateRequest.id());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("It is not possible for the Director to change");
            }

        } catch (EntityNotFoundException e) {
            log.error("Staff not found with id " + staffUpdateRequest.id());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public void saveStaff(StaffSaveRequest staffSaveRequest) {
        log.info("Try to save Staff");
        staffRepository.save(
                StaffMapper.saveStaff(staffSaveRequest, roleService));
        log.info("Success save Staff");
    }

    @Override
    public void saveStaff(Staff staff) {
        log.info("Try to save Staff");
        staffRepository.save(staff);
        log.info("Success save Staff");
    }

    @Override
    public ResponseEntity<?> activateStaff(InviteRequest request) {
        try {
            log.info("Try to activate Staff");
            saveStaff(
                    StaffMapper.activateStaff(
                            request,
                            getStaffByEmail(loadUserByToken(request.token()).getUsername())
                    )
            );
            log.info("Success activate Staff");
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Staff not found with email" + request.email());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> forgotPasswordStaff(ForgotPassRequest request) {
        try {
            log.info("Try to Forgot Password Staff");
            Staff staff = getStaffByEmail(loadUserByToken(request.token()).getUsername());
            saveStaff(
                    StaffMapper.forgotPasswordStaff(
                            request,
                            staff
                    )
            );
            sendUpdatePasswordWarning(
                    staff.getEmail(),
                    LocaleContextHolder.getLocale()
            );
            log.info("Success Forgot Password Staff");
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Staff not found with email" + loadUserByToken(request.token()).getUsername());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteStaff(Long id) {
        try {
            Staff director = getMainDirector();
            log.info("Try to delete Staff with id " + id);
            if (director.getId() == id.longValue()
                    && director.getRole().getJobTitle().equals(JobTitle.DIRECTOR)) {
                log.warn("Attempt to remove the Main Director with id " + id);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Director cannot be removed");
            } else {
                staffRepository.delete(getStaffById(id));
                log.info("Success delete Staff with id " + id);
                return ResponseEntity.ok().build();
            }
        } catch (EntityNotFoundException e) {
            log.error("Staff not found with id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = getStaffByEmail(email);
        return new org.springframework.security.core.userdetails.User(
                userDetails.getUsername(), userDetails.getPassword(), new ArrayList<>()
        );
    }

    @Override
    public UserDetails loadUserByToken(String token) throws JWTVerificationException {
        log.info("Try to search by token");
        String email = jwtService.extractUsername(token);
        return loadUserByUsername(email);
    }
}
