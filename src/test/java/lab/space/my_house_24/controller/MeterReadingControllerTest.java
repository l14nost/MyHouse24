package lab.space.my_house_24.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.entity.Apartment;
import lab.space.my_house_24.enums.MeterReadingStatus;
import lab.space.my_house_24.model.apartment.ApartmentResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.meterReading.*;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.service.ServiceResponseForSelect;
import lab.space.my_house_24.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeterReadingController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MeterReadingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MeterReadingService meterReadingService;
    @MockBean
    private ApartmentService apartmentService;
    @MockBean
    private HouseService houseService;
    @MockBean
    private SectionService sectionService;
    @MockBean
    private ServiceService serviceService;

    @Test
    void meterReadingPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/meter-readings"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/meterReading/meter-reading-main"));
    }

    @Test
    void getAllMeterReading() throws Exception {
        MeterReadingRequestForMainPage meterReadingRequestForMainPage = MeterReadingRequestForMainPage.builder().page(1).build();
        when(meterReadingService.findAllForMain(meterReadingRequestForMainPage)).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(MockMvcRequestBuilders.post("/meter-readings/get-all-meter-reading")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meterReadingRequestForMainPage)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));

    }

    @Test
    void addMeterReadingPage() throws Exception {
        when(meterReadingService.count()).thenReturn(1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/meter-readings/add-meter-reading"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/meterReading/meter-reading-add"));

    }

    @Test
    void addMeterReading() throws Exception {
        MeterReadingRequestForAdd meterReadingRequestForAdd = MeterReadingRequestForAdd.builder()
                .service(1L)
                .status(MeterReadingStatus.CONSIDERED)
                .date(LocalDate.of(2021,12,12))
                .count(123.1)
                .section(1L)
                .house(1L)
                .apartment(1L)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/meter-readings/add-meter-reading")
                        .content(objectMapper.writeValueAsString(meterReadingRequestForAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void addMeterReading_Valid() throws Exception {
        MeterReadingRequestForAdd meterReadingRequestForAdd = MeterReadingRequestForAdd.builder()
                .service(1L)
                .status(MeterReadingStatus.CONSIDERED)
                .date(LocalDate.of(2021,12,12))
                .count(123.1)
                .house(1L)
                .apartment(1L)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/meter-readings/add-meter-reading")
                        .content(objectMapper.writeValueAsString(meterReadingRequestForAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void editMeterReadingPage() throws Exception {
        when(meterReadingService.findByIdEdit(1L)).thenReturn(MeterReadingResponseEdit.builder()
                .number("000000001")
                .section(SectionResponseForTable.builder().build())
                .house(HouseResponseForTable.builder().build())
                .apartment(ApartmentResponseForTable.builder().build())
                .service(ServiceResponseForSelect.builder().build())
                .date(LocalDate.of(2021,12,12))
                .status(MeterReadingStatus.CONSIDERED)
                .build());
        when(houseService.houseListForTable()).thenReturn(List.of());
        when(apartmentService.apartmentListForSelect()).thenReturn(List.of());
        when(serviceService.serviceListForTable()).thenReturn(List.of());
        when(sectionService.sectionListForTable()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/meter-readings/edit-meter-reading/1"))
                .andExpect(status().isOk());
    }

    @Test
    void editMeterReading() throws Exception {
        MeterReadingRequestForEdit meterReadingRequestForEdit = MeterReadingRequestForEdit.builder()
                .service(1L)
                .status(MeterReadingStatus.CONSIDERED)
                .date(LocalDate.of(2021,12,12))
                .count(123.1)
                .section(1L)
                .house(1L)
                .apartment(1L)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/meter-readings/edit-meter-reading/1")
                        .content(objectMapper.writeValueAsString(meterReadingRequestForEdit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(meterReadingService, times(1)).update(meterReadingRequestForEdit, 1L);
    }

    @Test
    void editMeterReading_Valid() throws Exception {
        MeterReadingRequestForEdit meterReadingRequestForEdit = MeterReadingRequestForEdit.builder()
                .service(1L)
                .status(MeterReadingStatus.CONSIDERED)
                .date(LocalDate.of(2021,12,12))
                .count(123.1)
                .house(1L)
                .apartment(1L)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/meter-readings/edit-meter-reading/1")
                        .content(objectMapper.writeValueAsString(meterReadingRequestForEdit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(meterReadingService, times(0)).update(meterReadingRequestForEdit, 1L);
    }

    @Test
    void meterReadingByApartmentPage() throws Exception {
        when(apartmentService.findById(1L)).thenReturn(Apartment.builder().number(100).build());
        mockMvc.perform(MockMvcRequestBuilders.get("/meter-readings/meter-readings-by-apartment")
                        .param("idApartment", "1")
                        .param("idService", "1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/meterReading/meter-reading-apartment"));
    }

    @Test
    void getAllMeterReadingByApartment() throws Exception {
        MeterReadingRequestForApartmentPage meterReadingRequestForApartmentPage = MeterReadingRequestForApartmentPage.builder()
                .page(1)
                .build();
        when(meterReadingService.findAllForApartment(meterReadingRequestForApartmentPage)).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(MockMvcRequestBuilders.post("/meter-readings/get-all-meter-reading-by-apartment")
                        .content(objectMapper.writeValueAsString(meterReadingRequestForApartmentPage))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));
    }

    @Test
    void deleteMeterReading() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/meter-readings/delete-meter-reading/1"))
                .andExpect(status().isOk());
        verify(meterReadingService, times(1)).delete(1L);

    }

    @Test
    void meterReadingByApartmentAddPage() throws Exception {
        when(apartmentService.count()).thenReturn(1L);
        when(meterReadingService.findByIdForApartmentAdd(1L,1L)).thenReturn(MeterReadingResponseEdit.builder()
                .number("000000001")
                .section(SectionResponseForTable.builder().build())
                .house(HouseResponseForTable.builder().build())
                .apartment(ApartmentResponseForTable.builder().build())
                .service(ServiceResponseForSelect.builder().build())
                .date(LocalDate.of(2021,12,12))
                .status(MeterReadingStatus.CONSIDERED)
                .build());
        when(houseService.houseListForTable()).thenReturn(List.of());
        when(apartmentService.apartmentListForSelect()).thenReturn(List.of());
        when(serviceService.serviceListForTable()).thenReturn(List.of());
        when(sectionService.sectionListForTable()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/meter-readings/add-meter-reading-apartment")
                        .param("idApartment", "1")
                        .param("idService", "1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/meterReading/meter-reading-add-apartment"));

    }

    @Test
    void meterReadingByApartmentAddPage_Null() throws Exception {
        when(apartmentService.count()).thenReturn(1L);
        when(meterReadingService.findByIdForApartmentAdd(1L,1L)).thenReturn(null);
        when(houseService.houseListForTable()).thenReturn(List.of());
        when(apartmentService.apartmentListForSelect()).thenReturn(List.of());
        when(serviceService.serviceListForTable()).thenReturn(List.of());
        when(sectionService.sectionListForTable()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/meter-readings/add-meter-reading-apartment")
                        .param("idApartment", "1")
                        .param("idService", "1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/meterReading/meter-reading-add"));

    }
}