package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.enums.BillStatus;
import lab.space.my_house_24.repository.BillRepository;
import lab.space.my_house_24.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    @Override
    public BigDecimal sumOfAllBills() {
        List<Bill> all = billRepository.findAll();
        BigDecimal totalSum = BigDecimal.ZERO;
        for (Bill bill: all){
            totalSum = totalSum.add(bill.getTotalPrice());
        }
        return totalSum;
    }

    @Override
    public List<BigDecimal> sumOffAllBillsByMonths() {
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        for (int i = 1; i<13;i++){
            BigDecimal sum = BigDecimal.ZERO;
            for (Bill bill: billRepository.findAll()){
                if (bill.getCreateAt().atZone(ZoneId.systemDefault()).getMonth().getValue() == i){
                    sum = sum.add(bill.getTotalPrice());
                }
            }
            bigDecimalList.add(sum);
        }
        return bigDecimalList;
    }

    @Override
    public List<BigDecimal> sumOffAllPaidBillsByMonths() {
        List<BigDecimal> bigDecimalList = new ArrayList<>();
        List<Bill> bills = billRepository.findAllByStatus(BillStatus.PAID);
        for (int i = 1; i<13;i++){
            BigDecimal sum = BigDecimal.ZERO;
            for (Bill bill: bills){
                if (bill.getCreateAt().atZone(ZoneId.systemDefault()).getMonth().getValue() == i){
                    sum = sum.add(bill.getTotalPrice());
                }
            }
            bigDecimalList.add(sum);
        }
        return bigDecimalList;
    }
}
