package lab.space.my_house_24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.bankBook.BankBookResponseForCashBox;
import lab.space.my_house_24.model.cash_box.CashBoxRequest;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.cash_box.CashBoxSaveRequest;
import lab.space.my_house_24.model.cash_box.CashBoxUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.user.UserResponseForTable;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.validator.CashBoxValidator;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CashBoxController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CashBoxControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private StaffService staffService;

    @MockBean
    private CashBoxService cashBoxService;

    @MockBean
    private BankBookService bankBookService;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private CashBoxValidator cashBoxValidator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showCashBoxPage() throws Exception {
        mockMvc.perform(get("/cash-box"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/cash_box/cash-box"));
    }

    @Test
    void showCashBoxCardById() throws Exception {
        mockMvc.perform(get("/cash-box/card-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/cash_box/cash-box-card"));
    }

    @Test
    void showCashBoxIncomeSavePage() throws Exception {
        mockMvc.perform(get("/cash-box/add-income"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/cash_box/cash-box-save"));
    }

    @Test
    void showCashBoxExpensiveSavePage() throws Exception {
        mockMvc.perform(get("/cash-box/add-expensive"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/cash_box/cash-box-save"));
    }

    @Test
    void showCashBoxCopySavePage() throws Exception {
        mockMvc.perform(get("/cash-box/add-copy-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/cash_box/cash-box-save"));
    }

    @Test
    void showCashBoxUpdatePage() throws Exception {
        mockMvc.perform(get("/cash-box/update-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/pages/cash_box/cash-box-save"));
    }

    @Test
    void getAllCashBox() throws Exception {
        Page<CashBoxResponse> cashBoxResponses = new PageImpl<>(List.of(
                CashBoxResponse.builder().build(),
                CashBoxResponse.builder().build(),
                CashBoxResponse.builder().build(),
                CashBoxResponse.builder().build()
        ));
        when(cashBoxService.getAllCashBoxResponse(any())).thenReturn(cashBoxResponses);
        mockMvc.perform(post("/cash-box/get-all-cash-box")
                        .content(objectMapper.writeValueAsString(CashBoxRequest.builder().build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(cashBoxResponses)));
    }

    @Test
    void getAllUser() throws Exception {
        Page<UserResponseForTable> userResponseForTables = new PageImpl<>(List.of(
                UserResponseForTable.builder().build(),
                UserResponseForTable.builder().build(),
                UserResponseForTable.builder().build(),
                UserResponseForTable.builder().build()
        ));
        when(userService.userResponseForSelect(anyInt(), anyString())).thenReturn(userResponseForTables);
        mockMvc.perform(get("/cash-box/get-all-owner")
                        .param("page", "1").param("search", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userResponseForTables)));
    }

    @Test
    void getExcel() throws Exception {
        mockMvc.perform(post("/cash-box/get-excel-cash-boxes")
                        .content(objectMapper.writeValueAsString(CashBoxRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getExcelIOException() throws Exception {
        doThrow(new IOException("IOException"))
                .when(cashBoxService)
                .getExcel((CashBoxRequest) any());
        mockMvc.perform(post("/cash-box/get-excel-cash-boxes")
                        .content(objectMapper.writeValueAsString(CashBoxRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGatewayTimeout());
    }

    @Test
    void testGetExcel() throws Exception {
        mockMvc.perform(get("/cash-box/get-excel-cash-box/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetExcelIOException() throws Exception {
        doThrow(new IOException("IOException"))
                .when(cashBoxService)
                .getExcel((Long) any());
        mockMvc.perform(get("/cash-box/get-excel-cash-box/1"))
                .andExpect(status().isGatewayTimeout());
    }

    @Test
    void testGetExcelEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(cashBoxService)
                .getExcel((Long) any());
        mockMvc.perform(get("/cash-box/get-excel-cash-box/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllDraft() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(cashBoxService.getDraft()).thenReturn(enumResponses);
        mockMvc.perform(get("/cash-box/get-all-draft"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllStaff() throws Exception {
        Page<StaffResponse> staffResponses = new PageImpl<>(List.of(
                StaffResponse.builder().build(),
                StaffResponse.builder().build(),
                StaffResponse.builder().build(),
                StaffResponse.builder().build()
        ));
        when(staffService.getAllStaffManager(anyInt(), anyString())).thenReturn(staffResponses);
        mockMvc.perform(get("/cash-box/get-all-staff")
                        .param("page", "1").param("search", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(staffResponses)));
    }

    @Test
    void getAllBankBook() throws Exception {
        Page<BankBookResponseForCashBox> bankBookResponseForCashBoxes = new PageImpl<>(List.of(
                BankBookResponseForCashBox.builder().build(),
                BankBookResponseForCashBox.builder().build(),
                BankBookResponseForCashBox.builder().build(),
                BankBookResponseForCashBox.builder().build()
        ));
        when(bankBookService.getBankBookListForCashBoxByUserId(anyInt(),anyLong(), anyString())).thenReturn(bankBookResponseForCashBoxes);
        mockMvc.perform(get("/cash-box/get-all-bank-book").param("id", "1")
                        .param("page", "1").param("search", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bankBookResponseForCashBoxes)));
    }

    @Test
    void testGetAllArticleType() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(articleService.getAllType()).thenReturn(enumResponses);
        mockMvc.perform(get("/cash-box/get-all-statement-type"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getCashBoxResponse() throws Exception {
        when(cashBoxService.getCashBoxResponseById(anyLong())).thenReturn(CashBoxResponse.builder().build());
        mockMvc.perform(get("/cash-box/get-cash-box-1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CashBoxResponse.builder().build())));
    }

    @Test
    void getCashBoxResponseEntityNotFoundException() throws Exception {
        when(cashBoxService.getCashBoxResponseById(anyLong())).thenThrow(new EntityNotFoundException("Not Found"));
        mockMvc.perform(get("/cash-box/get-cash-box-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllArticle() throws Exception {
        Page<ArticleResponse> articleResponses = new PageImpl<>(List.of(
                ArticleResponse.builder().build(),
                ArticleResponse.builder().build(),
                ArticleResponse.builder().build(),
                ArticleResponse.builder().build()
        ));
        when(articleService.getAllArticleResponseByType(anyInt(), anyBoolean(), anyString())).thenReturn(articleResponses);
        mockMvc.perform(get("/cash-box/get-all-article").param("type", "true")
                        .param("page", "1").param("search", "test"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(articleResponses)));
    }

    @Test
    void getNewCashBox() throws Exception {
        when(cashBoxService.getNewCashBoxResponse(anyBoolean())).thenReturn(CashBoxResponse.builder().build());
        mockMvc.perform(post("/cash-box/get-new-cash-box")
                        .content(objectMapper.writeValueAsString(true))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CashBoxResponse.builder().build())));
    }

    @Test
    void updateCashBox() throws Exception {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("123123")
                .createAt(LocalDate.now())
                .draft(false)
                .price(BigDecimal.ONE)
                .type(true)
                .comment("Test")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();
        mockMvc.perform(put("/cash-box/update-cash-box")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(cashBoxService, times(1)).updateCashBoxByRequest(any());
    }

    @Test
    void updateCashBoxEntityNotFound() throws Exception {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("123123")
                .createAt(LocalDate.now())
                .draft(false)
                .price(BigDecimal.ONE)
                .type(true)
                .comment("Test")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(cashBoxService)
                .updateCashBoxByRequest(any());
        mockMvc.perform(put("/cash-box/update-cash-box")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cashBoxService, times(1)).updateCashBoxByRequest(any());
    }

    @Test
    void updateCashBoxBadRequest() throws Exception {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(0L)
                .number("")
                .createAt(LocalDate.now())
                .draft(false)
                .price(BigDecimal.ZERO)
                .type(true)
                .comment("")
                .bankBookId(0L)
                .articleId(0L)
                .staffId(0L)
                .build();
        mockMvc.perform(put("/cash-box/update-cash-box")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(cashBoxService, times(0)).updateCashBoxByRequest(any());
    }

    @Test
    void saveCashBox() throws Exception {
        CashBoxSaveRequest request = CashBoxSaveRequest.builder()
                .number("123123")
                .createAt(LocalDate.now())
                .draft(false)
                .price(BigDecimal.ONE)
                .type(true)
                .comment("Test")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();
        mockMvc.perform(post("/cash-box/save-cash-box")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(cashBoxService, times(1)).saveCashBoxByRequest(any());
    }

    @Test
    void saveCashBoxEntityNotFound() throws Exception {
        CashBoxSaveRequest request = CashBoxSaveRequest.builder()
                .number("123123")
                .createAt(LocalDate.now())
                .draft(false)
                .price(BigDecimal.ONE)
                .type(true)
                .comment("Test")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(cashBoxService)
                .saveCashBoxByRequest(any());
        mockMvc.perform(post("/cash-box/save-cash-box")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cashBoxService, times(1)).saveCashBoxByRequest(any());
    }

    @Test
    void saveCashBoxBadRequest() throws Exception {
        CashBoxSaveRequest request = CashBoxSaveRequest.builder()
                .number("123123")
                .createAt(LocalDate.now())
                .draft(false)
                .price(BigDecimal.ZERO)
                .type(true)
                .comment("Test")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();
        mockMvc.perform(post("/cash-box/save-cash-box")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(cashBoxService, times(0)).saveCashBoxByRequest(any());
    }

    @Test
    void deleteCashBoxById() throws Exception {
        mockMvc.perform(delete("/cash-box/delete-cash-box/1"))
                .andExpect(status().isOk());
        verify(cashBoxService, times(1)).deleteCashBoxById(anyLong());
    }

    @Test
    void deleteCashBoxByIdEntityNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(cashBoxService)
                .deleteCashBoxById(anyLong());
        mockMvc.perform(delete("/cash-box/delete-cash-box/1"))
                .andExpect(status().isNotFound());
        verify(cashBoxService, times(1)).deleteCashBoxById(anyLong());
    }

    @Test
    void deleteCashBoxByIdBadRequest() throws Exception {
        doThrow(new IllegalArgumentException("Bad Request"))
                .when(cashBoxService)
                .deleteCashBoxById(anyLong());
        mockMvc.perform(delete("/cash-box/delete-cash-box/1"))
                .andExpect(status().isBadRequest());
        verify(cashBoxService, times(1)).deleteCashBoxById(anyLong());
    }

    @Test
    void getStatisticsForComing() throws Exception {
        List<BigDecimal> bigDecimals = List.of(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
        when(cashBoxService.statisticSumByType(anyBoolean())).thenReturn(bigDecimals);
        mockMvc.perform(get("/cash-box/get-statistics-for-coming"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bigDecimals)));
    }

    @Test
    void getStatisticsForCosts() throws Exception {
        List<BigDecimal> bigDecimals = List.of(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
        when(cashBoxService.statisticSumByType(anyBoolean())).thenReturn(bigDecimals);
        mockMvc.perform(get("/cash-box/get-statistics-for-costs"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(bigDecimals)));
    }
}
