package uz.ciasev.ubdd_service.service.excel;

import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.entity.user.User;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public interface SearchExportToExcelService {

    void protocolGlobalSearchByFilter(OutputStream outputStream, Map<String, String> filterValues, Pageable pageable) throws IOException;

    void decisionGlobalSearchByFilter(OutputStream outputStream, Map<String, String> filterValues, Pageable pageable) throws IOException;

    byte[] protocolsUbddListByFilter(User user, Map<String, String> filters, Pageable pageable) throws IOException;

    byte[] smsSearchListByFilter(Map<String, String> filters, Pageable pageable) throws IOException;

    byte[] usersListByFilter(User user, Map<String, String> filters, Pageable pageable) throws IOException;
}
