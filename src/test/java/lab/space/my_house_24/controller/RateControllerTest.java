package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.model.price_rate.PriceRateRequest;
import lab.space.my_house_24.model.rate.RateRequest;
import lab.space.my_house_24.model.rate.RateResponse;
import lab.space.my_house_24.model.rate.RateSaveRequest;
import lab.space.my_house_24.model.rate.RateUpdateRequest;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.service.PriceRateService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.service.ServiceService;
import lab.space.my_house_24.validator.PriceRateValidator;
import lab.space.my_house_24.validator.RateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RateController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RateControllerTest {

    @MockBean
    private RateService rateService;

    @MockBean
    private RateValidator rateValidator;

    @MockBean
    private PriceRateValidator priceRateValidator;

    @MockBean
    private PriceRateService priceRateService;

    @MockBean
    private ServiceService serviceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void showRatesPage() throws Exception {
        mockMvc.perform(get("/rates"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/rate/rates"));
    }

    @Test
    void showRateCardById() throws Exception {
        mockMvc.perform(get("/rates/card-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/rate/rate-card"));
    }

    @Test
    void showRateSavePage() throws Exception {
        mockMvc.perform(get("/rates/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/rate/rate-save"));
    }

    @Test
    void showRateCopySavePage() throws Exception {
        mockMvc.perform(get("/rates/add-copy-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/rate/rate-save"));
    }

    @Test
    void showRateUpdatePage() throws Exception {
        mockMvc.perform(get("/rates/update-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/rate/rate-save"));
    }

    @Test
    void getRateDto() throws Exception {
        RateResponse rateResponse = RateResponse.builder().build();
        when(rateService.getRateByIdDto(anyLong())).thenReturn(rateResponse);
        mockMvc.perform(get("/rates/get-rate-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(rateResponse)));
        verify(rateService, times(1)).getRateByIdDto(anyLong());
    }

    @Test
    void getRateDtoEntityNotFound() throws Exception {
        when(rateService.getRateByIdDto(anyLong())).thenThrow(new EntityNotFoundException("Not Found"));
        mockMvc.perform(get("/rates/get-rate-1"))
                .andExpect(status().isNotFound());
        verify(rateService, times(1)).getRateByIdDto(anyLong());
    }

    @Test
    void getRateDtoWithId() throws Exception {
        RateResponse rateResponse = RateResponse.builder().build();
        when(rateService.getRateByIdWithUpdateAt(anyLong())).thenReturn(rateResponse);
        mockMvc.perform(get("/rates/get-rate-card-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(rateResponse)));
        verify(rateService, times(1)).getRateByIdWithUpdateAt(anyLong());
    }

    @Test
    void getRateDtoWithIdEntityNotFound() throws Exception {
        when(rateService.getRateByIdWithUpdateAt(anyLong())).thenThrow(new EntityNotFoundException("Not Found"));
        mockMvc.perform(get("/rates/get-rate-card-1"))
                .andExpect(status().isNotFound());
        verify(rateService, times(1)).getRateByIdWithUpdateAt(anyLong());
    }

    @Test
    void getAllRatesDto() throws Exception {
        Page<RateResponse> rateResponses = new PageImpl<>(List.of(
                RateResponse.builder().build(),
                RateResponse.builder().build(),
                RateResponse.builder().build(),
                RateResponse.builder().build()
        ));
        when(rateService.getAllRatesResponse(any())).thenReturn(rateResponses);
        mockMvc.perform(post("/rates/get-all-rates")
                        .content(objectMapper.writeValueAsString(RateRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(rateResponses)));
    }

    @Test
    void getAllServicesDto() throws Exception {
        List<ServiceResponse> serviceResponses = List.of(
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build()
        );
        when(serviceService.getAllServicesByIsActiveDto()).thenReturn(serviceResponses);
        mockMvc.perform(get("/rates/get-all-services"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(serviceResponses)));
    }

    @Test
    void updateRate() throws Exception {
        RateUpdateRequest request = RateUpdateRequest.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .priceRate(List.of(PriceRateRequest.builder().id(1L).price(BigDecimal.ONE).serviceId(1L).build()))
                .build();
        mockMvc.perform(put("/rates/update-rate")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(rateService, times(1)).updateRateByRequest(any());
    }

    @Test
    void updateRateEntityNotFound() throws Exception {
        RateUpdateRequest request = RateUpdateRequest.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .priceRate(List.of(PriceRateRequest.builder().id(1L).price(BigDecimal.ONE).serviceId(1L).build()))
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(rateService)
                .updateRateByRequest(any());
        mockMvc.perform(put("/rates/update-rate")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(rateService, times(1)).updateRateByRequest(any());
    }

    @Test
    void updateRateBadRequest() throws Exception {
        RateUpdateRequest request = RateUpdateRequest.builder()
                .id(0L)
                .name("")
                .description("")
                .priceRate(List.of())
                .build();
        mockMvc.perform(put("/rates/update-rate")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(rateService, times(0)).updateRateByRequest(any());
    }

    @Test
    void saveRate() throws Exception {
        RateSaveRequest request = RateSaveRequest.builder()
                .name("Test")
                .description("Test")
                .priceRate(List.of(PriceRateRequest.builder().id(1L).price(BigDecimal.ONE).serviceId(1L).build()))
                .build();
        mockMvc.perform(post("/rates/save-rate")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(rateService, times(1)).saveRateByRequest(any());
    }

    @Test
    void saveRateBadRequest() throws Exception {
        RateSaveRequest request = RateSaveRequest.builder()
                .name("")
                .description("")
                .priceRate(List.of())
                .build();
        mockMvc.perform(post("/rates/save-rate")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(rateService, times(0)).saveRateByRequest(any());
    }

    @Test
    void deletePriceRateById() throws Exception {
        mockMvc.perform(delete("/rates/delete-price-rate/1"))
                .andExpect(status().isOk());
        verify(priceRateService, times(1)).deletePriceRateById(anyLong());
    }

    @Test
    void deletePriceRateByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(priceRateService)
                .deletePriceRateById(anyLong());
        mockMvc.perform(delete("/rates/delete-price-rate/1"))
                .andExpect(status().isNotFound());
        verify(priceRateService, times(1)).deletePriceRateById(anyLong());
    }

    @Test
    void deleteRateById() throws Exception {
        mockMvc.perform(delete("/rates/delete-rate/1"))
                .andExpect(status().isOk());
        verify(rateService, times(1)).deleteRateById(anyLong());
    }

    @Test
    void deleteRateByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(rateService)
                .deleteRateById(anyLong());
        mockMvc.perform(delete("/rates/delete-rate/1"))
                .andExpect(status().isNotFound());
        verify(rateService, times(1)).deleteRateById(anyLong());
    }

    @Test
    void deleteRateByIdBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(rateService)
                .deleteRateById(anyLong());
        mockMvc.perform(delete("/rates/delete-rate/1"))
                .andExpect(status().isBadRequest());
        verify(rateService, times(1)).deleteRateById(anyLong());
    }
}
