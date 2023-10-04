package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lab.space.my_house_24.model.rate.*;
import lab.space.my_house_24.repository.RateRepository;
import lab.space.my_house_24.service.PriceRateService;
import lab.space.my_house_24.specification.RateSpecification;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

    @Mock
    private RateRepository rateRepository;

    @Mock
    private PriceRateService priceRateService;

    @Mock
    private RateSpecification rateSpecification;

    @Mock
    private MessageSource message;

    @InjectMocks
    private RateServiceImpl rateService;

    @Test
    void rateListForTable() {
        List<Rate> rateResponses = List.of(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .build(),
                Rate.builder()
                        .id(2L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .build(),
                Rate.builder()
                        .id(3L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .build(),
                Rate.builder()
                        .id(4L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .build()
        );
        when(rateRepository.findAll()).thenReturn(rateResponses);
        List<RateResponseForTable> responsePage = rateService.rateListForTable();
        assertEquals(4, responsePage.size());
        assertEquals(RateResponseForTable.class, responsePage.iterator().next().getClass());
    }

    @Test
    void getAllRatesResponse() {
        Page<Rate> rateResponses = new PageImpl<>(
                List.of(
                        Rate.builder()
                                .id(1L)
                                .name("Test")
                                .description("Test")
                                .updateAt(Instant.now())
                                .build(),
                        Rate.builder()
                                .id(2L)
                                .name("Test")
                                .description("Test")
                                .updateAt(Instant.now())
                                .build(),
                        Rate.builder()
                                .id(3L)
                                .name("Test")
                                .description("Test")
                                .updateAt(Instant.now())
                                .build(),
                        Rate.builder()
                                .id(4L)
                                .name("Test")
                                .description("Test")
                                .updateAt(Instant.now())
                                .build()
                )
        );
        when(rateRepository.findAll((Specification<Rate>) any(), any(PageRequest.class))).thenReturn(rateResponses);
        Page<RateResponse> responsePage = rateService.getAllRatesResponse(RateRequest.builder().pageIndex(1).build());
        assertEquals(4, responsePage.getTotalElements());
        assertEquals(RateResponse.class, responsePage.iterator().next().getClass());
    }

    @Test
    void getAllRatesForBill() {
        List<Rate> rateResponses = List.of(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build(),
                Rate.builder()
                        .id(2L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build(),
                Rate.builder()
                        .id(3L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build(),
                Rate.builder()
                        .id(4L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build()
        );
        when(rateRepository.findAll()).thenReturn(rateResponses);
        List<RateResponse> responsePage = rateService.getAllRatesForBill();
        assertEquals(4, responsePage.size());
        assertEquals(RateResponse.class, responsePage.iterator().next().getClass());
    }

    @Test
    void getRateById() {
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(Rate.builder().build()));
        assertEquals(Rate.builder().build(), rateService.getRateById(1L));

    }

    @Test
    void getRateByIdDto() {
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(Rate.builder()
                .id(4L)
                .name("Test")
                .description("Test")
                .updateAt(Instant.now())
                .priceRateList(List.of(PriceRate.builder()
                        .id(1L)
                        .price(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build()));
        rateService.getRateByIdDto(1L);
        verify(rateRepository, times(1)).findById(anyLong());
    }

    @Test
    void getRateByIdResponseForBill() {
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(Rate.builder()
                .id(4L)
                .name("Test")
                .description("Test")
                .updateAt(Instant.now())
                .priceRateList(List.of(PriceRate.builder()
                        .id(1L)
                        .price(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build()));
        rateService.getRateByIdResponseForBill(1L);
        verify(rateRepository, times(1)).findById(anyLong());
    }

    @Test
    void getRateByIdWithUpdateAt() {
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(Rate.builder()
                .id(4L)
                .name("Test")
                .description("Test")
                .updateAt(Instant.now())
                .priceRateList(List.of(PriceRate.builder()
                        .id(1L)
                        .price(BigDecimal.ZERO)
                        .service(Service.builder()
                                .id(1L)
                                .name("Test")
                                .unit(Unit.builder()
                                        .id(1L)
                                        .name("Test")
                                        .build())
                                .build())
                        .build()))
                .build()));
        rateService.getRateByIdWithUpdateAt(1L);
        verify(rateRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateRateByRequest() {
        RateUpdateRequest request = RateUpdateRequest.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .priceRate(List.of(PriceRateRequest.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .serviceId(1L)
                                .build(),
                        PriceRateRequest.builder()
                                .price(BigDecimal.ZERO)
                                .serviceId(1L)
                                .build()
                ))
                .build();
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(Rate.builder().apartmentList(List.of()).billList(List.of()).build()));
        rateService.updateRateByRequest(request);
        verify(rateRepository, times(1)).save(any());
        verify(priceRateService, times(1)).savePriceRateByRequest(any(), any());
        verify(priceRateService, times(1)).updatePriceRateByRequest(any(), any());
    }

    @Test
    void saveRateByRequest() {
        RateSaveRequest request = RateSaveRequest.builder()
                .name("Test")
                .description("Test")
                .priceRate(List.of(PriceRateRequest.builder()
                        .id(1L)
                        .price(BigDecimal.ZERO)
                        .serviceId(1L)
                        .build()))
                .build();
        rateService.saveRateByRequest(request);
        verify(rateRepository, times(1)).save(any());
        verify(priceRateService, times(1)).savePriceRateByRequest(any(), any());
    }

    @Test
    void saveRate() {
        rateService.saveRate(Rate.builder().build());
        verify(rateRepository, times(1)).save(any());
    }

    @Test
    void deleteRateById() {
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(Rate.builder().apartmentList(List.of()).billList(List.of()).build()));
        rateService.deleteRateById(1L);
        verify(rateRepository, times(1)).findById(anyLong());
        verify(rateRepository, times(1)).delete((Rate) any());
    }

    @Test
    void deleteRateByIdBadRequest() {
        when(rateRepository.findById(anyLong())).thenReturn(Optional.of(Rate.builder().apartmentList(List.of(Apartment.builder().build())).billList(List.of()).build()));
        assertThrows(IllegalArgumentException.class, () -> {
            rateService.deleteRateById(1L);
        });
        verify(rateRepository, times(1)).findById(anyLong());
        verify(rateRepository, times(0)).delete((Rate) any());
    }

    @Test
    void getAllRate() {
        List<Rate> rateResponses = List.of(
                Rate.builder()
                        .id(1L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build(),
                Rate.builder()
                        .id(2L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build(),
                Rate.builder()
                        .id(3L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build(),
                Rate.builder()
                        .id(4L)
                        .name("Test")
                        .description("Test")
                        .updateAt(Instant.now())
                        .priceRateList(List.of(PriceRate.builder()
                                .id(1L)
                                .price(BigDecimal.ZERO)
                                .service(Service.builder()
                                        .id(1L)
                                        .name("Test")
                                        .unit(Unit.builder()
                                                .id(1L)
                                                .name("Test")
                                                .build())
                                        .build())
                                .build()))
                        .build()
        );
        when(rateRepository.findAll()).thenReturn(rateResponses);
        List<Rate> responsePage = rateService.getAllRate();
        assertEquals(4, responsePage.size());
        assertEquals(Rate.class, responsePage.iterator().next().getClass());
    }
}