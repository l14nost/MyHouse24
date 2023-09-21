package lab.space.my_house_24.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelCustomizeAbstract {

    public CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = (XSSFFont) workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeight(12);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        return headerStyle;
    }

    public CellStyle getCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }

    public void autoSizeAllColumns(Sheet sheet, int colQty) {
        for (int col = 0; col < colQty; col++) {
            sheet.autoSizeColumn(col);
        }
    }

    public void setHeaderRow(Workbook workbook, Sheet sheet, String[] header, int INITIAL_INDEX_OF_ROW) {
        Row headerRow = sheet.createRow(INITIAL_INDEX_OF_ROW);
        for (int col = 0; col < header.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(header[col]);
            cell.setCellStyle(getHeaderStyle(workbook));
        }
    }

    public Row createInitialRows(Workbook workbook, Sheet sheet, int headersLength, int rowIndex) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < headersLength; i++) {
            row.createCell(i).setCellStyle(getCellStyle(workbook));
        }
        return row;
    }

    public void setWorkbookAuthor(Workbook workbook, String title) {
        if (workbook instanceof XSSFWorkbook) {
            ((XSSFWorkbook) workbook).getProperties().getCoreProperties().setCreator(title);
        }
    }

    public void setHeadersOfRows(Workbook workbook, Sheet sheet, String[] header, int INITIAL_INDEX_OF_ROW) {
        for (int row = 0; row < header.length; row++) {
            Row headerRow = sheet.createRow(INITIAL_INDEX_OF_ROW + row);
            Cell cell = headerRow.createCell(0);
            cell.setCellValue(header[row]);
            cell.setCellStyle(getHeaderStyle(workbook));
        }
    }

    public void createInitialCellsForRows(Workbook workbook, Sheet sheet, int headersLength, int rowIndex) {
        for (int row = 0; row < headersLength; row++) {
            sheet.getRow(row + rowIndex).createCell(1).setCellStyle(getCellStyle(workbook));
        }
    }

}
