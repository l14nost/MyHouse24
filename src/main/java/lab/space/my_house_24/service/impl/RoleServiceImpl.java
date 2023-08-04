package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.mapper.RoleMapper;
import lab.space.my_house_24.model.role.PageResponse;
import lab.space.my_house_24.model.role.RoleResponse;
import lab.space.my_house_24.model.role.RoleSimpleResponse;
import lab.space.my_house_24.model.role.RoleUpdateRequest;
import lab.space.my_house_24.repository.RoleRepository;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.service.SecurityLevelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
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
    public List<RoleSimpleResponse> getAllRoleSimpleDto() {
        log.info("Try to convert Roles in Dto");
        return RoleMapper.toSimpleDtoList(getAllRole());
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
    }

    @Override
    public ResponseEntity<?> updateAllRole(RoleUpdateRequest roleUpdateRequest) {
        try {
            log.info("Try to update Roles");
            updateRole(roleUpdateRequest.accountant(), JobTitle.ACCOUNTANT);
            updateRole(roleUpdateRequest.manager(), JobTitle.MANAGER);
            updateRole(roleUpdateRequest.electrician(), JobTitle.ELECTRIC);
            updateRole(roleUpdateRequest.plumber(), JobTitle.PLUMBER);
            log.info("Success update Roles");
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error update Roles");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
