package uz.ciasev.ubdd_service.service.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Stream;

public interface ExcelService {

    <T> byte[] saveToExcel(Class<T> t, Stream<T> rows) throws IOException;

    <T> void saveToExcel(OutputStream outputStream, Class<T> t, Stream<T> rows) throws IOException;
}
