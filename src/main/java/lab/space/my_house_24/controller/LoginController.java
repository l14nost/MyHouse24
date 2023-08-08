package lab.space.my_house_24.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
@RequiredArgsConstructor
public class LoginController {
    @GetMapping({"/", ""})
    public String showLogin() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User){
            return "redirect:/staff";
        }else {
            return "/admin/pages/login/login";
        }
    }

}