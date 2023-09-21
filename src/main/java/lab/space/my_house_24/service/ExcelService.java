package lab.space.my_house_24.service;

import lab.space.my_house_24.model.bankBook.BankBookResponse;
import lab.space.my_house_24.model.bill.BillResponse;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface ExcelService {
    void getExcelForCashBoxTable(Workbook workbook, String[] header, List<CashBoxResponse> cashBoxResponses);

    void getExcelForCashBoxCard(Workbook workbook, String[] title, CashBoxResponse cashBoxResponse);

    void getExcelForBankBookTable(Workbook workbook, String[] header, List<BankBookResponse> bankBookResponses);

    void getExcelForBillTable(Workbook workbook, String[] header, List<BillResponse> billResponses);

    void getExcelForBillCard(Workbook workbook, String[] title, String[] extraHeader, BillResponse billResponse);
}
