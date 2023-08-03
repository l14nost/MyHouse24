package lab.space.my_house_24.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
@RequiredArgsConstructor
public class LoginController {
    @GetMapping({"/", ""})
    public String showLogin() {
        return "/admin/pages/login/login";
    }

}