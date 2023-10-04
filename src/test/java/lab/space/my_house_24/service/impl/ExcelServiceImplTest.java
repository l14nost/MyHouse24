package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.model.apartment.ApartmentResponseForBankBook;
import lab.space.my_house_24.model.article.ArticleResponse;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bill.BillResponse;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import lab.space.my_house_24.model.house.HouseResponseForTable;
import lab.space.my_house_24.model.rate.RateResponse;
import lab.space.my_house_24.model.section.SectionResponseForTable;
import lab.space.my_house_24.model.service.ServiceResponse;
import lab.space.my_house_24.model.service_bill.ServiceBillResponse;
import lab.space.my_house_24.model.staff.StaffResponse;
import lab.space.my_house_24.model.unit.UnitResponse;
import lab.space.my_house_24.model.user.UserResponseForMastersApplication;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExcelServiceImplTest {

    @Mock
    private MessageSource message;

    @InjectMocks
    private ExcelServiceImpl excelService;

    @Test
    void testGetExcelForCashBoxTable() {
        Workbook workbook = new XSSFWorkbook();
        String[] header = {"Number", "Date", "Draft", "Article", "Owner", "BankBook", "Type", "Price"};

        List<CashBoxResponse> cashBoxResponses = new ArrayList<>();
        cashBoxResponses.add(createMockCashBoxResponse("123", LocalDate.now(), true, "Expense", "John Doe", "ABC123", false, 100.0));
        cashBoxResponses.add(createMockCashBoxResponse("456", LocalDate.now(), false, "Income", "Jane Smith", "XYZ789", true, 200.0));

        excelService.getExcelForCashBoxTable(workbook, header, cashBoxResponses);

        Sheet sheet = workbook.getSheet("Cash-Boxes Data Table");
        assertNotNull(sheet);

        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow);
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.getCell(i);
            assertNotNull(cell);
            assertEquals(header[i], cell.getStringCellValue());
        }

        for (int i = 0; i < cashBoxResponses.size(); i++) {
            Row row = sheet.getRow(i + 1);
            assertNotNull(row);
            assertEquals(cashBoxResponses.get(i).number(), row.getCell(0).getStringCellValue());
            assertEquals(cashBoxResponses.get(i).createAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), row.getCell(1).getStringCellValue());
        }

    }

    @Test
    void testGetExcelForCashBoxCard() {
        Workbook workbook = new XSSFWorkbook();
        String[] title = {"Title1", "Title2", "Title3", "Title4", "Title5", "Title6", "Title7", "Title8", "Title9"};

        CashBoxResponse cashBoxResponse = createMockCashBoxResponse("456", LocalDate.now(), false, "Income", "Jane Smith", "XYZ789", true, 200.0);


        excelService.getExcelForCashBoxCard(workbook, title, cashBoxResponse);

        Sheet sheet = workbook.getSheet("Cash-Box Data Table");
        assertNotNull(sheet);

        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow);
        for (int i = 0; i < title.length; i++) {
            Cell cell = headerRow.getCell(0);
            assertNotNull(cell);
        }

        assertEquals(cashBoxResponse.number(), sheet.getRow(0).getCell(1).getStringCellValue());
        assertEquals(cashBoxResponse.createAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), sheet.getRow(1).getCell(1).getStringCellValue());
        assertEquals(cashBoxResponse.article().name(), sheet.getRow(2).getCell(1).getStringCellValue());
        assertEquals(cashBoxResponse.bankBook().apartment().owner().fullName(), sheet.getRow(3).getCell(1).getStringCellValue());
        assertEquals(cashBoxResponse.bankBook().number(), sheet.getRow(4).getCell(1).getStringCellValue());
        assertEquals(cashBoxResponse.draft().value(), sheet.getRow(5).getCell(1).getStringCellValue());
        assertEquals("Test", sheet.getRow(6).getCell(1).getStringCellValue());
        assertEquals(cashBoxResponse.price().toString(), sheet.getRow(7).getCell(1).getStringCellValue());
        assertEquals(isNull(cashBoxResponse.comment()) ? "-" : cashBoxResponse.comment(), sheet.getRow(8).getCell(1).getStringCellValue());
    }

    @Test
    void testGetExcelForBankBookTable() {
        Workbook workbook = new XSSFWorkbook();
        String[] header = {"Number", "Date", "Draft", "Article", "Owner", "BankBook", "Type"};

        List<BankBookResponse> bankBookResponses = new ArrayList<>();
        bankBookResponses.add(createMockBankBookResponse("123"));
        bankBookResponses.add(createMockBankBookResponse("456"));

        excelService.getExcelForBankBookTable(workbook, header, bankBookResponses);

        Sheet sheet = workbook.getSheet("Bank Books Data Table");
        assertNotNull(sheet);

        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow);
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.getCell(i);
            assertNotNull(cell);
            assertEquals(header[i], cell.getStringCellValue());
        }

        for (int i = 0; i < bankBookResponses.size(); i++) {
            Row row = sheet.getRow(i + 1);
            assertNotNull(row);
            assertEquals(bankBookResponses.get(i).number(), row.getCell(0).getStringCellValue());
            assertEquals(bankBookResponses.get(i).status().value(), row.getCell(1).getStringCellValue());
        }

    }

    @Test
    void testGetExcelForBillTable() {
        Workbook workbook = new XSSFWorkbook();
        String[] header = {"Title1", "Title2", "Title3", "Title4", "Title5", "Title6", "Title7", "Title8", "Title9"};

        List<BillResponse> billResponses = new ArrayList<>();
        billResponses.add(createMockBillResponse("123"));
        billResponses.add(createMockBillResponse("456"));

        excelService.getExcelForBillTable(workbook, header, billResponses);

        Sheet sheet = workbook.getSheet("Bills Data Table");
        assertNotNull(sheet);

        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow);
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.getCell(i);
            assertNotNull(cell);
            assertEquals(header[i], cell.getStringCellValue());
        }

        for (int i = 0; i < billResponses.size(); i++) {
            Row row = sheet.getRow(i + 1);
            assertNotNull(row);
            assertEquals(billResponses.get(i).number(), row.getCell(0).getStringCellValue());
            assertEquals(billResponses.get(i).status().value(), row.getCell(1).getStringCellValue());
        }

    }

    @Test
    void testGetExcelForBillCard() {
        Workbook workbook = new XSSFWorkbook();
        String[] title = {"Title1", "Title2", "Title3", "Title4", "Title5", "Title6", "Title7", "Title8", "Title9", "Title10", "Title11", "Title12", "Title13"};
        String[] extraHeader = {"Title1", "Title2", "Title3", "Title4", "Title5", "Title6"};

        BillResponse billResponse = createMockBillResponse("456");


        excelService.getExcelForBillCard(workbook, title, extraHeader, billResponse);

        Sheet sheet = workbook.getSheet("Bill Data Table");
        assertNotNull(sheet);

        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow);
        for (int i = 0; i < title.length; i++) {
            Cell cell = headerRow.getCell(0);
            assertNotNull(cell);
        }

        assertEquals(billResponse.number(), sheet.getRow(0).getCell(1).getStringCellValue());
        assertEquals(billResponse.createAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), sheet.getRow(1).getCell(1).getStringCellValue());

        assertEquals(billResponse.services().get(0).service().name(), sheet.getRow(15).getCell(1).getStringCellValue());
        assertEquals(billResponse.totalPrice().toString(), sheet.getRow(15).getCell(5).getStringCellValue());
    }

    private CashBoxResponse createMockCashBoxResponse(String number, LocalDate date, boolean draft, String article, String owner, String bankBook, boolean type, double price) {
        return CashBoxResponse.builder()
                .number(number)
                .createAt(date)
                .draft(EnumResponse.builder().value(String.valueOf(draft)).build())
                .article(ArticleResponse.builder().name(article).build())
                .bankBook(BankBookResponse.builder().number(bankBook).apartment(ApartmentResponseForBankBook.builder().owner(UserResponseForMastersApplication.builder().fullName(owner).build()).build()).build())
                .type(type)
                .staff(StaffResponse.builder().fullName("Test").build())
                .price(BigDecimal.valueOf(price))
                .build();
    }

    private BankBookResponse createMockBankBookResponse(String number) {
        return BankBookResponse.builder()
                .number(number)
                .status(EnumResponse.builder().value("Test").name("test").build())
                .totalPrice(BigDecimal.ZERO)
                .apartment(ApartmentResponseForBankBook.builder()
                        .number("apartmentTest")
                        .house(HouseResponseForTable.builder().name("houseTest").build())
                        .section(SectionResponseForTable.builder().name("sectionTest").build())
                        .owner(UserResponseForMastersApplication.builder().fullName("ownerTest").build())
                        .build())
                .build();
    }

    private BillResponse createMockBillResponse(String number) {
        return BillResponse.builder()
                .id(1L)
                .number(number)
                .status(EnumResponse.builder().name("test").value("Test").build())
                .totalPrice(BigDecimal.ZERO)
                .payed(BigDecimal.ZERO)
                .draft(true)
                .bankBook(createMockBankBookResponse(number))
                .rate(RateResponse.builder().build())
                .services(List.of(ServiceBillResponse.builder()
                        .service(ServiceResponse.builder().name("serviceTest").unit(UnitResponse.builder().name("unitTest").build()).build())
                        .count(10.0)
                        .price(BigDecimal.ZERO)
                        .totalPrice(BigDecimal.ZERO)
                        .build()))
                .createAt(LocalDate.now())
                .periodTo(LocalDate.now())
                .month("monthTest")
                .build();
    }
}