package uz.ciasev.ubdd_service.service.search.decision;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionExcelProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.DecisionFullListProjection;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.FiltersNotSetException;
import uz.ciasev.ubdd_service.exception.SearchFiltersNotSetException;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.search.ChunkedSearchService;
import uz.ciasev.ubdd_service.specifications.DecisionSpecifications;
import uz.ciasev.ubdd_service.utils.filters.*;
import uz.ciasev.ubdd_service.utils.types.GlobalSearchType;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class DecisionSearchServiceImpl implements DecisionSearchService {

    private final DecisionRepository decisionRepository;
    private final FilterHelper<Decision> filterHelper;
    private final FilterHelper<Decision> filterViolatorHelper;
    private final DecisionSpecifications decisionSpecifications;
    private final ChunkedSearchService<DecisionExcelProjection> chunkedSearchService;

    @Autowired
    public DecisionSearchServiceImpl(DecisionRepository decisionRepository,
                                     DecisionSpecifications decisionSpecifications,
                                     ChunkedSearchService<DecisionExcelProjection> chunkedSearchService) {

        this.decisionRepository = decisionRepository;
        this.decisionSpecifications = decisionSpecifications;
        this.chunkedSearchService = chunkedSearchService;

        filterHelper = new FilterHelper<>(
                Pair.of("isActive", new BooleanFilter<>(decisionSpecifications::withIsActive)), // Фронт отображает как 3 значение [Активные, Не активные, Все]
                Pair.of("isArchived", new DefaultValueBooleanFilter<>(false, decisionSpecifications::withIsArchived)),
                Pair.of("number", new StringFilter<>(decisionSpecifications::withNumber)),
                Pair.of("mainPunishmentTypeId", new LongFilter<>(decisionSpecifications::withMainPunishmentTypeId)),
                Pair.of("statusIdIn", new SetFilter<>(decisionSpecifications::withStatusIdIn)),
                Pair.of("decisionTypeId", new LongFilter<>(decisionSpecifications::withDecisionTypeId)),
                Pair.of("articlePartIdIn", new SetFilter<>(decisionSpecifications::withArticlePartIdIn)),

                Pair.of("uaseId", new LongFilter<>(decisionSpecifications::withUserId)),
                Pair.of("userId", new LongFilter<>(decisionSpecifications::withUserId)),
                Pair.of("organId", new LongFilter<>(decisionSpecifications::withOrganId)),
                Pair.of("departmentId", new LongFilter<>(decisionSpecifications::withDepartmentId)),
                Pair.of("regionId", new LongFilter<>(decisionSpecifications::withRegionId)),
                Pair.of("districtId", new LongFilter<>(decisionSpecifications::withDistrictId)),
                Pair.of("admCaseId", new LongFilter<>(decisionSpecifications::withAdmCaseId)),
                Pair.of("protocolArticlePartIdIn", new SetFilter<>(decisionSpecifications::withProtocolArticlePartIdIn)),
                Pair.of("admCaseClaimId", new LongFilter<>(decisionSpecifications::withAdmCaseClaimId)),
                Pair.of("resolutionTimeFrom", new DateFilter<>(decisionSpecifications::withResolutionTimeAfter)),
                Pair.of("resolutionTimeTo", new DateFilter<>(decisionSpecifications::withResolutionTimeBefore)),

                Pair.of("violatorNationalityId", new LongFilter<>(decisionSpecifications::withViolatorNationalityId)),
                Pair.of("violatorGenderId", new LongFilter<>(decisionSpecifications::withViolatorGenderId)),
                Pair.of("violatorFirstName", new StringFilter<>(decisionSpecifications::withViolatorFirstNameLike)),
                Pair.of("violatorSecondName", new StringFilter<>(decisionSpecifications::withViolatorSecondNameLike)),
                Pair.of("violatorLastName", new StringFilter<>(decisionSpecifications::withViolatorLastNameLike)),
                Pair.of("violatorBirthDateFrom", new DateFilter<>(decisionSpecifications::withViolatorBirthDateAfter)),
                Pair.of("violatorBirthDateTo", new DateFilter<>(decisionSpecifications::withViolatorBirthDateBefore)),
                Pair.of("violatorDocumentNumber", new StringFilter<>(decisionSpecifications::withViolatorDocumentNumber)),
                Pair.of("violatorDocumentSeries", new StringFilter<>(decisionSpecifications::withViolatorDocumentSeries)),
                Pair.of("violatorActualAddressRegionId", new LongFilter<>(decisionSpecifications::withViolatorAddressRegionId)),
                Pair.of("violatorActualAddressDistrictId", new LongFilter<>(decisionSpecifications::withViolatorAddressDistrictId)),
                Pair.of("violatorActualAddressCountryId", new LongFilter<>(decisionSpecifications::withViolatorAddressCountryId)),

                Pair.of("invoiceSerial", new StringFilter<>(decisionSpecifications::withInvoiceSerial)),
                Pair.of("penaltyLastPayTimeFrom", new DateFilter<>(decisionSpecifications::withPenaltyLastPayTimeAfter)),
                Pair.of("penaltyLastPayTimeTo", new DateFilter<>(decisionSpecifications::withPenaltyLastPayTimeBefore))
        );
        filterViolatorHelper = new FilterHelper<>(
                Pair.of("isActive", new BooleanFilter<>(decisionSpecifications::withIsActive)), // Фронт отображает как 3 значение [Активные, Не активные, Все]
                Pair.of("isArchived", new DefaultValueBooleanFilter<>(false, decisionSpecifications::withIsArchived)),
                Pair.of("number", new StringFilter<>(decisionSpecifications::withNumber)),
                Pair.of("mainPunishmentTypeId", new LongFilter<>(decisionSpecifications::withMainPunishmentTypeId)),
                Pair.of("statusIdIn", new SetFilter<>(decisionSpecifications::withStatusIdIn)),
                Pair.of("decisionTypeId", new LongFilter<>(decisionSpecifications::withDecisionTypeId)),
                Pair.of("articlePartIdIn", new SetFilter<>(decisionSpecifications::withArticlePartIdIn)),

                Pair.of("uaseId", new LongFilter<>(decisionSpecifications::withUserId)),
                Pair.of("userId", new LongFilter<>(decisionSpecifications::withUserId)),
                Pair.of("organId", new LongFilter<>(decisionSpecifications::withOrganId)),
                Pair.of("departmentId", new LongFilter<>(decisionSpecifications::withDepartmentId)),
                Pair.of("regionId", new LongFilter<>(decisionSpecifications::withRegionId)),
                Pair.of("districtId", new LongFilter<>(decisionSpecifications::withDistrictId)),
                Pair.of("admCaseId", new LongFilter<>(decisionSpecifications::withAdmCaseId)),
                Pair.of("protocolArticlePartIdIn", new SetFilter<>(decisionSpecifications::withProtocolArticlePartIdIn)),
                Pair.of("admCaseClaimId", new LongFilter<>(decisionSpecifications::withAdmCaseClaimId)),
                Pair.of("resolutionTimeFrom", new DateFilter<>(decisionSpecifications::withResolutionTimeAfter)),
                Pair.of("resolutionTimeTo", new DateFilter<>(decisionSpecifications::withResolutionTimeBefore)),

                Pair.of("nationalityId", new LongFilter<>(decisionSpecifications::withViolatorNationalityId)),
                Pair.of("genderId", new LongFilter<>(decisionSpecifications::withViolatorGenderId)),
                Pair.of("firstName", new StringFilter<>(decisionSpecifications::withViolatorFirstNameLike)),
                Pair.of("secondName", new StringFilter<>(decisionSpecifications::withViolatorSecondNameLike)),
                Pair.of("lastName", new StringFilter<>(decisionSpecifications::withViolatorLastNameLike)),
                Pair.of("birthDateFrom", new DateFilter<>(decisionSpecifications::withViolatorBirthDateAfter)),
                Pair.of("birthDateTo", new DateFilter<>(decisionSpecifications::withViolatorBirthDateBefore)),
                Pair.of("documentNumber", new StringFilter<>(decisionSpecifications::withViolatorDocumentNumber)),
                Pair.of("documentSeries", new StringFilter<>(decisionSpecifications::withViolatorDocumentSeries)),
                Pair.of("actualAddressRegionId", new LongFilter<>(decisionSpecifications::withViolatorAddressRegionId)),
                Pair.of("actualAddressDistrictId", new LongFilter<>(decisionSpecifications::withViolatorAddressDistrictId)),
                Pair.of("actualAddressCountryId", new LongFilter<>(decisionSpecifications::withViolatorAddressCountryId)),

                Pair.of("invoiceSerial", new StringFilter<>(decisionSpecifications::withInvoiceSerial)),
                Pair.of("penaltyLastPayTimeFrom", new DateFilter<>(decisionSpecifications::withPenaltyLastPayTimeAfter)),
                Pair.of("penaltyLastPayTimeTo", new DateFilter<>(decisionSpecifications::withPenaltyLastPayTimeBefore))
        );
    }

    @Override
    public Page<DecisionFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable) {
        return getPageWithSpecification(
                pageable,
                buildSearchSpecification(filterValues)
        );
    }

    @Override
    public Page<DecisionFullListProjection> findAllFullProjectionByFilterType(Map<String, String> filterValues, String searchType, Pageable pageable) {
        return getPageWithSpecification(
                pageable,
                buildTypedSearchSpecification(filterValues, searchType)
        );
    }

    @Override
    public List<DecisionFullListProjection> findAllFullProjectionByFilterObjects(Map<String, String> filterValues, Pageable pageable) {
        return getListWithSpecification(
                pageable,
                buildSearchSpecification(filterValues)
        );
    }

    @Override
    public Page<Long> findAllFullProjectionByFilterPagination(Map<String, String> filterValues, Pageable pageable) {
        return getPaginationWithSpecification(
                pageable,
                buildSearchSpecification(filterValues)
        );
    }

    @Override
    public Page<DecisionFullListProjection> mailListProjectionByFilter(User user, Map<String, String> filterValues, Pageable pageable) {

        return getPageWithSpecification(
                pageable,
                buildSearchSpecification(filterValues)
                        .and(decisionSpecifications.decisionMailList())
                        .and(decisionSpecifications.inUserVisibility(user))
        );
    }

    @Override
    public Page<DecisionFullListProjection> mailListMibProjectionByFilter(User user, Map<String, String> filterValues, Pageable pageable) {

        return getPageWithSpecification(
                pageable,
                buildSearchSpecification(filterValues)
                        .and(decisionSpecifications.decisionPossiblyToBeSendToMib())
                        .and(decisionSpecifications.inUserVisibility(user))
        );
    }

    @Override
    public List<Decision> findAllByFilter(Long admCaseId,
                                          Long resolutionId,
                                          Long violatorId,
                                          Boolean isActive) {

        if (resolutionId == null && violatorId == null && admCaseId == null) {
            throw FiltersNotSetException.onOfRequired("resolutionId", "violatorId", "admCaseId");
        }

        return decisionRepository.findAll(
                decisionSpecifications.withAdmCaseId(admCaseId)
                        .and(decisionSpecifications.withResolutionId(resolutionId))
                        .and(decisionSpecifications.withViolatorId(violatorId))
                        .and(decisionSpecifications.withIsActive(isActive))
        );
    }

    @Override
    public Stream<DecisionExcelProjection> findAllExcelProjectionByFilter(Map<String, String> filterValues, long limit, Pageable pageable) {

        Specification<Decision> specification = buildSearchSpecification(filterValues);

        List<Long> ids = decisionRepository.findAllId(specification);

        return chunkedSearchService.findAllExcelProjectionByIds(
                ids,
                limit,
                l -> decisionRepository.findExcelProjectionById(l, pageable.getSort())
        );
    }

    private Page<DecisionFullListProjection> getPageWithSpecification(Pageable pageable, Specification<Decision> specification) {

        Page<Long> idPage = decisionRepository.findAllId(specification, pageable);
        List<DecisionFullListProjection> result = decisionRepository.findAllFullListProjectionById(idPage.getContent(), pageable.getSort());

        return new PageImpl<>(result, pageable, idPage.getTotalElements());
    }

    private List<DecisionFullListProjection> getListWithSpecification(Pageable pageable, Specification<Decision> specification) {
        List<Long> idPage = decisionRepository.findAllIdList(specification, pageable);
        return decisionRepository.findAllFullListProjectionById(idPage, pageable.getSort());
    }

    private Page<Long> getPaginationWithSpecification(Pageable pageable, Specification<Decision> specification) {
        return decisionRepository.getPagination(specification, pageable);
    }

    private Specification<Decision> buildSearchSpecification(Map<String, String> filterValues) {
        return filterHelper.getParamsSpecification(filterValues);
    }

    private Specification<Decision> buildTypedSearchSpecification(Map<String, String> filterValues, String searchType) {
        validateFilterParams(filterValues);

        GlobalSearchType type = GlobalSearchType.findByText(searchType.toUpperCase());
        switch (type) {
            case VIOLATOR:
                return filterViolatorHelper.getParamsSpecification(filterValues);
            case VICTIM:
                return decisionSpecifications.withVictimFilterParams(filterValues);
            case PARTICIPANT:
                return decisionSpecifications.withParticipantFilterParams(filterValues);
        }
        return filterHelper.getParamsSpecification(filterValues);
    }

    private void validateFilterParams(Map<String, String> filterValues) {
        if (filterValues != null && filterValues.isEmpty()) {
            throw new SearchFiltersNotSetException();
        }
    }
}
