package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.mapper.SectionMapper;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.repository.SectionRepository;
import lab.space.my_house_24.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    @Override
    public List<SectionResponseForTable> sectionListForTable() {
        return sectionRepository.findAll().stream().map(SectionMapper::entityToDtoForTable).toList();
    }


    public List<SectionResponseForTable> sectionByHouse(Long id){
        return sectionRepository.findAllByHouse_Id(id).stream().map(SectionMapper::entityToDtoForTable).toList();
    }
}
