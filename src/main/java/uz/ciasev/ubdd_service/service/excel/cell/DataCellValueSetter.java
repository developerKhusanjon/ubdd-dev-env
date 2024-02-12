package uz.ciasev.ubdd_service.service.excel.cell;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataCellValueSetter implements CellValueSetter {

    private CellStyle cellStyle;

    @Override
    public void setValue(Cell cell, Object value) {
        cell.setCellValue((LocalDate) value);
        cell.setCellStyle(cellStyle);
    }

    @Override
    public boolean canSetType(Class type) {
        return LocalDate.class.equals(type);
    }

    @Override
    public void createCellStyle(Workbook workbook, CreationHelper createHelper) {
        cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.mm.dd"));
    }
}
