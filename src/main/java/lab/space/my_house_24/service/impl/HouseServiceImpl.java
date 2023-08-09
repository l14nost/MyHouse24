package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.mapper.HouseMapper;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.repository.HouseRepository;
import lab.space.my_house_24.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;


    @Override
    public List<HouseResponseForTable> houseListForTable() {
        return houseRepository.findAll().stream().map(HouseMapper::entityToDtoForTable).toList();
    }
}
