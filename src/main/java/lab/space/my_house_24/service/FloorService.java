package lab.space.my_house_24.service;

import lab.space.my_house_24.model.floor.FloorResponseForTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FloorService {
    List<FloorResponseForTable> floorByHouse(Long houseId);
    Page<FloorResponseForTable> floorByHouse(Long houseId, Integer page, String search);
}
