package lab.space.my_house_24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPanelController {
    @GetMapping({"/", ""})
    public String redirectAdmin() {
        return "redirect:/statistic";
    }
}
