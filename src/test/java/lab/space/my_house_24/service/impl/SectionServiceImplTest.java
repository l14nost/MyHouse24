package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Section;
import lab.space.my_house_24.repository.SectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SectionServiceImplTest {
    @Mock
    private SectionRepository sectionRepository;
    @InjectMocks
    private SectionServiceImpl sectionService;

    @Test
    void sectionListForTable() {
        when(sectionRepository.findAll()).thenReturn(List.of(
                Section.builder().build(),
                Section.builder().build(),
                Section.builder().build(),
                Section.builder().build()
        ));
        assertEquals(4, sectionService.sectionListForTable().size());
    }

    @Test
    void sectionByHouse() {
        when(sectionRepository.findAllByHouse_Id(1L)).thenReturn(List.of(
                Section.builder().build(),
                Section.builder().build(),
                Section.builder().build(),
                Section.builder().build()
        ));
        assertEquals(4, sectionService.sectionByHouse(1L).size());
    }

    @Test
    void findAllSectionByHouse() {
        when(sectionRepository.findAllByHouse_Id(1L)).thenReturn(List.of(
                Section.builder().build(),
                Section.builder().build(),
                Section.builder().build(),
                Section.builder().build()
        ));
        assertEquals(4, sectionService.findAllSectionByHouse(1L).size());
    }
}