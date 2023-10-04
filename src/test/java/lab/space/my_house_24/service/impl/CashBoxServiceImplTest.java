package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.entity.*;
import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.enums.JobTitle;
import lab.space.my_house_24.model.cash_box.CashBoxRequest;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.cash_box.CashBoxSaveRequest;
import lab.space.my_house_24.model.cash_box.CashBoxUpdateRequest;
import lab.space.my_house_24.repository.CashBoxRepository;
import lab.space.my_house_24.service.*;
import lab.space.my_house_24.specification.CashBoxSpecification;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashBoxServiceImplTest {

    @Mock
    private CashBoxRepository cashBoxRepository;

    @Mock
    private CashBoxSpecification cashBoxSpecification;

    @Mock
    private MessageSource message;

    @Mock
    private ArticleService articleService;

    @Mock
    private StaffService staffService;

    @Mock
    private BankBookService bankBookService;

    @Mock
    private BillService billService;

    @Mock
    private StatisticService statisticService;

    @Mock
    private ExcelService excelService;

    private static MockedStatic<Workbook> workbookMockedStatic;
    private static MockedStatic<ByteArrayOutputStream> byteArrayOutputStreamMockedStatic;

    @InjectMocks
    private CashBoxServiceImpl cashBoxService;

    @BeforeAll
    public static void setUp() {
        workbookMockedStatic = Mockito.mockStatic(Workbook.class);
        byteArrayOutputStreamMockedStatic = Mockito.mockStatic(ByteArrayOutputStream.class);
    }

    @AfterAll
    public static void tearDown() {
        workbookMockedStatic.close();
        byteArrayOutputStreamMockedStatic.close();
    }

    @Test
    void getCashBoxById() {
        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(CashBox.builder().build()));
        assertEquals(CashBox.builder().build(), cashBoxService.getCashBoxById(1L));
    }

    @Test
    void getCashBoxResponseByIdDraftTrue() {
        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(CashBox.builder()
                .id(1L)
                .number("123123123")
                .createAt(LocalDateTime.now())
                .draft(true)
                .price(BigDecimal.ZERO)
                .comment("Test")
                .type(false)
                .bankBook(null)
                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                .build()));
        assertEquals(1L, cashBoxService.getCashBoxResponseById(1L).id());
        verify(cashBoxRepository, times(1)).findById(anyLong());
    }

    @Test
    void getCashBoxResponseByIdDraftFalse() {
        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(CashBox.builder()
                .id(1L)
                .number("123123123")
                .createAt(LocalDateTime.now())
                .draft(false)
                .price(BigDecimal.ZERO)
                .comment("Test")
                .type(false)
                .bankBook(null)
                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                .build()));
        assertEquals(1L, cashBoxService.getCashBoxResponseById(1L).id());
        verify(cashBoxRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllCashBoxResponse() {
        Page<CashBox> cashBoxes = new PageImpl<>(
                List.of(
                        CashBox.builder()
                                .id(1L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build(),
                        CashBox.builder()
                                .id(2L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build(),
                        CashBox.builder()
                                .id(3L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build(),
                        CashBox.builder()
                                .id(4L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build()
                )
        );
        when(cashBoxRepository.findAll((Specification<CashBox>) any(), any(PageRequest.class))).thenReturn(cashBoxes);
        Page<CashBoxResponse> cashBoxResponses = cashBoxService.getAllCashBoxResponse(CashBoxRequest.builder().pageIndex(1).build());
        verify(cashBoxRepository, times(1)).findAll((Specification<CashBox>) any(), any(PageRequest.class));
        assertEquals(4, cashBoxResponses.getTotalElements());
        assertEquals(CashBoxResponse.class, cashBoxResponses.iterator().next().getClass());
    }

    @Test
    void getAllCashBoxIsActive() {
        List<CashBox> cashBoxes = List.of(
                CashBox.builder()
                        .id(1L)
                        .number("123123123")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build(),
                CashBox.builder()
                        .id(2L)
                        .number("123123123")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build(),
                CashBox.builder()
                        .id(3L)
                        .number("123123123")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build(),
                CashBox.builder()
                        .id(4L)
                        .number("123123123")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build()
        );
        when(cashBoxRepository.findAll((Specification<CashBox>) any())).thenReturn(cashBoxes);
        List<CashBox> cashBoxResponses = cashBoxService.getAllCashBoxIsActive();
        verify(cashBoxRepository, times(1)).findAll((Specification<CashBox>) any());
        assertEquals(4, cashBoxResponses.size());
        assertEquals(CashBox.class, cashBoxResponses.iterator().next().getClass());
    }

    @Test
    void getDraft() {
        assertEquals(2, cashBoxService.getDraft().size());
    }

    @Test
    void updateCashBoxByRequestIncome() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(true)
                .price(BigDecimal.TEN)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(true)
                .isActive(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBook(BankBook.builder().id(1L).build())
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(1)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.TEN.subtract(BigDecimal.ONE))
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void updateCashBoxByRequestIncomeDraftFalse() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(true)
                .price(BigDecimal.TEN)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(false)
                .isActive(false)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBook(BankBook.builder().id(1L).build())
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(1)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.TEN)
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void updateCashBoxByRequestIncomeDraftTrue() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(false)
                .type(true)
                .price(BigDecimal.TEN)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(true)
                .isActive(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBook(BankBook.builder().id(1L).build())
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(1)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ZERO.subtract(BigDecimal.TEN))
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void updateCashBoxByRequestIncomeAnyBankBook() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(true)
                .price(BigDecimal.TEN)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(true)
                .isActive(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBook(BankBook.builder().id(2L).build())
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(anyLong())).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(2)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(0)).updateStatistic(any());
    }

    @Test
    void updateCashBoxByRequestIncomeDraftFalseAnyBankBook() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(true)
                .price(BigDecimal.TEN)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(false)
                .isActive(false)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBook(BankBook.builder().id(2L).build())
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(1)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.TEN)
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void updateCashBoxByRequestIncomeDraftTrueAnyBankBook() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(false)
                .type(true)
                .price(BigDecimal.TEN)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(true)
                .isActive(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBook(BankBook.builder().id(2L).build())
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(1)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ZERO.subtract(BigDecimal.ONE))
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void updateCashBoxByRequestIncomeAnyBankBookEqualPrice() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(true)
                .isActive(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBook(BankBook.builder().id(2L).build())
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(2)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(0)).updateStatistic(any());
    }

    @Test
    void updateCashBoxByRequestExpenseDraftFalseAnyBankBook() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(false)
                .price(BigDecimal.TEN)
                .comment("")
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(false)
                .isActive(false)
                .type(false)
                .price(BigDecimal.ONE)
                .comment("")
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(0)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ZERO.subtract(BigDecimal.TEN))
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void updateCashBoxByRequestExpenseDraftTrueAnyBankBook() {
        CashBoxUpdateRequest request = CashBoxUpdateRequest.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(false)
                .type(true)
                .price(BigDecimal.TEN)
                .comment("")
                .articleId(1L)
                .staffId(1L)
                .build();

        CashBox cashBox = CashBox.builder()
                .id(1L)
                .number("0123456789")
                .createAt(LocalDateTime.now())
                .draft(true)
                .isActive(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .articles(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build())
                .staff(Staff.builder().id(1L).build())
                .build();

        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.updateCashBoxByRequest(request);
        verify(bankBookService, times(0)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.TEN)
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void saveCashBoxByRequestIncome() {
        CashBoxSaveRequest request = CashBoxSaveRequest.builder()
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(true)
                .price(BigDecimal.ONE)
                .comment("")
                .bankBookId(1L)
                .articleId(1L)
                .staffId(1L)
                .build();

        when(bankBookService.getBankBookById(anyLong())).thenReturn(BankBook.builder().id(1L).build());
        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.INCOME).name("Test").build());

        cashBoxService.saveCashBoxByRequest(request);
        verify(bankBookService, times(1)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ONE)
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void saveCashBoxByRequestExpense() {
        CashBoxSaveRequest request = CashBoxSaveRequest.builder()
                .number("0123456789")
                .createAt(LocalDate.now())
                .draft(true)
                .type(false)
                .price(BigDecimal.ONE)
                .comment("")
                .articleId(1L)
                .staffId(1L)
                .build();

        when(staffService.getStaffById(anyLong())).thenReturn(Staff.builder().id(1L).build());
        when(articleService.getArticleById(1L)).thenReturn(Article.builder().id(1L).type(ArticleType.EXPENSE).name("Test").build());

        cashBoxService.saveCashBoxByRequest(request);
        verify(bankBookService, times(0)).calculateBankBook(anyLong(), any(), anyBoolean(), any());
        verify(statisticService, times(1)).updateStatistic(
                Statistic.builder()
                        .cashBoxState(BigDecimal.ZERO.subtract(BigDecimal.ONE))
                        .bankBookBalance(BigDecimal.ZERO)
                        .bankBookExpense(BigDecimal.ZERO)
                        .build()
        );
    }

    @Test
    void saveCashBox() {
        cashBoxService.saveCashBox(CashBox.builder().build());
        verify(cashBoxRepository, times(1)).save(any());
    }

    @Test
    void deleteCashBoxById() {
        CashBox cashBox = CashBox.builder()
                .id(4L)
                .number("123123123")
                .createAt(LocalDateTime.now())
                .draft(true)
                .isActive(false)
                .price(BigDecimal.ZERO)
                .comment("Test")
                .type(false)
                .bankBook(null)
                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                .build();
        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        cashBoxService.deleteCashBoxById(1L);
        verify(cashBoxRepository, times(1)).findById(anyLong());
        verify(cashBoxRepository, times(1)).delete((CashBox) any());
    }

    @Test
    void deleteCashBoxByIdException() {
        CashBox cashBox = CashBox.builder()
                .id(4L)
                .number("123123123")
                .createAt(LocalDateTime.now())
                .isActive(true)
                .draft(true)
                .price(BigDecimal.ZERO)
                .comment("Test")
                .type(true)
                .bankBook(null)
                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                .build();
        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        assertThrows(IllegalArgumentException.class, () -> cashBoxService.deleteCashBoxById(1L));
        verify(cashBoxRepository, times(1)).findById(anyLong());
        verify(cashBoxRepository, times(0)).delete((CashBox) any());
    }

    @Test
    void getNewCashBoxResponse() {
        List<CashBox> cashBoxes = List.of(
                CashBox.builder()
                        .id(1L)
                        .number("1231231230")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build(),
                CashBox.builder()
                        .id(2L)
                        .number("1231231231")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build(),
                CashBox.builder()
                        .id(3L)
                        .number("1231231232")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build(),
                CashBox.builder()
                        .id(4L)
                        .number("1231231233")
                        .createAt(LocalDateTime.now())
                        .draft(true)
                        .price(BigDecimal.ZERO)
                        .comment("Test")
                        .type(false)
                        .bankBook(null)
                        .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                        .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                        .build()
        );
        when(cashBoxRepository.findAll((Specification<CashBox>) any())).thenReturn(cashBoxes);
        assertEquals("1231231231", cashBoxService.getNewCashBoxResponse(true).number());
    }

    @Test
    void getNewCashBoxResponseEmtyList() {
        List<CashBox> cashBoxes = List.of();
        when(cashBoxRepository.findAll((Specification<CashBox>) any())).thenReturn(cashBoxes);
        assertEquals("0000000001", cashBoxService.getNewCashBoxResponse(true).number());
    }

    @Test
    void getExcel() {
        CashBox cashBox = CashBox.builder()
                .id(4L)
                .number("123123123")
                .createAt(LocalDateTime.now())
                .draft(true)
                .price(BigDecimal.ZERO)
                .comment("Test")
                .type(false)
                .bankBook(null)
                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                .build();
        when(cashBoxRepository.findById(anyLong())).thenReturn(Optional.of(cashBox));
        InputStreamResource excel = cashBoxService.getExcel(1L);
        assertNotNull(excel);
    }

    @Test
    public void testGetExcel() {
        Page<CashBox> cashBoxes = new PageImpl<>(
                List.of(
                        CashBox.builder()
                                .id(1L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build(),
                        CashBox.builder()
                                .id(2L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build(),
                        CashBox.builder()
                                .id(3L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build(),
                        CashBox.builder()
                                .id(4L)
                                .number("123123123")
                                .createAt(LocalDateTime.now())
                                .draft(true)
                                .price(BigDecimal.ZERO)
                                .comment("Test")
                                .type(false)
                                .bankBook(null)
                                .staff(Staff.builder().id(1L).firstname("Test").lastname("Test").role(Role.builder().jobTitle(JobTitle.DIRECTOR).build()).build())
                                .articles(Article.builder().id(1L).name("Test").type(ArticleType.INCOME).build())
                                .build()
                )
        );
        when(cashBoxRepository.findAll((Specification<CashBox>) any(), any(PageRequest.class))).thenReturn(cashBoxes);
        CashBoxRequest request = CashBoxRequest.builder().pageIndex(1).build();
        InputStreamResource excel = cashBoxService.getExcel(request);
        assertNotNull(excel);
    }

    @Test
    public void testGetExcelWithIOException() throws Exception {
        Workbook workbook = mock(Workbook.class);

        ByteArrayOutputStream out = mock(ByteArrayOutputStream.class);
        out.close();
        doThrow(new IOException("Test IO Exception")).when(workbook);
        CashBoxRequest request = CashBoxRequest.builder().pageIndex(1).build();
        assertThrows(RuntimeException.class, () -> cashBoxService.getExcel(request));
    }

    @Test
    void statisticSumByType() {
        when(cashBoxRepository.findAllByType(anyBoolean())).thenReturn(List.of(CashBox.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).price(BigDecimal.ONE).build(), CashBox.builder().createAt(LocalDateTime.of(2023, 1, 1, 14, 10)).price(BigDecimal.ONE).build()));
        List<BigDecimal> month = cashBoxService.statisticSumByType(true);
        verify(cashBoxRepository, times(1)).findAllByType(true);
        assertEquals("2", month.get(0).toString());
        assertNotEquals("2", month.get(1).toString());
    }

    @Test
    void statisticCashStatement() {
        when(cashBoxRepository.findAllByType(true)).thenReturn(List.of(CashBox.builder().price(BigDecimal.ONE).build(), CashBox.builder().price(BigDecimal.ONE).build()));
        when(cashBoxRepository.findAllByType(false)).thenReturn(List.of(CashBox.builder().price(BigDecimal.ONE).build(), CashBox.builder().price(BigDecimal.ONE).build()));
        assertEquals("0", cashBoxService.statisticCashStatement().toString());
        verify(cashBoxRepository, times(1)).findAllByType(true);
        verify(cashBoxRepository, times(1)).findAllByType(false);
    }

    @Test
    void statisticAccountBalance() {
        when(billService.sumOfAllBills()).thenReturn(BigDecimal.ONE);
        when(cashBoxRepository.findAllByType(true)).thenReturn(List.of(CashBox.builder().price(BigDecimal.ONE).build(), CashBox.builder().price(BigDecimal.ONE).build()));
        assertEquals("1", cashBoxService.statisticAccountBalance().toString());
        verify(billService, times(1)).sumOfAllBills();
        verify(cashBoxRepository, times(1)).findAllByType(true);
    }
}