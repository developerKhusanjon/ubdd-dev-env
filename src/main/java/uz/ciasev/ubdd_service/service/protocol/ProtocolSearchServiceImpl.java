package uz.ciasev.ubdd_service.service.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.protocol.*;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.SearchFiltersNotSetException;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolFilterBean;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolUbddListViewRepository;
import uz.ciasev.ubdd_service.repository.protocol.ViolationListViewRepository;
import uz.ciasev.ubdd_service.service.search.ChunkedSearchService;
import uz.ciasev.ubdd_service.specifications.ProtocolSpecifications;
import uz.ciasev.ubdd_service.utils.MapUtils;
import uz.ciasev.ubdd_service.utils.filters.RequiredFilterHelper;
import uz.ciasev.ubdd_service.utils.types.GlobalSearchType;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProtocolSearchServiceImpl implements ProtocolSearchService {

    private final ProtocolRepository protocolRepository;
    private final RequiredFilterHelper<Protocol> filterHelper;
    private final ProtocolFilterBean protocolFilter;
    private final ProtocolSpecifications protocolSpecifications;
    private final ProtocolUbddListViewRepository protocolUbddListViewRepository;
    private final ViolationListViewRepository violationListViewRepository;
    private final ChunkedSearchService<ProtocolExcelProjection> chunkedProtocolSearchService;
    private final ChunkedSearchService<ProtocolUbddListExcelProjection> chunkedUbddListSearchService;


    @Override
    public int getCountByAdmCaseId(Long admCaseId) {
        return protocolRepository.findAllId(protocolSpecifications.withAdmCaseId(admCaseId)).size();
    }

    @Override
    public List<ProtocolSimpleListProjection> findAllSimpleProjectionByAdmCaseId(Long admCaseId) {
        return getListWithSpecification(
                Sort.by(Protocol_.CREATED_TIME),
                protocolSpecifications.withAdmCaseId(admCaseId),
                protocolRepository::findAllSimpleListProjectionById
        );
    }

    @Override
    public List<ProtocolSlimProjection> findAllSlimeProjectionByAdmCaseId(Long admCaseId) {
        return getListWithSpecification(
                Sort.unsorted(),
                protocolSpecifications.withAdmCaseId(admCaseId),
                protocolRepository::findAllSlimProjectionByAdmCaseId
        );
    }

    @Override
    public Page<ProtocolUbddListView> findAllUserUbddListProjectionByFilter(User user, Map<String, String> filterValues, Pageable pageable) {
        Specification<Protocol> specification = buildSearchSpecification(filterValues, false)
                .and(protocolSpecifications.activeOnly())
                .and(protocolSpecifications.withOrgan(user.getOrgan()));
//                Ула сказал что маб должен видеть весь сиписок протаколов по своему органу.
//                .and(protocolSpecifications.inUserVisibility(user));

        return getPageWithSpecification(
                pageable,
                specification,
                protocolUbddListViewRepository::findAllByIdIn
        );
    }

    @Override
    public Stream<ProtocolUbddListExcelProjection> findAllUbddExcelProjectionByFilter(User user, Map<String, String> filterValues, int limit, Sort sort) {
        Specification<Protocol> specification = buildSearchSpecification(filterValues, false)
                .and(protocolSpecifications.withOrgan(user.getOrgan()));

        List<Long> ids = protocolRepository.findAllId(specification);

        return chunkedUbddListSearchService.findAllExcelProjectionByIds(
                ids,
                limit,
                longs -> protocolUbddListViewRepository.findAllUbddListExcelProjectionsByIds(longs, sort)
        );
    }

    @Override
    public Page<ProtocolFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable) {
        return getPageWithSpecification(
                pageable,
                buildSearchSpecification(filterValues).and(protocolSpecifications.withIsDeleted(false)),
                protocolRepository::findAllFullListProjectionById
        );
    }

    @Override
    public Stream<ProtocolExcelProjection> findAllExcelProjectionByFilter(Map<String, String> filterValues, long limit, Sort sort) {

        Specification<Protocol> specification = buildSearchSpecification(filterValues).and(protocolSpecifications.withIsDeleted(false));

        List<Long> ids = protocolRepository.findAllId(specification);

        return chunkedProtocolSearchService.findAllExcelProjectionByIds(
                ids,
                limit,
                l -> protocolRepository.findAllExcelProjectionById(l, sort)
        );
    }

    @Override
    public Page<ProtocolFullListProjection> findAllFullProjectionByFilterAndType(Map<String, String> filterValues, Pageable pageable, String searchType) {
        return getPageWithSpecification(
                pageable,
                buildSearchSpecification(filterValues, searchType).and(protocolSpecifications.withIsDeleted(false)),
                protocolRepository::findAllFullListProjectionById
        );
    }

    @Override
    public List<ViolationListView> findAllViolationView(Map<String, String> filterValues) {

        Set<String> searchFields = Set.of(
                // по этим параметрам ищется повторность для регистрации протакола
                "pinpp", "articleId",
                // по этим параметрам ищется повторность для вынесения решения гаи
                "firstName", "secondName", "lastName", "birthDate", "isMain", "hasActiveResolution", "exceptTermination"
        );

        Specification<Protocol> specification = buildSearchSpecification(MapUtils.filter(filterValues, searchFields), true).and(protocolSpecifications.activeOnly());
        return getListWithSpecification(specification, violationListViewRepository::findAllByIdIn);
    }

    @Override
    public List<ProtocolFullListProjection> findAllFullProjectionById(List<Long> protocolsId) {
        return protocolRepository.findAllFullListProjectionById(protocolsId, Sort.unsorted());
    }

    private <T> Page<T> getPageWithSpecification(Pageable pageable, Specification<Protocol> specification, BiFunction<List<Long>, Sort, List<T>> supplier) {
        Page<Long> idPage = protocolRepository.findAllId(specification, pageable);
        List<T> result = supplier.apply(idPage.getContent(), pageable.getSort());

        return new PageImpl<>(result, pageable, idPage.getTotalElements());
    }

    private <T> List<T> getListWithSpecification(Specification<Protocol> specification, BiFunction<List<Long>, Sort, List<T>> supplier) {
        return getListWithSpecification(Sort.unsorted(), specification, supplier);
    }

    private <T> List<T> getListWithSpecification(Sort sort, Specification<Protocol> specification, BiFunction<List<Long>, Sort, List<T>> supplier) {
        List<Long> idList = protocolRepository.findAllId(specification);
        return supplier.apply(idList, sort);
    }

    private Specification<Protocol> buildSearchSpecification(Map<String, String> filterValues) {
        return buildSearchSpecification(filterValues, true);
    }

    private Specification<Protocol> buildSearchSpecification(Map<String, String> filterValues, String searchType) {
        validateFilterParams(filterValues);

        GlobalSearchType type = GlobalSearchType.findByText(searchType.toUpperCase());
        switch (type) {
            case VIOLATOR:
                return buildSearchSpecification(filterValues, true);
            case VICTIM:
                return protocolSpecifications.withVictimFilterParams(filterValues);
            case PARTICIPANT:
                return protocolSpecifications.withParticipantFilterParams(filterValues);
        }
        return buildSearchSpecification(filterValues, true);
    }

    private Specification<Protocol> buildSearchSpecification(Map<String, String> filterValues, boolean checkRequirements) {
        return filterHelper.getParamsSpecification(filterValues, checkRequirements);
    }

    private void validateFilterParams(Map<String, String> filterValues) {
        if (filterValues != null && filterValues.isEmpty()) {
            throw new SearchFiltersNotSetException();
        }
    }
}
