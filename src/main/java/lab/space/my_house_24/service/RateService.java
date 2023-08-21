package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.rate.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RateService {
    List<RateResponseForTable> rateListForTable();

    Page<RateResponse> getAllRatesResponse(RateRequest rateRequest);

    Rate getRateById(Long id);

    ResponseEntity<?> getRateByIdDto(Long id);
    ResponseEntity<?> getRateByIdWithUpdateAt(Long id);

    ResponseEntity<?> updateRateByRequest(RateUpdateRequest rateUpdateRequest);

    ResponseEntity<?> saveRateByRequest(RateSaveRequest rateSaveRequest);

    Rate saveRate(Rate priceRate);

    ResponseEntity<?> deleteRateById(Long id);
}
