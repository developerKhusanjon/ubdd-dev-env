package uz.ciasev.ubdd_service.service.excel.cell;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataTimeCellValueSetter implements CellValueSetter {

    private CellStyle cellStyle;

    @Override
    public void setValue(Cell cell, Object value) {
        cell.setCellValue((LocalDateTime) value);
        cell.setCellStyle(cellStyle);
    }

    @Override
    public boolean canSetType(Class type) {
        return LocalDateTime.class.equals(type);
    }

    @Override
    public void createCellStyle(Workbook workbook, CreationHelper createHelper) {
        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.MM.dd hh:mm:s"));
    }
}
