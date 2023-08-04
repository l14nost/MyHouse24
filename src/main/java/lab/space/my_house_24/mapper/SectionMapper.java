package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.House;
import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;


public class SectionMapper {
    public  static SectionResponseForTable entityToDtoForTable(Section section){
        return SectionResponseForTable.builder().name(section.getName()).id(section.getId()).build();
    }
}
