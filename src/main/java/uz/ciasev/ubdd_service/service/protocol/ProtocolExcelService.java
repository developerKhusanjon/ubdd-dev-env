package uz.ciasev.ubdd_service.service.protocol;

import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Map;

public interface ProtocolExcelService {

    byte[] globalSearchByFilter(Map<String, String> filterValues, Pageable pageable) throws IOException;

}
