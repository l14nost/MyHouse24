package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Floor;
import lab.space.my_house_24.repository.FloorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FloorServiceImplTest {
    @Mock
    private FloorRepository floorRepository;
    @InjectMocks
    private FloorServiceImpl floorService;

    @Test
    void floorByHouse() {
        when(floorRepository.findAllByHouse_Id(1L)).thenReturn(List.of(
                Floor.builder().build(),
                Floor.builder().build(),
                Floor.builder().build(),
                Floor.builder().build()
        ));
        assertEquals(4,floorService.floorByHouse(1L).size());
    }
}