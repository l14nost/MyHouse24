package lab.space.my_house_24.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminPanelController {
    @GetMapping({"/", ""})
    public ModelAndView redirectAdmin() {
        return new ModelAndView("redirect:/payment-items");
    }
}
