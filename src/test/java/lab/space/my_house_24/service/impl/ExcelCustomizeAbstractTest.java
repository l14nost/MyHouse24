package lab.space.my_house_24.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExcelCustomizeAbstractTest {

    private ExcelCustomizeAbstract excelCustomizer;

    private Workbook workbook;

    @BeforeEach
    void setUp() {
        excelCustomizer = new ExcelCustomizeAbstract() {
        };
        workbook = new XSSFWorkbook();
    }

    @Test
    void testGetHeaderStyle() {
        CellStyle headerStyle = excelCustomizer.getHeaderStyle(workbook);
        assertNotNull(headerStyle);
        assertEquals(HorizontalAlignment.CENTER, headerStyle.getAlignment());
        assertEquals(VerticalAlignment.CENTER, headerStyle.getVerticalAlignment());
        assertEquals(BorderStyle.THIN, headerStyle.getBorderBottom());
        assertEquals(BorderStyle.THIN, headerStyle.getBorderTop());
        assertEquals(BorderStyle.THIN, headerStyle.getBorderRight());
        assertEquals(BorderStyle.THIN, headerStyle.getBorderLeft());
    }

    @Test
    void testGetCellStyle() {
        CellStyle cellStyle = excelCustomizer.getCellStyle(workbook);
        assertNotNull(cellStyle);
        assertEquals(HorizontalAlignment.CENTER, cellStyle.getAlignment());
        assertEquals(VerticalAlignment.CENTER, cellStyle.getVerticalAlignment());
        assertEquals(BorderStyle.THIN, cellStyle.getBorderBottom());
        assertEquals(BorderStyle.THIN, cellStyle.getBorderTop());
        assertEquals(BorderStyle.THIN, cellStyle.getBorderRight());
        assertEquals(BorderStyle.THIN, cellStyle.getBorderLeft());
    }

    @Test
    void testAutoSizeAllColumns() {
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < 5; i++) {
            row.createCell(i).setCellValue("Test");
        }

        excelCustomizer.autoSizeAllColumns(sheet, 5);

        for (int i = 0; i < 5; i++) {
            assertEquals(1229, sheet.getColumnWidth(i));
        }
    }

    @Test
    void testSetHeaderRow() {
        Sheet sheet = workbook.createSheet();
        String[] header = {"Header1", "Header2", "Header3"};

        excelCustomizer.setHeaderRow(workbook, sheet, header, 0);

        Row headerRow = sheet.getRow(0);
        assertNotNull(headerRow);
        for (int i = 0; i < header.length; i++) {
            Cell cell = headerRow.getCell(i);
            assertNotNull(cell);
            assertEquals(header[i], cell.getStringCellValue());
            CellStyle cellStyle = cell.getCellStyle();
            assertNotNull(cellStyle);
            assertEquals(HorizontalAlignment.CENTER, cellStyle.getAlignment());
            assertEquals(VerticalAlignment.CENTER, cellStyle.getVerticalAlignment());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderBottom());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderTop());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderRight());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderLeft());
        }
    }

    @Test
    void testCreateInitialRows() {
        Sheet sheet = workbook.createSheet();

        Row row = excelCustomizer.createInitialRows(workbook, sheet, 3, 0);

        assertNotNull(row);
        for (int i = 0; i < 3; i++) {
            Cell cell = row.getCell(i);
            assertNotNull(cell);
            CellStyle cellStyle = cell.getCellStyle();
            assertNotNull(cellStyle);
            assertEquals(HorizontalAlignment.CENTER, cellStyle.getAlignment());
            assertEquals(VerticalAlignment.CENTER, cellStyle.getVerticalAlignment());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderBottom());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderTop());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderRight());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderLeft());
        }
    }

    @Test
    void testSetWorkbookAuthor() {
        String authorTitle = "TestAuthor";
        Workbook testWorkbook = new XSSFWorkbook();

        excelCustomizer.setWorkbookAuthor(testWorkbook, authorTitle);

        if (testWorkbook instanceof XSSFWorkbook) {
            String creator = ((XSSFWorkbook) testWorkbook).getProperties().getCoreProperties().getCreator();
            assertNotNull(creator);
            assertEquals(authorTitle, creator);
        }
    }

    @Test
    void testSetHeadersOfRows() {
        Sheet sheet = workbook.createSheet();
        String[] headers = {"Row1", "Row2", "Row3"};

        excelCustomizer.setHeadersOfRows(workbook, sheet, headers, 0);

        for (int i = 0; i < headers.length; i++) {
            Row row = sheet.getRow(i);
            assertNotNull(row);
            Cell cell = row.getCell(0);
            assertNotNull(cell);
            assertEquals(headers[i], cell.getStringCellValue());
            CellStyle cellStyle = cell.getCellStyle();
            assertNotNull(cellStyle);
            assertEquals(HorizontalAlignment.CENTER, cellStyle.getAlignment());
            assertEquals(VerticalAlignment.CENTER, cellStyle.getVerticalAlignment());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderBottom());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderTop());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderRight());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderLeft());
        }
    }

    @Test
    void testCreateInitialCellsForRows() {
        Sheet sheet = workbook.createSheet();
        int headersLength = 4;
        int rowIndex = 1;

        for (int i = rowIndex; i < rowIndex + headersLength; i++) {
            sheet.createRow(i);
        }

        excelCustomizer.createInitialCellsForRows(workbook, sheet, headersLength, rowIndex);

        for (int i = rowIndex; i < rowIndex + headersLength; i++) {
            Row row = sheet.getRow(i);
            assertNotNull(row);
            Cell cell = row.getCell(1);
            assertNotNull(cell);
            CellStyle cellStyle = cell.getCellStyle();
            assertNotNull(cellStyle);
            assertEquals(HorizontalAlignment.CENTER, cellStyle.getAlignment());
            assertEquals(VerticalAlignment.CENTER, cellStyle.getVerticalAlignment());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderBottom());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderTop());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderRight());
            assertEquals(BorderStyle.THIN, cellStyle.getBorderLeft());
        }
    }
}