package uz.ciasev.ubdd_service.service.excel;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListExcelProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolExcelProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddListExcelProjection;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.UserListExcelProjection;
import uz.ciasev.ubdd_service.service.protocol.ProtocolSearchService;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionExcelProjection;
import uz.ciasev.ubdd_service.service.search.decision.DecisionSearchService;
import uz.ciasev.ubdd_service.service.search.sms.SmsSearchService;
import uz.ciasev.ubdd_service.service.user.UserAdminService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class SearchExportToExcelServiceImpl implements SearchExportToExcelService {

//    private final int ROW_LIMIT = 32767 - 1;
    private final int ROW_LIMIT = 10000;
    private final ExcelService excelService;
    private final ProtocolSearchService protocolSearchService;
    private final DecisionSearchService decisionSearchService;
    private final SmsSearchService smsSearchService;
    private final UserAdminService userSearchService;

    @Override
    public void protocolGlobalSearchByFilter(OutputStream outputStream, Map<String, String> filterValues, Pageable pageable) throws IOException {
        Stream<ProtocolExcelProjection> protocols = protocolSearchService.findAllExcelProjectionByFilter(filterValues, ROW_LIMIT, pageable.getSort());
        excelService.saveToExcel(outputStream, ProtocolExcelProjection.class, protocols);
    }

    @Override
    public void decisionGlobalSearchByFilter(OutputStream outputStream, Map<String, String> filterValues, Pageable pageable) throws IOException {
        Stream<DecisionExcelProjection> protocols = decisionSearchService.findAllExcelProjectionByFilter(filterValues, ROW_LIMIT, pageable);
        excelService.saveToExcel(outputStream, DecisionExcelProjection.class, protocols);
    }

    @Override
    public byte[] protocolsUbddListByFilter(User user, Map<String, String> filterValues, Pageable pageable) throws IOException {
        Stream<ProtocolUbddListExcelProjection> protocols = protocolSearchService.findAllUbddExcelProjectionByFilter(
                user,
                filterValues,
                ROW_LIMIT,
                pageable.getSort()
        );
        return excelService.saveToExcel(ProtocolUbddListExcelProjection.class, protocols);
    }

    @Override
    public byte[] smsSearchListByFilter(Map<String, String> filters, Pageable pageable) throws IOException {
        Stream<SmsFullListExcelProjection> smsStream = smsSearchService.findAllExcelProjectionByFilter(
                filters,
                ROW_LIMIT,
                pageable.getSort()
        );
        return excelService.saveToExcel(SmsFullListExcelProjection.class, smsStream);
    }

    @Override
    public byte[] usersListByFilter(User user, Map<String, String> filters, Pageable pageable) throws IOException {
        Stream<UserListExcelProjection> userStream = userSearchService.finadAllExcelProjectionByFilter(
                user,
                filters,
                ROW_LIMIT,
                pageable.getSort()
        );

        return excelService.saveToExcel(UserListExcelProjection.class, userStream);
    }
}
