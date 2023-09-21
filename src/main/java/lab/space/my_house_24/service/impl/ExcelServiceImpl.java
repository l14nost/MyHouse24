package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bill.BillResponse;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.service_bill.ServiceBillResponse;
import lab.space.my_house_24.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelServiceImpl extends ExcelCustomizeAbstract implements ExcelService {
    private final MessageSource message;

    @Override
    public void getExcelForCashBoxTable(Workbook workbook, String[] header, List<CashBoxResponse> cashBoxResponses) {
        int INITIAL_INDEX_OF_ROW = 0;
        Sheet sheet = workbook.createSheet("Cash-Boxes Data Table");
        setWorkbookAuthor(workbook, message.getMessage("layout.title", null, LocaleContextHolder.getLocale()));
        setHeaderRow(workbook, sheet, header, INITIAL_INDEX_OF_ROW);

        for (CashBoxResponse cashBoxResponse : cashBoxResponses) {
            final int rowIndex = INITIAL_INDEX_OF_ROW++;
            Row row = createInitialRows(workbook, sheet, header.length, rowIndex + 1);
            row.getCell(0).setCellValue(cashBoxResponse.number());
            row.getCell(1).setCellValue(cashBoxResponse.createAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            row.getCell(2).setCellValue(cashBoxResponse.draft().value());
            row.getCell(3).setCellValue(cashBoxResponse.article().name());
            row.getCell(4).setCellValue(nonNull(cashBoxResponse.bankBook()) ? cashBoxResponse.bankBook().apartment().owner().fullName() : "-");
            row.getCell(5).setCellValue(nonNull(cashBoxResponse.bankBook()) ? cashBoxResponse.bankBook().number() : "-");
            row.getCell(6).setCellValue(cashBoxResponse.type() ? ArticleType.INCOME.getArticleType(LocaleContextHolder.getLocale()) : ArticleType.EXPENSE.getArticleType(LocaleContextHolder.getLocale()));
            row.getCell(7).setCellValue(cashBoxResponse.price().toString());
        }
        autoSizeAllColumns(sheet, header.length);
    }

    @Override
    public void getExcelForCashBoxCard(Workbook workbook, String[] title, CashBoxResponse cashBoxResponse) {
        int INITIAL_INDEX_OF_ROW = 0;
        Sheet sheet = workbook.createSheet("Cash-Box Data Table");
        setWorkbookAuthor(workbook, message.getMessage("layout.title", null, LocaleContextHolder.getLocale()));
        setHeadersOfRows(workbook, sheet, title, INITIAL_INDEX_OF_ROW);
        createInitialCellsForRows(workbook, sheet, title.length, INITIAL_INDEX_OF_ROW);

        sheet.getRow(0).getCell(1).setCellValue(cashBoxResponse.number());
        sheet.getRow(1).getCell(1).setCellValue(cashBoxResponse.createAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        sheet.getRow(2).getCell(1).setCellValue(cashBoxResponse.article().name());
        sheet.getRow(3).getCell(1).setCellValue(nonNull(cashBoxResponse.bankBook()) ? cashBoxResponse.bankBook().apartment().owner().fullName() : "-");
        sheet.getRow(4).getCell(1).setCellValue(nonNull(cashBoxResponse.bankBook()) ? cashBoxResponse.bankBook().number() : "-");
        sheet.getRow(5).getCell(1).setCellValue(cashBoxResponse.draft().value());
        sheet.getRow(6).getCell(1).setCellValue(cashBoxResponse.staff().fullName());
        sheet.getRow(7).getCell(1).setCellValue(cashBoxResponse.price().toString());
        sheet.getRow(8).getCell(1).setCellValue(nonNull(cashBoxResponse.comment()) ? cashBoxResponse.comment() : "-");
        autoSizeAllColumns(sheet, title.length);
    }

    @Override
    public void getExcelForBankBookTable(Workbook workbook, String[] header, List<BankBookResponse> bankBookResponses) {
        int INITIAL_INDEX_OF_ROW = 0;
        Sheet sheet = workbook.createSheet("Bank Books Data Table");
        setWorkbookAuthor(workbook, message.getMessage("layout.title", null, LocaleContextHolder.getLocale()));
        setHeaderRow(workbook, sheet, header, INITIAL_INDEX_OF_ROW);

        for (BankBookResponse bankBookResponse : bankBookResponses) {
            final int rowIndex = INITIAL_INDEX_OF_ROW++;
            Row row = createInitialRows(workbook, sheet, header.length, rowIndex + 1);
            row.getCell(0).setCellValue(bankBookResponse.number());
            row.getCell(1).setCellValue(bankBookResponse.status().value());
            row.getCell(2).setCellValue(nonNull(bankBookResponse.apartment()) ? bankBookResponse.apartment().number() : "-");
            row.getCell(3).setCellValue(nonNull(bankBookResponse.apartment()) ? bankBookResponse.apartment().house().name() : "-");
            row.getCell(4).setCellValue(nonNull(bankBookResponse.apartment()) ? bankBookResponse.apartment().section().name() : "-");
            row.getCell(5).setCellValue(nonNull(bankBookResponse.apartment()) ? bankBookResponse.apartment().owner().fullName() : "-");
            row.getCell(6).setCellValue(bankBookResponse.totalPrice().toString());
        }
        autoSizeAllColumns(sheet, header.length);
    }

    @Override
    public void getExcelForBillTable(Workbook workbook, String[] header, List<BillResponse> billResponses) {
        int INITIAL_INDEX_OF_ROW = 0;
        Sheet sheet = workbook.createSheet("Bank Books Data Table");
        setWorkbookAuthor(workbook, message.getMessage("layout.title", null, LocaleContextHolder.getLocale()));
        setHeaderRow(workbook, sheet, header, INITIAL_INDEX_OF_ROW);

        for (BillResponse billResponse : billResponses) {
            final int rowIndex = INITIAL_INDEX_OF_ROW++;
            Row row = createInitialRows(workbook, sheet, header.length, rowIndex + 1);
            row.getCell(0).setCellValue(billResponse.number());
            row.getCell(1).setCellValue(billResponse.status().value());
            row.getCell(2).setCellValue(billResponse.createAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            row.getCell(3).setCellValue(billResponse.month());
            row.getCell(4).setCellValue(billResponse.bankBook().apartment().number() + ", " + billResponse.bankBook().apartment().house().name());
            row.getCell(5).setCellValue(billResponse.bankBook().apartment().owner().fullName());
            row.getCell(6).setCellValue(billResponse.draft() ? message.getMessage("bills.draft", null, LocaleContextHolder.getLocale()) : message.getMessage("bills.no_draft", null, LocaleContextHolder.getLocale()));
            row.getCell(7).setCellValue(billResponse.payed().toString());
            row.getCell(8).setCellValue(billResponse.totalPrice().toString());
        }
        autoSizeAllColumns(sheet, header.length);
    }

    @Override
    public void getExcelForBillCard(Workbook workbook, String[] title, String[] extraHeader, BillResponse billResponse) {
        int INITIAL_INDEX_OF_ROW = 0;
        Sheet sheet = workbook.createSheet("Bill Data Table");
        setWorkbookAuthor(workbook, message.getMessage("layout.title", null, LocaleContextHolder.getLocale()));
        setHeadersOfRows(workbook, sheet, title, INITIAL_INDEX_OF_ROW);
        createInitialCellsForRows(workbook, sheet, title.length, INITIAL_INDEX_OF_ROW);

        sheet.getRow(0).getCell(1).setCellValue(billResponse.number());
        sheet.getRow(1).getCell(1).setCellValue(billResponse.createAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        sheet.getRow(2).getCell(1).setCellValue(billResponse.draft() ? message.getMessage("bills.draft", null, LocaleContextHolder.getLocale()) : message.getMessage("bills.no_draft", null, LocaleContextHolder.getLocale()));
        sheet.getRow(3).getCell(1).setCellValue(billResponse.status().value());
        sheet.getRow(4).getCell(1).setCellValue(billResponse.payed().toString());
        sheet.getRow(5).getCell(1).setCellValue(billResponse.month());
        sheet.getRow(6).getCell(1).setCellValue(billResponse.bankBook().apartment().owner().fullName());
        sheet.getRow(7).getCell(1).setCellValue(billResponse.bankBook().number());
        sheet.getRow(8).getCell(1).setCellValue(billResponse.bankBook().apartment().owner().phone());
        sheet.getRow(9).getCell(1).setCellValue(billResponse.bankBook().apartment().house().name());
        sheet.getRow(10).getCell(1).setCellValue(billResponse.bankBook().apartment().number());
        sheet.getRow(11).getCell(1).setCellValue(billResponse.bankBook().apartment().section().name());
        sheet.getRow(12).getCell(1).setCellValue(nonNull(billResponse.rate()) ? billResponse.rate().name() : "-");

        INITIAL_INDEX_OF_ROW = 12 + 2;
        int indexService = 1;
        setHeaderRow(workbook, sheet, extraHeader, INITIAL_INDEX_OF_ROW);

        for (ServiceBillResponse serviceBillResponse : billResponse.services()) {
            final int rowIndex = INITIAL_INDEX_OF_ROW++;
            Row row = createInitialRows(workbook, sheet, extraHeader.length, rowIndex + 1);
            row.getCell(0).setCellValue(indexService++);
            row.getCell(1).setCellValue(serviceBillResponse.service().name());
            row.getCell(2).setCellValue(serviceBillResponse.count().toString());
            row.getCell(3).setCellValue(serviceBillResponse.service().unit().name());
            row.getCell(4).setCellValue(serviceBillResponse.price().toString());
            row.getCell(5).setCellValue(serviceBillResponse.totalPrice().toString());
        }

        Row row = createInitialRows(workbook, sheet, extraHeader.length, INITIAL_INDEX_OF_ROW + 1);
        row.getCell(5).setCellValue(message.getMessage("bills.card.total_price", null, LocaleContextHolder.getLocale()) + ": " + billResponse.totalPrice().toString());

        autoSizeAllColumns(sheet, title.length);
    }
}
