package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.PriceRate;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.mapper.PriceRateMapper;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lab.space.my_house_24.repository.PriceRateRepository;
import lab.space.my_house_24.service.PriceRateService;
import lab.space.my_house_24.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceRateServiceImpl implements PriceRateService {

    private final PriceRateRepository priceRateRepository;
    private final ServiceService serviceService;

    @Override
    public PriceRate getPriceRateById(Long id) throws EntityNotFoundException {
        log.info("Try to find PriceRate");
        return priceRateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PriceRate not found by id " + id));
    }

    @Override
    public void updatePriceRateByRequest(PriceRateRequest priceRate, Rate rate) throws EntityNotFoundException {
        log.info("Try to update PriceRate by Update Request");
        savePriceRate(PriceRateMapper.toUpdatePriceRate(priceRate, getPriceRateById(priceRate.id()),
                serviceService.getServiceById(priceRate.serviceId()), rate));
        log.info("Success save PriceRate by Save Request");
    }

    @Override
    public void savePriceRateByRequest(PriceRateRequest priceRate, Rate rate) throws EntityNotFoundException {
        log.info("Try to save PriceRate by Save Request");
        savePriceRate(PriceRateMapper.toSavePriceRate(priceRate,
                serviceService.getServiceById(priceRate.serviceId()), rate));
        log.info("Success save PriceRate by Save Request");
    }

    @Override
    public void savePriceRate(PriceRate priceRate) {
        log.info("Try to save PriceRate");
        priceRateRepository.save(priceRate);
        log.info("Success save PriceRate");
    }

    @Override
    public void deletePriceRateById(Long id) throws EntityNotFoundException{
        log.info("Try to delete PriceRate by id " + id);
        priceRateRepository.delete(getPriceRateById(id));
        log.info("Success delete PriceRate by id " + id);
    }
}
