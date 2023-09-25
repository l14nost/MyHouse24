package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Statistic;
import lab.space.my_house_24.repository.StatisticRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatisticServiceImplTest {

    @Mock
    private StatisticRepository statisticRepository;
    @InjectMocks
    private StatisticServiceImpl statisticService;

    @Test
    void getAllStatistics() {
        when(statisticRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))).thenReturn(List.of(Statistic.builder().build()));
        assertEquals(List.of(Statistic.builder().build()), statisticService.getAllStatistics());
    }

    @Test
    void getAllStatistics_Empty() {
        when(statisticRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))).thenReturn(List.of());
        assertEquals(List.of(), statisticService.getAllStatistics());
    }

    @Test
    void updateStatistic_CashBox() {
        Statistic statistic = Statistic.builder()
                .cashBoxState(BigDecimal.TEN)
                .bankBookExpense(new BigDecimal(100))
                .bankBookBalance(new BigDecimal(10000))
                .id(1L)
                .build();
        when(statisticRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))).thenReturn(List.of(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ZERO)
                        .bankBookExpense(new BigDecimal(10))
                        .bankBookBalance(new BigDecimal(10))
                        .id(1L)
                        .build()
        ));
        statisticService.updateStatistic(statistic);
        verify(statisticRepository, times(1)).save(Statistic.builder()
                .cashBoxState(BigDecimal.TEN)
                .bankBookExpense(new BigDecimal(10))
                .bankBookBalance(new BigDecimal(10))
                .id(1L)
                .build());
    }
    @Test
    void updateStatistic_BankBookBalance() {
        Statistic statistic = Statistic.builder()
                .cashBoxState(BigDecimal.ZERO)
                .bankBookExpense(new BigDecimal(100))
                .bankBookBalance(new BigDecimal(10000))
                .id(1L)
                .build();
        when(statisticRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))).thenReturn(List.of(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ZERO)
                        .bankBookExpense(new BigDecimal(10))
                        .bankBookBalance(new BigDecimal(10))
                        .id(1L)
                        .build()
        ));
        statisticService.updateStatistic(statistic);
        verify(statisticRepository, times(1)).save(Statistic.builder()
                .cashBoxState(BigDecimal.ZERO)
                .bankBookExpense(new BigDecimal(10))
                .bankBookBalance(new BigDecimal(10010))
                .id(1L)
                .build());
    }

    @Test
    void updateStatistic_BankBookExpense() {
        Statistic statistic = Statistic.builder()
                .cashBoxState(BigDecimal.ZERO)
                .bankBookExpense(new BigDecimal(100))
                .bankBookBalance(new BigDecimal(0))
                .id(1L)
                .build();
        when(statisticRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))).thenReturn(List.of(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ZERO)
                        .bankBookExpense(new BigDecimal(10))
                        .bankBookBalance(new BigDecimal(10))
                        .id(1L)
                        .build()
        ));
        statisticService.updateStatistic(statistic);
        verify(statisticRepository, times(1)).save(Statistic.builder()
                .cashBoxState(BigDecimal.ZERO)
                .bankBookExpense(new BigDecimal(110))
                .bankBookBalance(new BigDecimal(10))
                .id(1L)
                .build());
    }

    @Test
    void updateStatistic_Empty() {
        Statistic statistic = Statistic.builder()
                .cashBoxState(BigDecimal.ZERO)
                .bankBookExpense(new BigDecimal(100))
                .bankBookBalance(new BigDecimal(0))
                .id(1L)
                .build();
        when(statisticRepository.findAll(Sort.by(Sort.Direction.ASC,"id"))).thenReturn(List.of(
        ));
        try {
            statisticService.updateStatistic(statistic);
        }
        catch (EntityNotFoundException e){
            verify(statisticRepository, times(0)).save(any(Statistic.class));
        }
    }

}