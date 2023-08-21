package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.rate.RateResponse;
import lab.space.my_house_24.model.rate.RateResponseForTable;
import lab.space.my_house_24.model.rate.RateSaveRequest;
import lab.space.my_house_24.model.rate.RateUpdateRequest;

import java.time.ZoneId;
import java.util.stream.Collectors;


public interface RateMapper {
    static RateResponseForTable entityToDtoForTable(Rate rate) {
        return RateResponseForTable.builder().name(rate.getName()).id(rate.getId()).build();
    }

    static RateResponse toPageRateResponse(Rate rate) {
        return RateResponse.builder()
                .id(rate.getId())
                .name(rate.getName())
                .description(rate.getDescription())
                .date(rate.getUpdateAt().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    static RateResponse toRateResponseWithUpdateAt(Rate rate) {
        return RateResponse.builder()
                .id(rate.getId())
                .name(rate.getName())
                .description(rate.getDescription())
                .date(rate.getUpdateAt().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .priceRate(rate.getPriceRateList()
                        .stream()
                        .map(PriceRateMapper::toPriceRateResponse)
                        .collect(Collectors.toList())
                )
                .build();
    }

    static RateResponse toRateResponse(Rate rate) {
        return RateResponse.builder()
                .name(rate.getName())
                .description(rate.getDescription())
                .priceRate(rate.getPriceRateList()
                        .stream()
                        .map(PriceRateMapper::toPriceRateResponse)
                        .collect(Collectors.toList())
                )
                .build();
    }

    static Rate toRate(RateUpdateRequest rateUpdateRequest, Rate rate) {
        return rate
                .setName(rateUpdateRequest.name())
                .setDescription(rateUpdateRequest.description());
    }

    static Rate toRate(RateSaveRequest rateSaveRequest) {
        return Rate.builder()
                .name(rateSaveRequest.name())
                .description(rateSaveRequest.description())
                .build();
    }
}
