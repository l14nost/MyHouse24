package lab.space.my_house_24.controller;

import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lab.space.my_house_24.service.impl.HouseServiceImpl;
import lab.space.my_house_24.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final HouseServiceImpl houseService;

    @GetMapping("/users")
    public String userMainPage(Model model){
        model.addAttribute("houseList", houseService.houseListForUserPage());
        return "admin/pages/user-main";
    }


    @PostMapping("/get-all-users")
    public ResponseEntity getAllUserSpecification(@RequestBody UserMainPageRequest userMainPageRequest){
        return ResponseEntity.ok(userService.getAllUserDto(userMainPageRequest));
    }

    @GetMapping("/user-card/{id}")
    public String userCard(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "admin/pages/user-card";
    }
}
