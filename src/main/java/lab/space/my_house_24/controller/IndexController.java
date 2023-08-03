package lab.space.my_house_24.controller;

import lab.space.my_house_24.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
@RequiredArgsConstructor
public class IndexController {
    private final StaffService service;

    @GetMapping({"/", ""})
    public String indexPage() {
        System.out.println(service.getAllStaffDto());
        return "/admin/template-pages";
    }

    @GetMapping("/login-index")
    public String loginIndexPage() {
        return "/admin/template-login";
    }
}
