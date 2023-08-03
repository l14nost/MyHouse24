package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Role;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.model.role.PageResponse;
import lab.space.my_house_24.model.role.RoleResponse;
import lab.space.my_house_24.model.role.RoleUpdateRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {

    Role getRoleByJobTitle(JobTitle jobTitle);

    List<Role> getAllRole();
    RoleResponse getAllRoleDto();

    void saveRole(Role role);

    void updateRole(PageResponse pageResponse,JobTitle jobTitle);

    ResponseEntity<?> updateAllRole(RoleUpdateRequest role);

}
