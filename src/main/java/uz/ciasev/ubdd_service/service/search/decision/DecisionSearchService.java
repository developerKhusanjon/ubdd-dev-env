package uz.ciasev.ubdd_service.service.search.decision;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionExcelProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionFullListProjection;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Validated
public interface DecisionSearchService {

    Page<DecisionFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable);

    Page<DecisionFullListProjection> findAllFullProjectionByFilterType(Map<String, String> filterValues, String searchType, Pageable pageable);

    List<DecisionFullListProjection> findAllFullProjectionByFilterObjects(Map<String, String> filterValues, Pageable pageable);

    Page<Long> findAllFullProjectionByFilterPagination(Map<String, String> filterValues, Pageable pageable);

    List<Decision> findAllByFilter(Long admCaseId, Long resolutionId, Long violatorId, Boolean isActive);

    Stream<DecisionExcelProjection> findAllExcelProjectionByFilter(Map<String, String> filterValues, long limit, Pageable pageable);

    // Список решений, по каторым можно отправить письмо с оповещением О вынесение решения
    Page<DecisionFullListProjection> mailListProjectionByFilter(User user, Map<String, String> filterValues, Pageable pageable);

    // Список решений, по каторым можно отправить письмо с оповещением Об отправке в МИБ
    Page<DecisionFullListProjection> mailListMibProjectionByFilter(User user, Map<String, String> filterValues, Pageable pageable);
}
