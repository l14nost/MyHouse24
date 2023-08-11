package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.mapper.RateMapper;
import lab.space.my_house_24.model.rate.RateResponseForTable;
import lab.space.my_house_24.repository.RateRepository;
import lab.space.my_house_24.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    @Override
    public List<RateResponseForTable> rateListForTable() {
        return rateRepository.findAll().stream().map(RateMapper::entityToDtoForTable).toList();
    }
}
