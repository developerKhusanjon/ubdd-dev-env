package uz.ciasev.ubdd_service.service.excel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.service.excel.cell.CellValueSetter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final List<CellValueSetter> cellValueSetters;

    @Override
    public <T> byte[] saveToExcel(Class<T> t, Stream<T> rows) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(200 * 1000);
        saveToExcel(baos, t, rows);
        return baos.toByteArray();
    }

    public <T> void saveToExcel(OutputStream outputStream, Class<T> t, Stream<T> rows) throws IOException {
        log.debug("Export started");
        validate(t);
        log.debug("Export validated");

        List<Pair<ExcelColumn, Method>> excelColumn = getRowMethods(t);
        ExcelSheetMetadata sheetMetadata = buildSheet(t, excelColumn);

        log.debug("Export metadata build");

        generateExcel(outputStream, sheetMetadata, rows);
    }

    private <T> void generateExcel(OutputStream outputStream, ExcelSheetMetadata metadata, Stream<T> dataRows) throws IOException {
        Workbook workbook = new SXSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet(metadata.getName());
        log.debug("Export sheet created");

        cellValueSetters.forEach(cellValueSetter -> cellValueSetter.createCellStyle(workbook, createHelper));
        log.debug("Export sheet style created");

        CountHelper counter = new CountHelper();

        // Write headers

        CellStyle style = getHeaderStyle(workbook);
        Row rowhead = sheet.createRow(counter.nextRow());
        rowhead.setHeight((short) 400);

        Cell noCell = rowhead.createCell(counter.nextColumn());
        noCell.setCellValue("№");
        noCell.setCellStyle(style);

        log.debug("Export start set header row ");

        metadata.getColumnsHeader()
                .forEach(columnsHeaderName -> {
                    Cell cell = rowhead.createCell(counter.nextColumn());
                    cell.setCellValue(columnsHeaderName);
                    cell.setCellStyle(style);
                });

        log.debug("Export end set header row");
        log.debug("Export start set data rows");

        // Write body
        dataRows
                .forEach(dataRow -> {

                    int rowCount = counter.nextRow();
                    Row row = sheet.createRow(rowCount);
                    // row number
                    row.createCell(counter.nextColumn()).setCellValue(rowCount);
                    metadata.getColumnsMethods()
                            .forEach(columnsMethod -> {
                                Cell cell = row.createCell(counter.nextColumn());
                                setCellValue(cell, columnsMethod, dataRow);
                            });
                });

        // Style

        log.debug("Export end set data rows");

        // Freeze first column and row
        sheet.createFreezePane(0, 2);
        sheet.createFreezePane(1, 1);

        log.debug("Export panel freezed");


        // вызов nextRow нужен для правельной работы автосейза колонок
        int dataRowCount = counter.nextRow();
//        metadata.getColumnsHeader()
//                .forEach(columnsHeaderName -> {
//                    sheet.autoSizeColumn(counter.nextColumn());
//                });
//
//        log.info(String.format("Export hash %s column autosized", dataRows.hashCode()));

//        ByteArrayOutputStream baos = new ByteArrayOutputStream(200 * dataRowCount);
        workbook.write(outputStream);

        log.debug("Export file writed");

//        return baos.toByteArray();
    }

    private void setCellValue(Cell cell, Method columnsMethod, Object dataRow) {
        Object value = null;
        try {
            value = columnsMethod.invoke(dataRow);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.warn("EXCEL SERVICE: column method {} invoke error ", columnsMethod.getName(), e);
        }

        CellValueSetter setter = findSetter(columnsMethod.getReturnType());
        setter.setValue(cell, value);
    }

    private <T> ExcelSheetMetadata buildSheet(Class<T> t, List<Pair<ExcelColumn, Method>> excelColumns) {
        List<String> columnsHeader = new ArrayList<>();
        List<Method> columnsMethods = new ArrayList<>();

        short i = 0;

        for (Pair<ExcelColumn, Method> column : excelColumns) {
            ExcelColumn columnMetadata = column.getFirst();
            Method columnMethod = column.getSecond();

            columnsHeader.add(columnMetadata.name());
            columnsMethods.add(columnMethod);
            i++;
        }

        String sheetName = t.getAnnotation(ExcelFile.class).name();

        return new ExcelSheetMetadata(sheetName, columnsHeader, columnsMethods);
    }

    private <T> List<Pair<ExcelColumn, Method>> getRowMethods(Class<T> t) {

        List<Pair<ExcelColumn, Method>> columns = new ArrayList<>();

        Arrays.stream(t.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelColumn.class))
                .forEach(field -> {
                    ExcelColumn metadata = field.getAnnotation(ExcelColumn.class);

                    String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    try {
                        Method method = t.getMethod(getterName);
                        columns.add(Pair.of(metadata, method));
                    } catch (NoSuchMethodException e) {
                        log.warn("EXCEL SERVICE: getter for field {} not found", field.getName());
                    }
                });

        Arrays.stream(t.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(ExcelColumn.class))
                .forEach(method -> {
                    ExcelColumn metadata = method.getAnnotation(ExcelColumn.class);
                    columns.add(Pair.of(metadata, method));
                });

        columns.sort(Comparator.comparingInt(a -> a.getFirst().order()));

        return columns;
    }

    private <T> void validate(Class<T> t) {
        if (!t.isAnnotationPresent(ExcelFile.class)) {
            throw new LogicalException("Not excel dto");
        }

        List<Pair<ExcelColumn, Method>> methods = getRowMethods(t);

        if (methods.size() == 0) {
            throw new LogicalException("Not row in excel dto");
        }

        boolean containsNotStringField = methods.stream()
                .map(Pair::getSecond)
                .map(Method::getReturnType)
                .anyMatch(returnType -> findSetter(returnType) == null);

        if (containsNotStringField) {
            throw new LogicalException("Not all row in excel dto has cellValueSetter");
        }
    }

    private CellValueSetter findSetter(Class type) {
        return cellValueSetters.stream()
                .filter(cellValueSetter -> cellValueSetter.canSetType(type))
                .findFirst()
                .orElse(null);
    }

    private CellStyle getHeaderStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        font.setItalic(false);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);

        return style;
    }

}
