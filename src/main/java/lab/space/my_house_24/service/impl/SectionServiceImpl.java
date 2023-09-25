package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.mapper.SectionMapper;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.repository.SectionRepository;
import lab.space.my_house_24.service.SectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    @Override
    public List<SectionResponseForTable> sectionListForTable() {
        log.info("Try to find list sections dto for table");
        return sectionRepository.findAll().stream().map(SectionMapper::entityToDtoForTable).toList();
    }

    @Override
    public List<SectionResponseForTable> sectionByHouse(Long id){
        log.info("Try to find list sections dto by house for table");
        return sectionRepository.findAllByHouse_Id(id).stream().map(SectionMapper::entityToDtoForTable).toList();
    }

    @Override
    public List<Section> findAllSectionByHouse(Long id) {
        log.info("Try to find list sections dto by house for table");
        return sectionRepository.findAllByHouse_Id(id);
    }
}
