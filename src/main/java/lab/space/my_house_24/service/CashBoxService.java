package lab.space.my_house_24.service;

import java.math.BigDecimal;
import java.util.List;

public interface CashBoxService {

    List<BigDecimal> statisticSumByType(Boolean type);
    BigDecimal statisticCashStatement();
    BigDecimal statisticAccountBalance();

}
