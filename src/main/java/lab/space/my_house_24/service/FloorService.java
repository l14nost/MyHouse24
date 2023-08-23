package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;

import java.util.List;

public interface FloorService {
    List<FloorResponseForTable> floorListForTable();
    List<FloorResponseForTable> floorByHouse(Long houseId);

    Floor findById(Long floor);

    List<Floor> findAllFloorByHouse(Long house);
}
