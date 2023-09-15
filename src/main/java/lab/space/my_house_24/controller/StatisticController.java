package lab.space.my_house_24.controller;

import lab.space.my_house_24.entity.Statistic;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("statistics")
@RequiredArgsConstructor
public class StatisticController {
    private final UserService userService;
    private final HouseService houseService;
    private final BankBookService bankBookService;
    private final MastersApplicationService mastersApplicationService;
    private final ApartmentService apartmentService;
    private final CashBoxService cashBoxService;
    private final StatisticService statisticService;

    @GetMapping({"/", ""})
    public String showMastersApplicationPage(Model model) {
        model.addAttribute("owner",userService.countByStatus(UserStatus.ACTIVE));
        model.addAttribute("apartment",apartmentService.count());
        model.addAttribute("house",houseService.count());
        model.addAttribute("bankBook",bankBookService.count());
        model.addAttribute("masterApplicationAtWork",mastersApplicationService.countByStatus(MastersApplicationStatus.IN_PROCESS));
        model.addAttribute("masterApplicationNew",mastersApplicationService.countByStatus(MastersApplicationStatus.NEW));
        model.addAttribute("cashStatement",cashBoxService.statisticCashStatement());
        model.addAttribute("accountBalance",cashBoxService.statisticAccountBalance());
        return "admin/pages/statistic/statistic";
    }

    @GetMapping("/get-statistic")
    public ResponseEntity<Statistic> getStatistic() {
        return ResponseEntity.ok(statisticService.getAllStatistics().get(0));
    }
}
