package lab.space.my_house_24.controller;

import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.model.user.UserMainPageRequest;
import lab.space.my_house_24.service.impl.HouseServiceImpl;
import lab.space.my_house_24.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final HouseServiceImpl houseService;
    private UserStatus userStatus;

    @GetMapping("/users")
    public String userMainPage(@RequestBody(required = false) UserMainPageRequest userMainPageRequest, Model model){
        model.addAttribute("houseList", houseService.houseListForUserPage());
        return "admin/pages/user-main";
    }
}
