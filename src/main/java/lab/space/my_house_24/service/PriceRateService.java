package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.PriceRate;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import org.springframework.http.ResponseEntity;

public interface PriceRateService {
    PriceRate getPriceRateById(Long id);

    void updatePriceRateByRequest(PriceRateRequest priceRate, Rate rate);

    void savePriceRateByRequest(PriceRateRequest priceRate, Rate rate);

    void savePriceRate(PriceRate priceRate);
    ResponseEntity<?> deletePriceRateById(Long id);
}
