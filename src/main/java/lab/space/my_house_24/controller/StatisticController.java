package lab.space.my_house_24.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("statistics")
@RequiredArgsConstructor
public class StatisticController {
    @GetMapping({"/", ""})
    public String showMastersApplicationPage() {
        return "admin/pages/statistic/statistic";
    }
}
