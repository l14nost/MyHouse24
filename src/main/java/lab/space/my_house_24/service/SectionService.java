package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SectionService {

    List<SectionResponseForTable> sectionListForTable();

    Page<String> sectionListForTable(Integer pageIndex, String search);

    List<SectionResponseForTable> sectionByHouse(Long id);

    Page<SectionResponseForTable> sectionByHouse(Long id, Integer pageIndex, String search);

    List<Section> findAllSectionByHouse(Long id);
}
