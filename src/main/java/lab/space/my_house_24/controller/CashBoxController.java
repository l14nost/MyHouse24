package lab.space.my_house_24.controller;

import lab.space.my_house_24.service.CashBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cash-box")
public class CashBoxController {
    private final CashBoxService cashBoxService;

    @GetMapping("/get-statistics-for-coming")
    public ResponseEntity getStatisticsForComing(){
        return ResponseEntity.ok(cashBoxService.statisticSumByType(true));
    }

    @GetMapping("/get-statistics-for-costs")
    public ResponseEntity getStatisticsForCosts(){
        return ResponseEntity.ok(cashBoxService.statisticSumByType(false));
    }
}
