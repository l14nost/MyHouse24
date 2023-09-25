package lab.space.my_house_24.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.*;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.JwtServiceForUser;
import lab.space.my_house_24.service.UserService;
import lab.space.my_house_24.util.ErrorMapper;
import lab.space.my_house_24.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HouseService houseService;
    private final UserValidator userValidator;
    private final JwtServiceForUser jwtServiceForUser;

    @GetMapping({"/", ""})
    public String userMainPage(Model model) {
        model.addAttribute("houseList", houseService.houseListForTable());
        return "admin/pages/users/user-main";
    }


    @PostMapping("/get-all-users")
    public ResponseEntity getAllUserSpecification(@RequestBody UserMainPageRequest userMainPageRequest) {
        return ResponseEntity.ok(userService.getAllUserDto(userMainPageRequest));
    }

    @GetMapping("/user-card/{id}")
    public String userCard(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "admin/pages/users/user-card";
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/add-user")
    public String addUser() {
        return "admin/pages/users/user-add";
    }

    @PostMapping("/add-user")
    public ResponseEntity addUserPost(@ModelAttribute @Valid UserAddRequest userAddRequest, BindingResult result) {
        if (userAddRequest.date() != null) {
            userValidator.ageValid(userAddRequest.date(), result, "UserAddRequest");
        }
        if (userAddRequest.password() != null && userAddRequest.confirmPassword() != null) {
            userValidator.passwordMatch(userAddRequest.password(), userAddRequest.confirmPassword(), result, "UserAddRequest");
        }
        if (userAddRequest.email() != null) {
            userValidator.uniqueEmail(userAddRequest.email(), 0L, result, "UserAddRequest");
        }
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        userService.save(userAddRequest);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/edit-user/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserEditResponse userEditResponse = userService.findByIdEdit(id);
        model.addAttribute("user", userEditResponse);
        return "admin/pages/users/user-edit";
    }

    @PutMapping("/edit-user/{id}")
    public ResponseEntity editUserPut(@PathVariable Long id, @ModelAttribute @Valid UserEditRequest userEditRequest, BindingResult result) {
        if (userEditRequest.date() != null) {
            userValidator.ageValid(userEditRequest.date(), result, "UserAddRequest");
        }
        if (!userEditRequest.password().isEmpty() && !userEditRequest.confirmPassword().isEmpty()) {
            userValidator.passwordMatch(userEditRequest.password(), userEditRequest.confirmPassword(), result, "UserEditRequest");
        }
        if (userEditRequest.email() != null) {
            userValidator.uniqueEmail(userEditRequest.email(), id, result, "UserAddRequest");
        }
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        userService.update(userEditRequest, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invite-user")
    public String inviteUser() {
        return "admin/pages/users/user-invite";
    }

    @PostMapping("/invite-user")
    public ResponseEntity inviteUser(@RequestBody @Valid UserInviteRequest userInviteRequest, BindingResult result) {
        if (userInviteRequest.email() != null) {
            userValidator.uniqueEmail(userInviteRequest.email(), 0L, result, "UserInviteRequest");
        }
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        userService.inviteUser(userInviteRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    @GetMapping("/get-users/apartment-table")
    @ResponseBody
    public Page<UserResponseForTable> userForApartmentTable(@RequestParam Integer page, @RequestParam String search){
        return userService.userResponseForSelect(page, search);
    }

    @PostMapping("/invite-send")
    public ResponseEntity sendForgot(@RequestParam String email){
        if (!email.isBlank()) {
            if (!userValidator.existByEmail(email)){
                return ResponseEntity.badRequest().build();
            }
        }
        userService.sendActivateLetter(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activate/{token}")
    public String activePage(@PathVariable String token, Model model) {
        String email = userService.loadUserByToken(token);
        if (!jwtServiceForUser.isTokenValid(
                token,
                email,
                userService.getUserByEmail(email))) {
            return "admin/pages/users/activate-error";
        } else {
            model.addAttribute("token", token);
            return "admin/pages/users/activate";
        }
    }

    @PutMapping("/activate/{token}")
    public ResponseEntity<?> active(@PathVariable String token, @Valid @RequestBody ForgotPassRequest forgotPassRequest,
                                                 BindingResult bindingResult) {
        if (forgotPassRequest.password()!=null && forgotPassRequest.confirmPassword()!=null) {
            userValidator.passwordMatch(forgotPassRequest.password(), forgotPassRequest.confirmPassword(), bindingResult, "ForgotPassRequest");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        userService.activate(forgotPassRequest, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-users-for-header")
    public ResponseEntity getUserForHeader() {
        return ResponseEntity.ok(userService.usersByStatus(UserStatus.NEW));
    }
}
