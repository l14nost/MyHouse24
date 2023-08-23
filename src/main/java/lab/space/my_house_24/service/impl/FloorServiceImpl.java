package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.mapper.FloorMapper;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
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
    @Override
    public List<FloorResponseForTable> floorByHouse(Long houseId) {
        return floorRepository.findAllByHouse_Id(houseId).stream().map(FloorMapper::entityToDtoForTable).toList();
    }

    @Override
    public Floor findById(Long floor) {
        return floorRepository.findById(floor).orElseThrow(()->new EntityNotFoundException("Floor by id "+floor+" is not found"));
    }

    @Override
    public List<Floor> findAllFloorByHouse(Long house) {
        return floorRepository.findAllByHouse_Id(house);
    }
}
