package uz.ciasev.ubdd_service.service.search.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListExcelProjection;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListProjection;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.repository.notification.sms.SmsNotificationRepository;
import uz.ciasev.ubdd_service.service.search.ChunkedSearchService;
import uz.ciasev.ubdd_service.specifications.SmsSpecifications;
import uz.ciasev.ubdd_service.utils.filters.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class SmsSearchServiceImpl implements SmsSearchService {

    private final SmsNotificationRepository repository;
    private final RequiredFilterHelper<SmsNotification> filterHelper;
    private final SmsSpecifications specifications;
    private final ChunkedSearchService<SmsFullListExcelProjection> chunkedSearchService;

    @Autowired
    public SmsSearchServiceImpl(SmsNotificationRepository repository, SmsSpecifications specifications, ChunkedSearchService<SmsFullListExcelProjection> chunkedSearchService) {
        this.repository = repository;
        this.specifications = specifications;
        this.chunkedSearchService = chunkedSearchService;

        filterHelper = new RequiredFilterHelper<>(

                Pair.of("violatorFirstName", new StringFilter<>(specifications::withViolatorFirstNameLike)),
                Pair.of("violatorSecondName", new StringFilter<>(specifications::withViolatorSecondNameLike)),
                Pair.of("violatorLastName", new StringFilter<>(specifications::withViolatorLastNameLike)),

                Pair.of("violatorBirthDateFrom", new DateFilter<>(specifications::withViolatorBirthDateAfter)),
                Pair.of("violatorBirthDateTo", new DateFilter<>(specifications::withViolatorBirthDateBefore)),

                Pair.of("phoneNumber", new StringFilter<>(specifications::withPhoneNumber)),

                Pair.of("notificationType", new StringFilter<>(specifications::withNotificationType)),
                Pair.of("deliveryStatusId", new LongFilter<>(specifications::withDeliveryStatusId)),
                Pair.of("messageId", new StringFilter<>(specifications::withMessageId)),

                Pair.of("sendDateFrom", new DateFilter<>(specifications::withSendDateAfter)),
                Pair.of("sendDateTo", new DateFilter<>(specifications::withSendDateBefore)),

                Pair.of("receiveDateFrom", new DateFilter<>(specifications::withReceiveDateAfter)),
                Pair.of("receiveDateTo", new DateFilter<>(specifications::withReceiveDateBefore)),

                Pair.of("organId", new LongFilter<>(specifications::withOrganId)),
                Pair.of("regionId", new LongFilter<>(specifications::withRegionId)),
                Pair.of("districtId", new LongFilter<>(specifications::withDistrictId))
        );
    }

    @Override
    public Page<SmsFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable) {
        return getPageWithSpecification(
                pageable,
                filterHelper.getParamsSpecification(filterValues)
        );
    }

    @Override
    public Stream<SmsFullListExcelProjection> findAllExcelProjectionByFilter(Map<String, String> filterValues, int limit, Sort sort) {
        Specification<SmsNotification> specification = filterHelper.getParamsSpecification(filterValues);

        List<Long> ids = repository.findAllId(specification);

        return chunkedSearchService.findAllExcelProjectionByIds(
                ids,
                limit,
                longs -> repository.findAllFullListExcelProjectionById(longs, sort)
        );
    }

    private Page<SmsFullListProjection> getPageWithSpecification(Pageable pageable, Specification<SmsNotification> specification) {
        Page<Long> idPage = repository.findAllId(specification, pageable);
        List<SmsFullListProjection> result = repository.findAllFullListProjectionById(idPage.getContent(), pageable.getSort());

        return new PageImpl<>(result, pageable, idPage.getTotalElements());
    }
}
