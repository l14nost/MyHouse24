package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;


public class FloorMapper {
    public  static FloorResponseForTable entityToDtoForTable(Floor floor){
        return FloorResponseForTable.builder().name(floor.getName()).id(floor.getId()).build();
    }
}
