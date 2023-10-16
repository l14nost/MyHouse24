package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.enums.BillStatus;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBankBook;
import lab.space.my_house_24.model.apartment.ApartmentResponseForBill;
import lab.space.my_house_24.model.bankBook.BankBookRequest;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bill.*;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.meterReading.MeterReadingResponseForBill;
import lab.space.my_house_24.model.rate.RateResponse;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service_bill.ServiceBillRequest;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.validator.BillValidator;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BillControllerTest {

    @MockBean
    private BillService billService;

    @MockBean
    private BankBookService bankBookService;

    @MockBean
    private RateService rateService;

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

    @MockBean
    private ServiceBillService serviceBillService;

    @MockBean
    private BillValidator billValidator;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showBillPage() throws Exception {
        mockMvc.perform(get("/bills"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bill/bill"));
    }

    @Test
    void showBillCardById() throws Exception {
        mockMvc.perform(get("/bills/card-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bill/bill-card"));
    }

    @Test
    void showBillSavePage() throws Exception {
        mockMvc.perform(get("/bills/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bill/bill-save"));
    }

    @Test
    void showRateCopySavePage() throws Exception {
        mockMvc.perform(get("/bills/add-copy-bank-book-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bill/bill-save"));
    }

    @Test
    void showBillCopySavePage() throws Exception {
        mockMvc.perform(get("/bills/add-copy-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bill/bill-save"));
    }

    @Test
    void showBillUpdatePage() throws Exception {
        mockMvc.perform(get("/bills/update-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/bill/bill-save"));
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
        mockMvc.perform(get("/bills/get-all-owner"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllDraft() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(billService.getDraft()).thenReturn(enumResponses);
        mockMvc.perform(get("/bills/get-all-draft"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllBillResponse() throws Exception {
        Page<BillResponse> billResponses = new PageImpl<>(List.of(
                BillResponse.builder().build(),
                BillResponse.builder().build(),
                BillResponse.builder().build(),
                BillResponse.builder().build()
        ));
        when(billService.getAllBillResponse(any())).thenReturn(billResponses);
        mockMvc.perform(post("/bills/get-all-bills")
                        .content(objectMapper.writeValueAsString(BillRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(billResponses)));
    }

    @Test
    void getExcel() throws Exception {
        mockMvc.perform(post("/bills/get-excel-bills")
                        .content(objectMapper.writeValueAsString(BankBookRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getExcelIOException() throws Exception {
        doThrow(new IOException("IOException"))
                .when(billService)
                .getExcel((BillRequest) any());
        mockMvc.perform(post("/bills/get-excel-bills")
                        .content(objectMapper.writeValueAsString(BankBookRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGatewayTimeout());
    }

    @Test
    void testGetExcel() throws Exception {
        mockMvc.perform(get("/bills/get-excel-bill/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetExcelIOException() throws Exception {
        doThrow(new IOException("IOException"))
                .when(billService)
                .getExcel((Long) any());
        mockMvc.perform(get("/bills/get-excel-bill/1"))
                .andExpect(status().isGatewayTimeout());
    }

    @Test
    void testGetExcelEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(billService)
                .getExcel((Long) any());
        mockMvc.perform(get("/bills/get-excel-bill/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankBookResponse() throws Exception {
        BankBookResponse bankBookResponse = BankBookResponse.builder().apartment(ApartmentResponseForBankBook.builder().build()).build();
        when(bankBookService.getBankBookResponseById(anyLong())).thenReturn(bankBookResponse);
        mockMvc.perform(get("/bills/get-bank-book-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bankBookResponse)));
    }

    @Test
    void getBankBookResponseWithOutApartmentEntityNotFound() throws Exception {
        BankBookResponse bankBookResponse = BankBookResponse.builder().build();
        when(bankBookService.getBankBookResponseById(anyLong())).thenReturn(bankBookResponse);
        mockMvc.perform(get("/bills/get-bank-book-0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBankBookResponseEntityNotFound() throws Exception {
        when(bankBookService.getBankBookResponseById(anyLong())).thenThrow(new EntityNotFoundException("Not Found"));
        mockMvc.perform(get("/bills/get-bank-book-0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBillResponse() throws Exception {
        BillResponse billResponse = BillResponse.builder().build();
        when(billService.getBillResponseById(anyLong())).thenReturn(billResponse);
        mockMvc.perform(get("/bills/get-bill-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(billResponse)));
    }

    @Test
    void getBillResponseEntityNotFound() throws Exception {
        when(billService.getBillResponseById(anyLong())).thenThrow(new EntityNotFoundException("Not Found"));
        mockMvc.perform(get("/bills/get-bill-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRateResponse() throws Exception {
        RateResponse rateResponse = RateResponse.builder().build();
        when(rateService.getRateByIdResponseForBill(anyLong())).thenReturn(rateResponse);
        mockMvc.perform(get("/bills/get-rate-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(rateResponse)));
    }

    @Test
    void getRateResponseEntityNotFound() throws Exception {
        when(rateService.getRateByIdResponseForBill(anyLong())).thenThrow(new EntityNotFoundException("Not Found"));
        mockMvc.perform(get("/bills/get-rate-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNewBill() throws Exception {
        BillResponse billResponse = BillResponse.builder().build();
        when(billService.getNewBillResponse()).thenReturn(billResponse);
        mockMvc.perform(get("/bills/get-new-bill"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(billResponse)));
    }

    @Test
    void getAllRates() throws Exception {
        Page<RateResponse> rateResponses = new PageImpl<>(List.of(
                RateResponse.builder().build(),
                RateResponse.builder().build(),
                RateResponse.builder().build(),
                RateResponse.builder().build()
        ));
        when(rateService.getAllRatesForBill(anyInt(), anyString())).thenReturn(rateResponses);
        mockMvc.perform(get("/bills/get-all-rates").param("pageIndex", "1").param("search", "Test"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllService() throws Exception {
        Page<ServiceResponse> serviceResponses = new PageImpl<>(List.of(
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build(),
                ServiceResponse.builder().build()
        ));
        when(serviceService.getAllServicesByIsActiveDto(anyInt(), anyString())).thenReturn(serviceResponses);
        mockMvc.perform(get("/bills/get-all-services").param("page", "1").param("search", "Test"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(serviceResponses)));
    }

    @Test
    void getAllStatus() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(billService.getAllBillStatus()).thenReturn(enumResponses);
        mockMvc.perform(get("/bills/get-all-status"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllMeterReadingByRequest() throws Exception {
        Page<MeterReadingResponseForBill> meterReadingResponseForBills = new PageImpl<>(List.of(
                MeterReadingResponseForBill.builder().build(),
                MeterReadingResponseForBill.builder().build(),
                MeterReadingResponseForBill.builder().build(),
                MeterReadingResponseForBill.builder().build()
        ));
        when(meterReadingService.getAllMeterReadingResponseByRequest(any())).thenReturn(meterReadingResponseForBills);
        mockMvc.perform(post("/bills/get-all-meter-reading")
                        .content(objectMapper.writeValueAsString(BillRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(meterReadingResponseForBills)));
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
        mockMvc.perform(get("/bills/get-all-house"))
                .andExpect(status().isOk());
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
        mockMvc.perform(get("/bills/get-section/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllApartment() throws Exception {
        List<ApartmentResponseForBill> apartmentResponseForBills = List.of(
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build()
        );
        when(apartmentService.getAllApartmentResponseForBill()).thenReturn(apartmentResponseForBills);
        mockMvc.perform(get("/bills/get-all-apartment"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllApartmentByHouse() throws Exception {
        List<ApartmentResponseForBill> apartmentResponseForBills = List.of(
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build()
        );
        when(apartmentService.getAllApartmentResponseByHouseIdForBill(anyLong())).thenReturn(apartmentResponseForBills);
        mockMvc.perform(get("/bills/get-all-apartment-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(apartmentResponseForBills)));
    }

    @Test
    void getAllApartmentByHouseAndSection() throws Exception {
        List<ApartmentResponseForBill> apartmentResponseForBills = List.of(
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build(),
                ApartmentResponseForBill.builder().build()
        );
        when(apartmentService.getAllApartmentResponseByHouseIdAndSectionIdForBill(anyLong(), anyLong())).thenReturn(apartmentResponseForBills);
        mockMvc.perform(get("/bills/get-all-apartment-1/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(apartmentResponseForBills)));
    }

    @Test
    void updateBill() throws Exception {
        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("12312313")
                .createAt(LocalDate.now())
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.ZERO)
                .payed(BigDecimal.ZERO)
                .bankBookId(1L)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .count(10101.12)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        mockMvc.perform(put("/bills/update-bill")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(billService, times(1)).updateBillByRequest(any());
    }

    @Test
    void updateBillBadRequest() throws Exception {
        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(0L)
                .number("12312313123123123")
                .createAt(LocalDate.now())
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(0L)
                .totalPrice(BigDecimal.ZERO)
                .payed(BigDecimal.ZERO)
                .bankBookId(0L)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(0L)
                        .serviceId(0L)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .count(0.0)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        mockMvc.perform(put("/bills/update-bill")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(billService, times(0)).updateBillByRequest(any());
    }

    @Test
    void updateBillEntityNotFound() throws Exception {
        BillUpdateRequest request = BillUpdateRequest.builder()
                .id(1L)
                .number("12312313")
                .createAt(LocalDate.now())
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.ZERO)
                .payed(BigDecimal.ZERO)
                .bankBookId(1L)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .count(10101.12)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(billService)
                .updateBillByRequest(any());
        mockMvc.perform(put("/bills/update-bill")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(billService, times(1)).updateBillByRequest(any());
    }

    @Test
    void saveBill() throws Exception {
        BillSaveRequest request = BillSaveRequest.builder()
                .number("12312313")
                .createAt(LocalDate.now())
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.ZERO)
                .payed(BigDecimal.ZERO)
                .bankBookId(1L)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .count(10101.12)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        mockMvc.perform(post("/bills/save-bill")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(billService, times(1)).saveBillByRequest(any());
    }

    @Test
    void saveBillBadRequest() throws Exception {
        BillSaveRequest request = BillSaveRequest.builder()
                .number("12312313123123123")
                .createAt(LocalDate.now())
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(0L)
                .totalPrice(BigDecimal.ZERO)
                .payed(BigDecimal.ZERO)
                .bankBookId(0L)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(0L)
                        .serviceId(0L)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .count(0.0)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        mockMvc.perform(post("/bills/save-bill")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(billService, times(0)).saveBillByRequest(any());
    }

    @Test
    void saveBillEntityNotFound() throws Exception {
        BillSaveRequest request = BillSaveRequest.builder()
                .number("12312313")
                .createAt(LocalDate.now())
                .draft(true)
                .status(BillStatus.PAID)
                .rateId(1L)
                .totalPrice(BigDecimal.ZERO)
                .payed(BigDecimal.ZERO)
                .bankBookId(1L)
                .serviceBillList(List.of(ServiceBillRequest.builder()
                        .id(1L)
                        .serviceId(1L)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .count(10101.12)
                        .build()))
                .periodOf(LocalDate.now())
                .periodTo(LocalDate.now())
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(billService)
                .saveBillByRequest(any());
        mockMvc.perform(post("/bills/save-bill")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(billService, times(1)).saveBillByRequest(any());
    }

    @Test
    void deleteServiceBillById() throws Exception {
        mockMvc.perform(delete("/bills/delete-service-bill/1"))
                .andExpect(status().isOk());
        verify(serviceBillService, times(1)).deleteServiceBillById(anyLong());
    }

    @Test
    void deleteServiceBillByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(serviceBillService)
                .deleteServiceBillById(anyLong());
        mockMvc.perform(delete("/bills/delete-service-bill/1"))
                .andExpect(status().isNotFound());
        verify(serviceBillService, times(1)).deleteServiceBillById(anyLong());
    }

    @Test
    void deleteBillByRequest() throws Exception {
        mockMvc.perform(delete("/bills/delete-bills")
                        .content(objectMapper.writeValueAsString(List.of(BillDeleteRequest.builder().build())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(billService, times(1)).deleteBillByRequest(any());
    }

    @Test
    void deleteBillByRequestEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(billService)
                .deleteBillByRequest(any());
        mockMvc.perform(delete("/bills/delete-bills")
                        .content(objectMapper.writeValueAsString(List.of(BillDeleteRequest.builder().build())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(billService, times(1)).deleteBillByRequest(any());
    }

    @Test
    void deleteBillByRequestBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(billService)
                .deleteBillByRequest(any());
        mockMvc.perform(delete("/bills/delete-bills")
                        .content(objectMapper.writeValueAsString(List.of(BillDeleteRequest.builder().build())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(billService, times(1)).deleteBillByRequest(any());
    }

    @Test
    void deleteBillById() throws Exception {
        mockMvc.perform(delete("/bills/delete-bill/1"))
                .andExpect(status().isOk());
        verify(billService, times(1)).deleteBillById(anyLong());
    }

    @Test
    void deleteBillByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(billService)
                .deleteBillById(anyLong());
        mockMvc.perform(delete("/bills/delete-bill/1"))
                .andExpect(status().isNotFound());
        verify(billService, times(1)).deleteBillById(anyLong());
    }

    @Test
    void deleteBillByIdBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(billService)
                .deleteBillById(anyLong());
        mockMvc.perform(delete("/bills/delete-bill/1"))
                .andExpect(status().isBadRequest());
        verify(billService, times(1)).deleteBillById(anyLong());
    }

    @Test
    void getSumOfAllBills() throws Exception {
        List<BigDecimal> bigDecimals = List.of(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
        when(billService.sumOffAllBillsByMonths()).thenReturn(bigDecimals);
        mockMvc.perform(get("/bills/get-sum-of-all-bills"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bigDecimals)));
    }

    @Test
    void getSumOfAllPaidBills() throws Exception {
        List<BigDecimal> bigDecimals = List.of(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
        when(billService.sumOffAllPaidBillsByMonths()).thenReturn(bigDecimals);
        mockMvc.perform(get("/bills/get-sum-of-all-paid-bills"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bigDecimals)));
    }
}
