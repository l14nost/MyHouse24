package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.mapper.FloorMapper;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.repository.FloorRepository;
import lab.space.my_house_24.service.FloorService;
import lab.space.my_house_24.specification.FloorSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;
    @Override
    public List<FloorResponseForTable> floorByHouse(Long houseId) {
        log.info("Try to get all floor by house id: "+houseId);
        return floorRepository.findAllByHouse_Id(houseId).stream().map(FloorMapper::entityToDtoForTable).toList();
    }

    @Override
    public Page<FloorResponseForTable> floorByHouse(Long houseId, Integer page, String search) {
        FloorSpecification floorSpecification = FloorSpecification.builder().houseId(houseId).name(search).build();
        return floorRepository.findAll(floorSpecification,PageRequest.of(page, 10)).map(FloorMapper::entityToDtoForTable);
    }
}
