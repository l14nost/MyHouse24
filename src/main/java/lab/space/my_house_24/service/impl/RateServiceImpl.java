package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.mapper.RateMapper;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lab.space.my_house_24.model.rate.*;
import lab.space.my_house_24.repository.RateRepository;
import lab.space.my_house_24.service.PriceRateService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.specification.RateSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final PriceRateService priceRateService;
    private final RateSpecification rateSpecification;

    @Override
    public List<RateResponseForTable> rateListForTable() {
        return rateRepository.findAll().stream().map(RateMapper::entityToDtoForTable).toList();
    }

    @Override
    public Page<RateResponse> getAllRatesResponse(RateRequest request) {
        log.info("Try to get all RatesResponse by Request");
        final int DEFAULT_PAGE_SIZE = 10;
        return rateRepository.findAll(
                rateSpecification.getRateByRequest(request),
                PageRequest.of(request.pageIndex(), DEFAULT_PAGE_SIZE)).map(RateMapper::toPageRateResponse);
    }

    @Override
    public List<RateResponse> getAllRatesForBill() {
        log.info("Try to get all RatesResponse");
        return rateRepository.findAll().stream().map(RateMapper::toRateResponseForBill).toList();
    }

    @Override
    public Rate getRateById(Long id) throws EntityNotFoundException {
        log.info("Try to find Rate");
        return rateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rate not found by id " + id));
    }


    @Override
    public ResponseEntity<?> getRateByIdDto(Long id) {
        try {
            log.info("Try to get RateResponse");
            return ResponseEntity.ok(RateMapper.toRateResponse(getRateById(id)));
        } catch (EntityNotFoundException e) {
            log.error("Error get RateResponse");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> getRateByIdResponseForBill(Long id) {
        try {
            log.info("Try to get RateResponse");
            return ResponseEntity.ok(RateMapper.toRateResponseForBill(getRateById(id)));
        } catch (EntityNotFoundException e) {
            log.error("Error get RateResponse");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> getRateByIdWithUpdateAt(Long id) {
        try {
            log.info("Try to get Rate");
            return ResponseEntity.ok(RateMapper.toRateResponseWithUpdateAt(getRateById(id)));
        } catch (EntityNotFoundException e) {
            log.error("Error get Rate by Update Request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> updateRateByRequest(RateUpdateRequest rateUpdateRequest) {
        try {
            log.info("Try to update Rate by Update Request");
            Rate rate = saveRate(RateMapper.toRate(rateUpdateRequest, getRateById(rateUpdateRequest.id())));
            log.info("Success update Rate by Update Request");
            for (PriceRateRequest priceRate : rateUpdateRequest.priceRate()){
                if (nonNull(priceRate.id())) {
                    priceRateService.updatePriceRateByRequest(priceRate, rate);
                } else priceRateService.savePriceRateByRequest(priceRate, rate);
            }
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error update Rate by Update Request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public ResponseEntity<?> saveRateByRequest(RateSaveRequest rateSaveRequest) {
        try {
            log.info("Try to save Rate by Save Request");
            Rate rate = saveRate(RateMapper.toRate(rateSaveRequest));
            log.info("Success save Rate by Save Request");
            for (PriceRateRequest priceRate : rateSaveRequest.priceRate()){
                priceRateService.savePriceRateByRequest(priceRate, rate);
            }
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error save Rate by Save Request");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @Override
    public Rate saveRate(Rate rate) {
        log.info("Try to save Rate");
        return rateRepository.save(rate);
    }

    @Override
    public ResponseEntity<?> deleteRateById(Long id) {
        try {
            log.info("Try to delete Rate by id " + id);
            Rate rate = getRateById(id);
            if (rate.getApartmentList().isEmpty() && rate.getBillList().isEmpty()){
                rateRepository.delete(rate);
            }else {
                String error;
                if (LocaleContextHolder.getLocale().toLanguageTag().equals("uk")) {
                    error = "Тарифом користуються. Видалення неможливе.";
                } else {
                    error = "The rate is used. Removal is not possible.";
                }
                log.warn("Warning delete Rate");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
            }
            log.info("Success delete Rate by id " + id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            log.error("Error delete Rate by id " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
}
