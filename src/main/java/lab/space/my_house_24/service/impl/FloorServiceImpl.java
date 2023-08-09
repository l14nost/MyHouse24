package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.mapper.FloorMapper;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.repository.FloorRepository;
import lab.space.my_house_24.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;

    @Override
    public List<FloorResponseForTable> floorListForTable() {
        return floorRepository.findAll().stream().map(FloorMapper::entityToDtoForTable).toList();
    }
}
