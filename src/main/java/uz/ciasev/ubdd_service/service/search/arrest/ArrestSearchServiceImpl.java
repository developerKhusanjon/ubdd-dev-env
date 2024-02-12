package uz.ciasev.ubdd_service.service.search.arrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestFullListProjection;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestPunishment;
import uz.ciasev.ubdd_service.repository.resolution.punishment.ArrestRepository;
import uz.ciasev.ubdd_service.service.user.SystemUserService;
import uz.ciasev.ubdd_service.specifications.ArrestSpecifications;
import uz.ciasev.ubdd_service.utils.filters.*;

import java.util.List;
import java.util.Map;

@Service
public class ArrestSearchServiceImpl implements ArrestSearchService {

    private final ArrestRepository repository;
    private final RequiredFilterHelper<ArrestPunishment> filterHelper;
    private final ArrestSpecifications specifications;
    private final SystemUserService userService;

    @Autowired
    public ArrestSearchServiceImpl(ArrestRepository repository, ArrestSpecifications specifications, SystemUserService userService) {
        this.repository = repository;
        this.specifications = specifications;
        this.userService = userService;

        filterHelper = new RequiredFilterHelper<>(

                Pair.of("violatorFirstName", new StringFilter<>(specifications::withViolatorFirstNameLike)),
                Pair.of("violatorSecondName", new StringFilter<>(specifications::withViolatorSecondNameLike)),
                Pair.of("violatorLastName", new StringFilter<>(specifications::withViolatorLastNameLike)),

                Pair.of("violatorBirthDateFrom", new DateFilter<>(specifications::withViolatorBirthDateAfter)),
                Pair.of("violatorBirthDateTo", new DateFilter<>(specifications::withViolatorBirthDateBefore)),

                Pair.of("violatorDocumentNumber", new StringFilter<>(specifications::withViolatorDocumentNumber)),
                Pair.of("violatorDocumentSeries", new StringFilter<>(specifications::withViolatorDocumentSeries)),

                Pair.of("decisionNumber", new StringFilter<>(specifications::withDecisionNumber)),
                Pair.of("decisionSeries", new StringFilter<>(specifications::withDecisionSeries)),

                Pair.of("resolutionTimeFrom", new DateFilter<>(specifications::withResolutionTimeAfter)),
                Pair.of("resolutionTimeTo", new DateFilter<>(specifications::withResolutionTimeBefore)),

                Pair.of("arrestPunishmentInDateFrom", new DateFilter<>(specifications::withArrestPunishmentInDateAfter)),
                Pair.of("arrestPunishmentInDateTo", new DateFilter<>(specifications::withArrestPunishmentInDateBefore)),

                Pair.of("arrestPunishmentOutDateFrom", new DateFilter<>(specifications::withArrestPunishmentOutDateAfter)),
                Pair.of("arrestPunishmentOutDateTo", new DateFilter<>(specifications::withArrestPunishmentOutDateBefore)),

                Pair.of("statusIdIn", new SetFilter<>(specifications::withStatusIdIn)),

                Pair.of("regionId", new LongFilter<>(specifications::withRegionId)),
                Pair.of("districtId", new LongFilter<>(specifications::withDistrictId))
        );
    }

    @Override
    public Page<ArrestFullListProjection> findAllFullProjectionByFilter(Map<String, String> filterValues, Pageable pageable) {
        return getPageWithSpecification(
                pageable,
                specifications.withUserVisibility(userService.getCurrentUser())
                        .and(specifications.withResolutionIsActive()
                                .and(filterHelper.getParamsSpecification(filterValues))
                        )
        );
    }

    private Page<ArrestFullListProjection> getPageWithSpecification(Pageable pageable, Specification<ArrestPunishment> specification) {
        Page<Long> idPage = repository.findAllId(specification, pageable);
        List<ArrestFullListProjection> result = repository.findAllFullListProjectionById(idPage.getContent(), pageable.getSort());

        return new PageImpl<>(result, pageable, idPage.getTotalElements());
    }
}
