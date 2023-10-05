package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.mapper.RoleMapper;
import lab.space.my_house_24.model.role.PageResponse;
import lab.space.my_house_24.model.role.RoleResponse;
import lab.space.my_house_24.model.role.RoleUpdateRequest;
import lab.space.my_house_24.repository.RoleRepository;
import lab.space.my_house_24.repository.StaffRepository;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.service.SecurityLevelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final SecurityLevelService securityLevelService;

    @Override
    public Role getRoleByJobTitle(JobTitle jobTitle) throws EntityNotFoundException {
        log.info("Try to search Staff by JobTitle " + jobTitle);
        return roleRepository.findByJobTitle(jobTitle)
                .orElseThrow(() -> new EntityNotFoundException("Role not found by JobTitle " + jobTitle));
    }

    @Override
    public List<Role> getAllRole() {
        log.info("Try to search Roles");
        return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public RoleResponse getAllRoleDto() {
        log.info("Try to convert Roles in Dto");
        return RoleMapper.toDto(getAllRole());
    }

    @Override
    public void saveRole(Role role) {
        log.info("Try to save Role");
        roleRepository.save(role);
        log.info("Success save Role");
    }

    @Override
    public void updateRole(PageResponse pageResponse, JobTitle jobTitle) throws EntityNotFoundException {
        log.info("Try to update Role");
        roleRepository.save(RoleMapper.toRole(pageResponse, getRoleByJobTitle(jobTitle), securityLevelService));
        log.info("Success update Role");

        Staff staff = getStaffByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(staff.getUsername(), staff.getPassword(), staff.getAuthorities()),
                staff.getPassword(),
                staff.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void updateAllRole(RoleUpdateRequest roleUpdateRequest) throws EntityNotFoundException {
        log.info("Try to update Roles");
        updateRole(roleUpdateRequest.accountant(), JobTitle.ACCOUNTANT);
        updateRole(roleUpdateRequest.manager(), JobTitle.MANAGER);
        updateRole(roleUpdateRequest.electrician(), JobTitle.ELECTRIC);
        updateRole(roleUpdateRequest.plumber(), JobTitle.PLUMBER);
        log.info("Success update Roles");
    }

    private Staff getStaffByEmail(String email) {
        log.info("Try to search Staff by email " + email);
        return staffRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found by email " + email));
    }
}
