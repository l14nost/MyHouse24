package lab.space.my_house_24.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("master-call")
@RequiredArgsConstructor
public class MastersApplicationController {
    @GetMapping({"/", ""})
    public String showMastersApplicationPage() {
        return "admin/pages/masters_application/masters-application";
    }

    @GetMapping("/card-{id}")
    public String showMastersApplicationCardById(@PathVariable("id") Long id) {
        return "admin/pages/masters_application/masters-application-card";
    }

    @GetMapping("/add")
    public String showMastersApplicationSavePage() {
        return "admin/pages/masters_application/masters-application-save";
    }

    @GetMapping("/update-{id}")
    public String showMastersApplicationUpdatePage(@PathVariable Long id) {
        return "admin/pages/masters_application/masters-application-save";
    }
}
