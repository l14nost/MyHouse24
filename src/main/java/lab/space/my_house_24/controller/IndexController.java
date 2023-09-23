package lab.space.my_house_24.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("index")
@RequiredArgsConstructor
public class IndexController {

    @GetMapping({"/", ""})
    public ModelAndView indexPage() {
        return new ModelAndView("admin/template-pages");
    }

    @GetMapping("/login-index")
    public ModelAndView loginIndexPage() {
        return new ModelAndView("admin/template-login");
    }
}
