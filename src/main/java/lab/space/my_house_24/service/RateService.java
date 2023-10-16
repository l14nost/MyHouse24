package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.rate.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RateService {
    List<RateResponseForTable> rateListForTable();

    Page<RateResponse> getAllRatesResponse(RateRequest rateRequest);

    Page<RateResponse> getAllRatesForBill(Integer pageIndex, String search);

    Rate getRateById(Long id);

    RateResponse getRateByIdDto(Long id);

    RateResponse getRateByIdResponseForBill(Long id);

    RateResponse getRateByIdWithUpdateAt(Long id);

    void updateRateByRequest(RateUpdateRequest rateUpdateRequest);

    void saveRateByRequest(RateSaveRequest rateSaveRequest);

    Rate saveRate(Rate priceRate);

    void deleteRateById(Long id);

    List<Rate> getAllRate();

    Page<RateResponseForTable> getAllRateDtoForSelect(String search, Integer page);
}
