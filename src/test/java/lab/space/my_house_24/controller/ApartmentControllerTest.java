package lab.space.my_house_24.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24.model.apartment.ApartmentAddRequest;
import lab.space.my_house_24.model.apartment.ApartmentRequestForMainPage;
import lab.space.my_house_24.model.apartment.ApartmentResponseForCard;
import lab.space.my_house_24.model.apartment.ApartmentResponseForEdit;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.model.floor.FloorResponseForTable;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.message.MessageMainPageRequest;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.service.ApartmentService;
import lab.space.my_house_24.service.BankBookService;
import lab.space.my_house_24.service.HouseService;
import lab.space.my_house_24.service.RateService;
import lab.space.my_house_24.validator.ApartmentValidator;
import lab.space.my_house_24.validator.BankBookValidator;
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
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApartmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ApartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HouseService houseService;
    @MockBean
    private ApartmentService apartmentService;
    @MockBean
    private RateService rateService;
    @MockBean
    private BankBookService bankBookService;
    @MockBean
    private BankBookValidator bankBookValidator;
    @MockBean
    private ApartmentValidator apartmentValidator;


    @Test
    void apartmentMain() throws Exception {
        when(houseService.houseListForTable()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/apartment/apartment-main"));
    }

    @Test
    void allApartment() throws Exception {
        ApartmentRequestForMainPage apartmentRequestForMainPage = new ApartmentRequestForMainPage(1,100,"house","section","floor","owner",true);
        when(apartmentService.findAllForMainPage(apartmentRequestForMainPage)).thenReturn(new PageImpl<>(List.of()));
        mockMvc.perform(MockMvcRequestBuilders.post("/apartments/get-all-apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apartmentRequestForMainPage)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new PageImpl<>(List.of()))));

    }

    @Test
    void deleteApartment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/apartments/delete-apartment/1"))
                .andExpect(status().isOk());

        verify(apartmentService, times(1)).deleteApartment(1L);

    }

    @Test
    void apartmentCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments/apartment-card/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/apartment/apartment-card"));
    }

    @Test
    void getApartmentById() throws Exception {
        when(apartmentService.findByIdForCard(1L)).thenReturn(ApartmentResponseForCard.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments/get-apartment-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(ApartmentResponseForCard.builder().build())));
    }

    @Test
    void addApartmentPage() throws Exception {
        when(houseService.houseListForTable()).thenReturn(List.of());
        when(rateService.rateListForTable()).thenReturn(List.of());
        when(bankBookService.bankBookListForTable()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments/add-apartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/pages/apartment/apartment-add"));

    }

    @Test
    void addApartment() throws Exception {
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .owner(1L)
                .floor(1L)
                .house(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/apartments/add-apartment-save")
                        .content(objectMapper.writeValueAsString(apartmentAddRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(apartmentService, times(1)).saveApartment(apartmentAddRequest);
        verify(bankBookValidator, times(1)).busyBankBook(anyString(), any(BindingResult.class), anyString(), anyString(), anyLong());
        verify(apartmentValidator, times(1)).checkUniqueApartmentNumber(anyInt(),anyString(),anyLong(),anyLong(), anyString(),any(BindingResult.class));
    }

    @Test
    void addApartment_Valid() throws Exception {
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .floor(1L)
                .house(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/apartments/add-apartment-save")
                        .content(objectMapper.writeValueAsString(apartmentAddRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(apartmentService, times(0)).saveApartment(apartmentAddRequest);
        verify(bankBookValidator, times(1)).busyBankBook(anyString(), any(BindingResult.class), anyString(), anyString(), anyLong());
        verify(apartmentValidator, times(1)).checkUniqueApartmentNumber(anyInt(),anyString(),anyLong(),anyLong(), anyString(),any(BindingResult.class));
    }

    @Test
    void editApartmentPage() throws Exception {
        when(apartmentService.findByIdApartment(1L)).thenReturn(ApartmentResponseForEdit.builder().section(SectionResponseForTable.builder().build()).floor(FloorResponseForTable.builder().build()).bankBook(BankBookResponseForTable.builder().build()).house(HouseResponseForTable.builder().build()).owner(UserResponseForTable.builder().build()).build());
        when(houseService.houseListForTable()).thenReturn(List.of());
        when(rateService.rateListForTable()).thenReturn(List.of());
        when(bankBookService.bankBookListForTable()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments/edit-apartment/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/pages/apartment/apartment-edit"));
    }

    @Test
    void editApartment() throws Exception {
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .owner(1L)
                .floor(1L)
                .house(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/apartments/edit-apartment/1")
                        .content(objectMapper.writeValueAsString(apartmentAddRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(apartmentService, times(1)).updateApartment(1L, apartmentAddRequest);
        verify(bankBookValidator, times(1)).busyBankBook(anyString(), any(BindingResult.class), anyString(), anyString(), anyLong());
        verify(apartmentValidator, times(1)).checkUniqueApartmentNumber(anyInt(),anyString(),anyLong(),anyLong(), anyString(),any(BindingResult.class));
    }

    @Test
    void editApartment_Valid() throws Exception {
        ApartmentAddRequest apartmentAddRequest = ApartmentAddRequest.builder()
                .area(20.01)
                .bankBook("0000001")
                .owner(1L)
                .floor(1L)
                .section(1L)
                .rate(1L)
                .number(100)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/apartments/edit-apartment/1")
                        .content(objectMapper.writeValueAsString(apartmentAddRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(apartmentService, times(0)).updateApartment(1L, apartmentAddRequest);
        verify(bankBookValidator, times(1)).busyBankBook(anyString(), any(BindingResult.class), anyString(), anyString(), anyLong());
        verify(apartmentValidator, times(0)).checkUniqueApartmentNumber(anyInt(),anyString(),anyLong(),anyLong(), anyString(),any(BindingResult.class));
    }

    @Test
    void getApartmentByHouse() throws Exception {
        when(apartmentService.apartmentForSelect(1L,1L,1L,true)).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/apartments/get-apartment")
                        .param("idHouse", "1")
                        .param("idSection", "1")
                        .param("idFloor", "1")
                        .param("duty", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));

    }
}