package uz.ciasev.ubdd_service.service.excel.cell;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public interface CellValueSetter {

    void setValue(Cell cell, Object value);

    boolean canSetType(Class type);

    void createCellStyle(Workbook workbook, CreationHelper createHelper);
}
