package uz.ciasev.ubdd_service.service.search.sms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListExcelProjection;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListProjection;

import java.util.Map;
import java.util.stream.Stream;

@Validated
public interface SmsSearchService {

    Page<SmsFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable);

    Stream<SmsFullListExcelProjection> findAllExcelProjectionByFilter(Map<String, String> filterValues, int limit, Sort sort);
}
