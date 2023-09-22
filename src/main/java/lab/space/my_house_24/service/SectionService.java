package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;

import java.util.List;

public interface SectionService {
    List<SectionResponseForTable> sectionListForTable();
    List<SectionResponseForTable> sectionByHouse(Long id);
    List<Section> findAllSectionByHouse(Long id);
}
