package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.model.role.RoleUpdateRequest;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping({"/", ""})
    public String showLogin() {
        return "/admin/pages/role/role";
    }

    @GetMapping("/get-all-roles")
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(roleService.getAllRoleDto());
    }

    @PostMapping("/update-all-roles")
    public ResponseEntity<?> updateStaffById(@Valid @RequestBody RoleUpdateRequest roleUpdateRequest,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }

        return roleService.updateAllRole(roleUpdateRequest);
    }

}
