package com.app.task.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ExcelExporter {
    private static final String SHEET = "Data";

    public<T> Workbook createWorkbook(List<T> entities, String[] headers, String[] fieldColumns) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET);
        createHeaderRow(sheet, headers);
        fillDataRows(sheet, entities, fieldColumns);

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        return workbook;
    }
    public <T> void exportToExcel(List<T> entities, Map<String, String> columnHeaders, String fileName, HttpServletResponse response) {
        try{
            String[] headers = columnHeaders.values().toArray(new String[0]);
            String[] fieldColumns = columnHeaders.keySet().toArray(new String[0]);
            Workbook workbook = createWorkbook(entities, headers, fieldColumns);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s", fileName));

            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (Exception ex) {
            throw new RuntimeException("Fail to export data to Excel file: " + ex.getMessage());
        }
    }

    public <T> ByteArrayInputStream exportToExcel(List<T> entities, Map<String, String> columnHeaders) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();)  {
            String[] fieldColumns = columnHeaders.keySet().toArray(new String[0]);
            String[] headers = columnHeaders.values().toArray(new String[0]);
            Workbook workbook = createWorkbook(entities, headers, fieldColumns);
            workbook.write(out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            workbook.close();
            return in;
        } catch (Exception ex) {
            throw new RuntimeException("Fail to export data to Excel file: " + ex.getMessage());
        }
    }

    private void createHeaderRow(Sheet sheet, String[] columnNames) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
        XSSFFont headerFont = (XSSFFont) sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerCellStyle.setFont(headerFont);

        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private <T> void fillDataRows(Sheet sheet, List<T> entities, String[] columns) {
        CellStyle dataCellStyle = sheet.getWorkbook().createCellStyle();

        for (int rowIndex = 0; rowIndex < entities.size(); rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1);
            T entity = entities.get(rowIndex);
            for (int columnIndex = 0; columnIndex < columns.length; columnIndex++) {
                Cell cell = row.createCell(columnIndex);
                String column = columns[columnIndex];

                try {
                    Field field = entity.getClass().getDeclaredField(column);
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (value != null) {
                        cell.setCellValue(String.valueOf(value));
                    }
                } catch (Exception ex) {
                    log.error("[Export excel exception] An exception occurred. " + ex.getMessage());
                }

                cell.setCellStyle(dataCellStyle);
            }
        }
    }
}