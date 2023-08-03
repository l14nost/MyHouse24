package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.mapper.StaffMapper;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.staff.StaffSaveRequest;
import lab.space.my_house_24.model.staff.StaffUpdateRequest;
import lab.space.my_house_24.repository.StaffRepository;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService, UserDetailsService {

    private final StaffRepository staffRepository;
    private final RoleService roleService;

    @Override
    public Staff getStaffById(Long id) {
        log.info("Try to search Staff by id " + id);
        return staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found by id " + id));
    }

    @Override
    public StaffResponse getStaffByIdWithSimpleDto(Long id) {
        log.info("Try convert to Simple Dto Staff by id " + id);
        return StaffMapper.toSimpleDto(getStaffById(id));
    }

    @Override
    public StaffResponse getStaffByIdWithDto(Long id) {
        log.info("Try convert to Dto Staff by id " + id);
        return StaffMapper.toDto(getStaffById(id));
    }

    @Override
    public Staff getStaffByEmail(String email) {
        log.info("Try to search Staff by email " + email);
        return staffRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found by email " + email));
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
    public List<StaffResponse> getAllStaffDto() {
        log.info("Search all Staff and convert in DTO");
        return staffRepository.findAll()
                .stream()
                .map(StaffMapper::toSimpleDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> updateStaff(StaffUpdateRequest staffUpdateRequest) {
        log.info("Try to update Staff by id " + staffUpdateRequest.id());
        try {
            Staff director = getMainDirector();
            if (director.getId() != staffUpdateRequest.id().longValue()) {
                staffRepository.save(
                        StaffMapper.saveStaff(
                                staffUpdateRequest,
                                getStaffById(staffUpdateRequest.id()),
                                roleService
                        )
                );
                log.info("Success update Staff by id " + staffUpdateRequest.id());
                return ResponseEntity.ok().build();
            } else if (director.getId() == staffUpdateRequest.id().longValue()
                    && director.getRole().getJobTitle().equals(staffUpdateRequest.jobTitle())) {
                staffRepository.save(
                        StaffMapper.saveStaff(
                                staffUpdateRequest,
                                getStaffById(staffUpdateRequest.id()),
                                roleService
                        )
                );
                log.warn("Success update Main Director. id ->" + staffUpdateRequest.id());
                return ResponseEntity.ok().build();
            } else {
                log.error("Error update Staff with id " + staffUpdateRequest.id());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("It is not possible for the Director to change roles");
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
}
