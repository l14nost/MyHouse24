package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.enums.BankBookStatus;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBankBook;
import lab.space.my_house_24.model.bankBook.BankBookRequest;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bankBook.BankBookSaveRequest;
import lab.space.my_house_24.model.bankBook.BankBookUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.validator.ApartmentValidator;
import lab.space.my_house_24.validator.BankBookValidator;
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

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankBookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BankBookControllerTest {

    @MockBean
    private BankBookService bankBookService;

    @MockBean
    private BankBookValidator bankBookValidator;

    @MockBean
    private ApartmentValidator apartmentValidator;

    @MockBean
    private ApartmentService apartmentService;

    @MockBean
    private SectionService sectionService;

    @MockBean
    private HouseService houseService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showBankBookPage() throws Exception {
        mockMvc.perform(get("/bank-books"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bank_book/bank-book"));
    }

    @Test
    void showBankBookCardById() throws Exception {
        mockMvc.perform(get("/bank-books/card-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bank_book/bank-book-card"));
    }

    @Test
    void showBankBookSavePage() throws Exception {
        mockMvc.perform(get("/bank-books/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bank_book/bank-book-save"));
    }

    @Test
    void showBankBookUpdatePage() throws Exception {
        mockMvc.perform(get("/bank-books/update-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bank_book/bank-book-save"));
    }

    @Test
    void getAllBalanceStatus() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(bankBookService.getAllBalanceStatus()).thenReturn(enumResponses);
        mockMvc.perform(get("/bank-books/get-all-balance-status"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllUser() throws Exception {
        List<UserResponseForTable> userResponseForTables = List.of(
                UserResponseForTable.builder().build(),
                UserResponseForTable.builder().build(),
                UserResponseForTable.builder().build(),
                UserResponseForTable.builder().build()
        );
        when(userService.userListForTable()).thenReturn(userResponseForTables);
        mockMvc.perform(get("/bank-books/get-all-owner"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userResponseForTables)));
    }

    @Test
    void getAllSection() throws Exception {
        List<SectionResponseForTable> sectionResponseForTables = List.of(
                SectionResponseForTable.builder().build(),
                SectionResponseForTable.builder().build(),
                SectionResponseForTable.builder().build(),
                SectionResponseForTable.builder().build()
        );
        when(sectionService.sectionListForTable()).thenReturn(sectionResponseForTables);
        mockMvc.perform(get("/bank-books/get-all-section"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sectionResponseForTables)));
    }

    @Test
    void getAllBankBookStatus() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(bankBookService.getAllBankBookStatus()).thenReturn(enumResponses);
        mockMvc.perform(get("/bank-books/get-all-status"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllHouse() throws Exception {
        List<HouseResponseForTable> houseResponseForTables = List.of(
                HouseResponseForTable.builder().build(),
                HouseResponseForTable.builder().build(),
                HouseResponseForTable.builder().build(),
                HouseResponseForTable.builder().build()
        );
        when(houseService.houseListForTable()).thenReturn(houseResponseForTables);
        mockMvc.perform(get("/bank-books/get-all-house"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(houseResponseForTables)));
    }

    @Test
    void getSectionByHouse() throws Exception {
        List<SectionResponseForTable> sectionResponseForTables = List.of(
                SectionResponseForTable.builder().build(),
                SectionResponseForTable.builder().build(),
                SectionResponseForTable.builder().build(),
                SectionResponseForTable.builder().build()
        );
        when(sectionService.sectionByHouse(anyLong())).thenReturn(sectionResponseForTables);
        mockMvc.perform(get("/bank-books/get-section/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sectionResponseForTables)));
    }

    @Test
    void getAllApartmentByHouse() throws Exception {
        List<ApartmentResponseForBankBook> apartmentResponseForBankBooks = List.of(
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build()
        );
        when(apartmentService.getAllApartmentResponseByHouseId(anyLong())).thenReturn(apartmentResponseForBankBooks);
        mockMvc.perform(get("/bank-books/get-all-apartment-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(apartmentResponseForBankBooks)));
    }

    @Test
    void getAllApartmentByHouseAndSection() throws Exception {
        List<ApartmentResponseForBankBook> apartmentResponseForBankBooks = List.of(
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build()
        );
        when(apartmentService.getAllApartmentResponseByHouseIdAndSectionId(anyLong(), anyLong())).thenReturn(apartmentResponseForBankBooks);
        mockMvc.perform(get("/bank-books/get-all-apartment-1/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(apartmentResponseForBankBooks)));
    }

    @Test
    void getAllApartment() throws Exception {
        List<ApartmentResponseForBankBook> apartmentResponseForBankBooks = List.of(
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build(),
                ApartmentResponseForBankBook.builder().build()
        );
        when(apartmentService.getAllApartmentResponse()).thenReturn(apartmentResponseForBankBooks);
        mockMvc.perform(get("/bank-books/get-all-apartment"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(apartmentResponseForBankBooks)));
    }

    @Test
    void getBankBookResponse() throws Exception {
        BankBookResponse bankBookResponse = BankBookResponse.builder().build();
        when(bankBookService.getBankBookResponseById(anyLong())).thenReturn(bankBookResponse);
        mockMvc.perform(get("/bank-books/get-bank-book-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bankBookResponse)));
    }

    @Test
    void getBankBookResponseEntityNotFound() throws Exception {
        when(bankBookService.getBankBookResponseById(anyLong())).thenThrow(new EntityNotFoundException("Not found"));
        mockMvc.perform(get("/bank-books/get-bank-book-0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllBankBookResponse() throws Exception {
        Page<BankBookResponse> bankBookResponses = new PageImpl<>(List.of(
                BankBookResponse.builder().build(),
                BankBookResponse.builder().build(),
                BankBookResponse.builder().build(),
                BankBookResponse.builder().build()
        ));
        when(bankBookService.getAllBankBookResponse(any())).thenReturn(bankBookResponses);
        mockMvc.perform(post("/bank-books/get-all-bank-book")
                        .content(objectMapper.writeValueAsString(BankBookRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bankBookResponses)));
    }

    @Test
    void getExcel() throws Exception {
        mockMvc.perform(post("/bank-books/get-excel-bank-books")
                        .content(objectMapper.writeValueAsString(BankBookRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getExcelIOException() throws Exception {
        doThrow(new IOException("IOException"))
                .when(bankBookService)
                .getExcel(any());
        mockMvc.perform(post("/bank-books/get-excel-bank-books")
                        .content(objectMapper.writeValueAsString(BankBookRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGatewayTimeout());
    }

    @Test
    void updateBankBook() throws Exception {
        BankBookUpdateRequest request = BankBookUpdateRequest.builder()
                .id(1L)
                .status(BankBookStatus.INACTIVE)
                .apartmentId(1L)
                .number("55555-55555")
                .build();
        mockMvc.perform(put("/bank-books/update-bank-book")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bankBookService, times(1)).updateBankBookByRequest(any());
    }

    @Test
    void updateBankBookBadRequest() throws Exception {
        BankBookUpdateRequest request = BankBookUpdateRequest.builder()
                .id(0L)
                .status(BankBookStatus.INACTIVE)
                .apartmentId(0L)
                .number("0000")
                .build();
        mockMvc.perform(put("/bank-books/update-bank-book")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(bankBookService, times(0)).updateBankBookByRequest(any());
    }

    @Test
    void updateBankBookEntityNotFound() throws Exception {
        BankBookUpdateRequest request = BankBookUpdateRequest.builder()
                .id(1L)
                .status(BankBookStatus.INACTIVE)
                .apartmentId(1L)
                .number("55555-55555")
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(bankBookService)
                .updateBankBookByRequest(any());
        mockMvc.perform(put("/bank-books/update-bank-book")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(bankBookService, times(1)).updateBankBookByRequest(any());
    }

    @Test
    void saveBankBook() throws Exception {
        BankBookSaveRequest request = BankBookSaveRequest.builder()
                .status(BankBookStatus.INACTIVE)
                .apartmentId(1L)
                .number("55555-55555")
                .build();
        mockMvc.perform(post("/bank-books/save-bank-book")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bankBookService, times(1)).saveBankBookByRequest(any());
    }

    @Test
    void saveBankBookBadRequest() throws Exception {
        BankBookSaveRequest request = BankBookSaveRequest.builder()
                .status(BankBookStatus.INACTIVE)
                .apartmentId(0L)
                .number("0000")
                .build();
        mockMvc.perform(post("/bank-books/save-bank-book")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(bankBookService, times(0)).saveBankBookByRequest(any());
    }

    @Test
    void saveBankBookEntityNotFound() throws Exception {
        BankBookSaveRequest request = BankBookSaveRequest.builder()
                .status(BankBookStatus.INACTIVE)
                .apartmentId(1L)
                .number("55555-55555")
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(bankBookService)
                .saveBankBookByRequest(any());
        mockMvc.perform(post("/bank-books/save-bank-book")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(bankBookService, times(1)).saveBankBookByRequest(any());
    }

    @Test
    void deleteBankBookById() throws Exception {
        mockMvc.perform(delete("/bank-books/delete-bank-book/1"))
                .andExpect(status().isOk());
        verify(bankBookService, times(1)).deleteBankBookById(any());
    }

    @Test
    void deleteBankBookByIdBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(bankBookService)
                .deleteBankBookById(anyLong());
        mockMvc.perform(delete("/bank-books/delete-bank-book/0"))
                .andExpect(status().isBadRequest());
        verify(bankBookService, times(1)).deleteBankBookById(any());
    }

    @Test
    void deleteBankBookByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(bankBookService)
                .deleteBankBookById(anyLong());
        mockMvc.perform(delete("/bank-books/delete-bank-book/1"))
                .andExpect(status().isNotFound());
        verify(bankBookService, times(1)).deleteBankBookById(any());
    }
}
