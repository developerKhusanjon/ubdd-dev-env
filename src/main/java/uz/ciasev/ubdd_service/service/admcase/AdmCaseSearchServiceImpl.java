package uz.ciasev.ubdd_service.service.admcase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseListProjection;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.admcase.AdmCaseRepository;
import uz.ciasev.ubdd_service.specifications.AdmCaseSpecifications;
import uz.ciasev.ubdd_service.utils.filters.*;

import java.util.List;
import java.util.Map;

@Service
public class AdmCaseSearchServiceImpl implements AdmCaseSearchService {

    private final AdmCaseSpecifications admCaseSpecifications;
    private final AdmCaseRepository admCaseRepository;
    private final FilterHelper<AdmCase> filterHelper;

    @Autowired
    public AdmCaseSearchServiceImpl(AdmCaseSpecifications admCaseSpecifications, AdmCaseRepository admCaseRepository) {
        this.admCaseSpecifications = admCaseSpecifications;
        this.admCaseRepository = admCaseRepository;

        filterHelper = new FilterHelper<>(
                Pair.of("id", new LongFilter<AdmCase>(this.admCaseSpecifications::withId)),
                Pair.of("claimId", new LongFilter<AdmCase>(this.admCaseSpecifications::withClaimId)),
                Pair.of("number", new StringFilter<AdmCase>(this.admCaseSpecifications::withNumber)),
                Pair.of("idOrNumber", new StringFilter<AdmCase>(this.admCaseSpecifications::withIdOrNumber)),
                Pair.of("createdTimeFrom", new DateFilter<AdmCase>(this.admCaseSpecifications::createdAfter)),
                Pair.of("createdTimeTo", new DateFilter<AdmCase>(this.admCaseSpecifications::createdBefore)),
                Pair.of("statusIdIn", new SetFilter<AdmCase>(this.admCaseSpecifications::withStatusIdIn)),
                Pair.of("organId", new LongFilter<AdmCase>(this.admCaseSpecifications::withOrganId)),
                Pair.of("departmentId", new LongFilter<AdmCase>(this.admCaseSpecifications::withDepartmentId)),
                Pair.of("regionId", new LongFilter<AdmCase>(this.admCaseSpecifications::withRegionId)),
                Pair.of("districtId", new LongFilter<AdmCase>(this.admCaseSpecifications::withDistrictId)),
                Pair.of("courtStatusId", new LongFilter<AdmCase>(this.admCaseSpecifications::withCourtStatusId)),
                Pair.of("courtRegionId", new LongFilter<AdmCase>(this.admCaseSpecifications::withCourtRegionId)),
                Pair.of("courtDistrictId", new LongFilter<AdmCase>(this.admCaseSpecifications::withCourtDistrictId)),
                Pair.of("hearingDateFrom", new DateFilter<AdmCase>(this.admCaseSpecifications::hearingInCourtAfter)),
                Pair.of("hearingDateTo", new DateFilter<AdmCase>(this.admCaseSpecifications::hearingInCourtBefore)),
                Pair.of("protocolArticlePartId", new SetFilter<AdmCase>(this.admCaseSpecifications::withProtocolArticlePartIdIn)),
                Pair.of("protocolArticlePartIdIn", new SetFilter<AdmCase>(this.admCaseSpecifications::withProtocolArticlePartIdIn)),
                Pair.of("violatorFirstName", new StringFilter<AdmCase>(this.admCaseSpecifications::violatorPersonFirstNameLike)),
                Pair.of("violatorSecondName", new StringFilter<AdmCase>(this.admCaseSpecifications::violatorPersonSecondNameLike)),
                Pair.of("violatorLastName", new StringFilter<AdmCase>(this.admCaseSpecifications::violatorPersonLastNameLike))
        );
    }

    @Override
    public Page<AdmCaseListProjection> findConsideredAdmCasesByUserAndFilter(User user, Map<String, String> filters, Pageable pageable) {
        return getPageWithSpecification(pageable, getConsideredAdmCasesByUserSpec(user, filters));
    }

    @Override
    public List<AdmCaseListProjection> findConsideredAdmCasesByUserAndFilterList(User user, Map<String, String> filters, Pageable pageable) {
        return getListWithSpecification(pageable, getConsideredAdmCasesByUserSpec(user, filters));
    }

    @Override
    public Page<Long> findConsideredAdmCasesByUserAndFilterPagination(User user, Map<String, String> filters, Pageable pageable) {
        return getPaginationWithSpecification(pageable, getConsideredAdmCasesByUserSpec(user, filters));
    }

    @Override
    public Page<AdmCaseListProjection> findConsideredAdmCasesByOrgan(User user, Map<String, String> filters, Pageable pageable) {
        return getPageWithSpecification(pageable, getConsideredAdmCasesByOrganSpec(user, filters));
    }

    @Override
    public List<AdmCaseListProjection> findConsideredAdmCasesByOrganList(User user, Map<String, String> filters, Pageable pageable) {
        return getListWithSpecification(pageable, getConsideredAdmCasesByOrganSpec(user, filters));
    }

    @Override
    public Page<Long> findConsideredAdmCasesByOrganPagination(User user, Map<String, String> filters, Pageable pageable) {
        return getPaginationWithSpecification(pageable, getConsideredAdmCasesByOrganSpec(user, filters));
    }

    private Page<AdmCaseListProjection> getPageWithSpecification(Pageable pageable, Specification<AdmCase> specification) {
        Page<Long> caseIdPage = admCaseRepository.findAllId(specification, pageable);
        List<AdmCaseListProjection> result = admCaseRepository.findListProjectionByIds(caseIdPage.getContent(), pageable.getSort());

        return new PageImpl<>(result, pageable, caseIdPage.getTotalElements());
    }

    private List<AdmCaseListProjection> getListWithSpecification(Pageable pageable, Specification<AdmCase> specification) {
        List<Long> caseIdPage = admCaseRepository.findAllIdList(specification, pageable);
        return admCaseRepository.findListProjectionByIds(caseIdPage, pageable.getSort());
    }

    private Page<Long> getPaginationWithSpecification(Pageable pageable, Specification<AdmCase> specification) {
        return admCaseRepository.getPagination(specification, pageable);
    }

    private Specification<AdmCase> getConsideredAdmCasesByOrganSpec(User user, Map<String, String> filters) {
        return admCaseSpecifications.isActiveOnly()
                .and(admCaseSpecifications.inUserVisibility(user))
                .and(filterHelper.getParamsSpecification(filters));
    }

    private Specification<AdmCase> getConsideredAdmCasesByUserSpec(User user, Map<String, String> filter) {
        return admCaseSpecifications.isActiveOnly()
                        .and(admCaseSpecifications.withConsiderUser(user))
                        .and(admCaseSpecifications.inUserVisibility(user))
                        .and(filterHelper.getParamsSpecification(filter));
    }
}
