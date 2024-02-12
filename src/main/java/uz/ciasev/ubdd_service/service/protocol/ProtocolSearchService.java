package uz.ciasev.ubdd_service.service.protocol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.protocol.*;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Validated
public interface ProtocolSearchService {

    int getCountByAdmCaseId(Long admCaseId);

    List<ProtocolSimpleListProjection> findAllSimpleProjectionByAdmCaseId(Long admCaseId);

    List<ProtocolSlimProjection> findAllSlimeProjectionByAdmCaseId(Long admCaseId);

    Page<ProtocolUbddListView> findAllUserUbddListProjectionByFilter(User user, Map<String, String> filterValues, Pageable pageable);

    Stream<ProtocolUbddListExcelProjection> findAllUbddExcelProjectionByFilter(User user, Map<String, String> filterValues, int limit, Sort sort);

    Page<ProtocolFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable);

    Page<ProtocolFullListProjection> findAllFullProjectionByFilterAndType(Map<String, String> filterValues, Pageable pageable, String searchType);

    Stream<ProtocolExcelProjection> findAllExcelProjectionByFilter(Map<String, String> filterValues, long limit, Sort pageable);

    List<ViolationListView> findAllViolationView(Map<String, String> filterValues);

    List<ProtocolFullListProjection> findAllFullProjectionById(List<Long> protocolsId);
}
