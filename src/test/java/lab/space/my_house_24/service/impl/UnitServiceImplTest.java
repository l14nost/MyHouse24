package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.entity.Unit;
import lab.space.my_house_24.model.unit.UnitRequest;
import lab.space.my_house_24.model.unit.UnitSaveRequest;
import lab.space.my_house_24.repository.UnitRepository;
import lab.space.my_house_24.specification.UnitSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceImplTest {

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private UnitSpecification unitSpecification;

    @Mock
    private MessageSource message;

    @InjectMocks
    private UnitServiceImpl unitServiceImpl;

    @Test
    void getUnitById() {
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(Unit.builder().build()));
        assertEquals(Unit.builder().build(), unitServiceImpl.getUnitById(1L));
    }

    @Test
    void getAllUnitDto() {
        Page<Unit> response = new PageImpl<>(List.of(
                Unit.builder().id(1L).name("Test").build(),
                Unit.builder().id(2L).name("Test").build(),
                Unit.builder().id(3L).name("Test").build(),
                Unit.builder().id(4L).name("Test").build()
        ));
        when(unitRepository.findAll((Specification<Unit>) any(), any(PageRequest.class))).thenReturn(response);
        assertEquals(response.getTotalElements(), unitServiceImpl.getAllUnitDtoForSelect(1,"Test").getTotalElements());
    }

    @Test
    void saveUnit() {
        unitServiceImpl.saveUnit(Unit.builder().build());
        verify(unitRepository, times(1)).save(any());
    }

    @Test
    void saveUnitByRequest() {
        UnitSaveRequest request = UnitSaveRequest.builder()
                .unitRequestList(
                        List.of(
                                UnitRequest.builder()
                                        .id(1L)
                                        .name("Test1")
                                        .build(),
                                UnitRequest.builder()
                                        .name("Test2")
                                        .build()
                        )
                )
                .build();
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(Unit.builder().build()));
        unitServiceImpl.saveUnitByRequest(request);
        verify(unitRepository, times(1)).findById(anyLong());
        verify(unitRepository, times(2)).save(any());
    }

    @Test
    void deleteUnitById() {
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(Unit.builder().serviceList(List.of()).build()));
        unitServiceImpl.deleteUnitById(1L);
        verify(unitRepository, times(1)).findById(anyLong());
        verify(unitRepository, times(1)).delete((Unit) any());
    }

    @Test
    void deleteUnitByIdBadRequest() {
        when(unitRepository.findById(anyLong())).thenReturn(Optional.of(Unit.builder().serviceList(List.of(Service.builder().build())).build()));
        assertThrows(IllegalArgumentException.class, () -> {
            unitServiceImpl.deleteUnitById(1L);
        });
        verify(unitRepository, times(1)).findById(anyLong());
        verify(unitRepository, times(0)).delete((Unit) any());
    }
}
