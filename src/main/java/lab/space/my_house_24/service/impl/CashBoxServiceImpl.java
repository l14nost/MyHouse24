package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.CashBox;
import lab.space.my_house_24.repository.CashBoxRepository;
import lab.space.my_house_24.service.BillService;
import lab.space.my_house_24.service.CashBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CashBoxServiceImpl implements CashBoxService {
    private final CashBoxRepository cashBoxRepository;
    private final BillService billService;
    @Override
    public List<BigDecimal> statisticSumByType(Boolean type) {
        List<CashBox> all = cashBoxRepository.findAllByType(type);
        List<BigDecimal> allSumByMonths = new ArrayList<>();
        for (int i = 1; i<13;i++){
            BigDecimal sum = BigDecimal.ZERO;
            for (CashBox cashBox : all){
                if (cashBox.getDate().atZone(ZoneId.systemDefault()).getMonth().getValue() == i){
                    sum=sum.add(cashBox.getSum());
                }
            }
            allSumByMonths.add(sum);
        }
        return allSumByMonths;
    }

    @Override
    public BigDecimal statisticCashStatement() {
        List<CashBox> allComing = cashBoxRepository.findAllByType(true);
        List<CashBox> allCosts = cashBoxRepository.findAllByType(false);
        BigDecimal sumComing = BigDecimal.ZERO;
        for (CashBox cashBox : allComing){
            sumComing=sumComing.add(cashBox.getSum());

        }
        BigDecimal sumCosts = BigDecimal.ZERO;
        for (CashBox cashBox : allCosts){
            sumCosts=sumCosts.add(cashBox.getSum());

        }
        return sumComing.subtract(sumCosts);
    }
    @Override
    public BigDecimal statisticAccountBalance(){
        BigDecimal billsSum = billService.sumOfAllBills();
        List<CashBox> cashBoxes = cashBoxRepository.findAllByType(true);
        BigDecimal cashBoxSum = BigDecimal.ZERO;
        for (CashBox cashBox: cashBoxes){
            cashBoxSum = cashBoxSum.add(cashBox.getSum());
        }
        return cashBoxSum.subtract(billsSum);
    }
}
