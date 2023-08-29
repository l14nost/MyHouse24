package lab.space.my_house_24.controller;

import lab.space.my_house_24.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class BillController {

    private final BillService billService;

    @GetMapping("/get-sum-of-all-bills")
    public ResponseEntity getSumOfAllBills(){
        return ResponseEntity.ok(billService.sumOffAllBillsByMonths());
    }
    @GetMapping("/get-sum-of-all-paid-bills")
    public ResponseEntity getSumOfAllPaidBills(){
        return ResponseEntity.ok(billService.sumOffAllPaidBillsByMonths());
    }
}
