package lab.space.my_house_24.controller;

import lab.space.my_house_24.entity.Statistic;
import lab.space.my_house_24.enums.MastersApplicationStatus;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView showStatisticPage() {
        ModelAndView modelAndView = new ModelAndView("admin/pages/statistic/statistic");
        modelAndView.addObject("owner", userService.countByStatus(UserStatus.ACTIVE));
        modelAndView.addObject("apartment", apartmentService.count());
        modelAndView.addObject("house", houseService.count());
        modelAndView.addObject("bankBook", bankBookService.count());
        modelAndView.addObject("masterApplicationAtWork", mastersApplicationService.countByStatus(MastersApplicationStatus.IN_PROCESS));
        modelAndView.addObject("masterApplicationNew", mastersApplicationService.countByStatus(MastersApplicationStatus.NEW));
        modelAndView.addObject("cashStatement", cashBoxService.statisticCashStatement());
        modelAndView.addObject("accountBalance", cashBoxService.statisticAccountBalance());
        return modelAndView;
    }

    @GetMapping("/get-statistic")
    public ResponseEntity<Statistic> getStatistic() {
        return ResponseEntity.ok(statisticService.getAllStatistics().get(0));
    }

    @MessageMapping("/statistics")
    @SendTo("/topic/statistics")
    public String changeStatistic() {
        return "change";
    }
}
