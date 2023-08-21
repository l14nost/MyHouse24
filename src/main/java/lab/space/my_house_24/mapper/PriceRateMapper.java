package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.PriceRate;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lab.space.my_house_24.model.price_rate.PriceRateResponse;

public interface PriceRateMapper {

    static PriceRateResponse toPriceRateResponse(PriceRate priceRate) {
        return PriceRateResponse.builder()
                .id(priceRate.getId())
                .price(priceRate.getPrice())
                .service(ServiceMapper.toDto(priceRate.getService()))
                .build();
    }

    static PriceRate toUpdatePriceRate(PriceRateRequest priceRateUpdateRequest, PriceRate priceRate, Service service, Rate rate) {
        return priceRate
                .setRate(rate)
                .setPrice(priceRateUpdateRequest.price())
                .setService(service);
    }

    static PriceRate toSavePriceRate(PriceRateRequest priceRateSaveRequest, Service service, Rate rate) {
        return PriceRate.builder()
                .rate(rate)
                .price(priceRateSaveRequest.price())
                .service(service)
                .build();
    }
}
