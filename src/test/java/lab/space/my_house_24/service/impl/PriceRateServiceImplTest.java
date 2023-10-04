package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.PriceRate;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.entity.Service;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lab.space.my_house_24.repository.PriceRateRepository;
import lab.space.my_house_24.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceRateServiceImplTest {

    @Mock
    private PriceRateRepository priceRateRepository;

    @Mock
    private ServiceService serviceService;

    @InjectMocks
    private PriceRateServiceImpl priceRateService;

    @Test
    void getPriceRateById() {
        when(priceRateRepository.findById(anyLong())).thenReturn(Optional.of(PriceRate.builder().build()));
        assertEquals(PriceRate.builder().build(), priceRateService.getPriceRateById(1L));
    }

    @Test
    void updatePriceRateByRequest() {
        PriceRateRequest request = PriceRateRequest.builder()
                .id(1L)
                .price(BigDecimal.ZERO)
                .serviceId(1L)
                .build();
        Rate rate = Rate.builder().build();
        Service service = Service.builder().build();

        when(priceRateRepository.findById(anyLong())).thenReturn(Optional.of(PriceRate.builder().build()));
        when(serviceService.getServiceById(anyLong())).thenReturn(service);
        priceRateService.updatePriceRateByRequest(request, rate);
        verify(priceRateRepository, times(1)).findById(anyLong());
        verify(serviceService, times(1)).getServiceById(anyLong());
        verify(priceRateRepository, times(1)).save(any());
    }

    @Test
    void savePriceRateByRequest() {
        PriceRateRequest request = PriceRateRequest.builder()
                .price(BigDecimal.ZERO)
                .serviceId(1L)
                .build();
        Rate rate = Rate.builder().build();
        Service service = Service.builder().build();

        when(serviceService.getServiceById(anyLong())).thenReturn(service);
        priceRateService.savePriceRateByRequest(request, rate);
        verify(serviceService, times(1)).getServiceById(anyLong());
        verify(priceRateRepository, times(1)).save(any());
    }

    @Test
    void savePriceRate() {
        priceRateService.savePriceRate(PriceRate.builder().build());
        verify(priceRateRepository, times(1)).save(any());
    }

    @Test
    void deletePriceRateById() {
        when(priceRateRepository.findById(anyLong())).thenReturn(Optional.of(PriceRate.builder().build()));
        priceRateService.deletePriceRateById(1L);
        verify(priceRateRepository, times(1)).findById(anyLong());
        verify(priceRateRepository, times(1)).delete(any());
    }
}