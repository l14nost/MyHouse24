package lab.space.my_house_24.service;

import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;

import java.util.List;

public interface FloorService {
    List<FloorResponseForTable> floorListForTable();
}
