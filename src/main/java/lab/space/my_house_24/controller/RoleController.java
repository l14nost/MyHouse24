package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.model.role.RoleResponse;
import lab.space.my_house_24.model.role.RoleUpdateRequest;
import lab.space.my_house_24.service.RoleService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping({"/", ""})
    public ModelAndView showLogin() {
        return new ModelAndView("admin/pages/role/role");
    }

    @GetMapping("/get-all-roles")
    public ResponseEntity<RoleResponse> getAllStaff() {
        return ResponseEntity.ok(roleService.getAllRoleDto());
    }

    @PutMapping("/update-all-roles")
    public ResponseEntity<?> updateStaffById(@Valid @RequestBody RoleUpdateRequest roleUpdateRequest,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            roleService.updateAllRole(roleUpdateRequest);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
