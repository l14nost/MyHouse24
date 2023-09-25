package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.Requisites;
import lab.space.my_house_24.model.requites.RequisitesRequest;
import lab.space.my_house_24.model.requites.RequisitesResponse;
import lab.space.my_house_24.repository.RequisitesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequisitesServiceImplTest {
    @Mock
    private RequisitesRepository requisitesRepository;

    @InjectMocks
    private RequisitesServiceImpl requisitesService;

    @Test
    void findById() {
        Requisites requisites = Requisites.builder().id(1L).name("name").info("info").build();
        when(requisitesRepository.findById(1L)).thenReturn(Optional.of(requisites));
        Requisites requisites1 = requisitesService.findById(1L);
        assertEquals(requisites, requisites1);
    }

    @Test
    void findByIdResponse() {
        Requisites requisites = Requisites.builder().id(1L).name("name").info("info").build();
        when(requisitesRepository.findById(1L)).thenReturn(Optional.of(requisites));
        RequisitesResponse requisitesResponse = requisitesService.findByIdResponse(1L);
        RequisitesResponse requisitesResponse1 = RequisitesResponse.builder().name("name").info("info").build();
        assertEquals(requisitesResponse1, requisitesResponse);
    }

    @Test
    void update() {
        Requisites requisites = Requisites.builder().id(1L).name("name").info("info").build();
        when(requisitesRepository.findById(1L)).thenReturn(Optional.of(requisites));

        RequisitesRequest requisitesRequest = RequisitesRequest.builder().info("info1").name("name1").build();
        requisitesService.update(requisitesRequest);

        verify(requisitesRepository, times(1)).save(Requisites.builder().id(1L).name("name1").info("info1").build());
    }

    @Test
    void save() {
        Requisites requisites = Requisites.builder().id(1L).build();
        requisitesService.save(requisites);
        verify(requisitesRepository, times(1)).save(requisites);
    }
}