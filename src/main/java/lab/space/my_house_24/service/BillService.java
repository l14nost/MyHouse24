package lab.space.my_house_24.service;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {
    BigDecimal sumOfAllBills();

    List<BigDecimal> sumOffAllBillsByMonths();

    List<BigDecimal> sumOffAllPaidBillsByMonths();
}
