package uz.ciasev.ubdd_service.repository.admcase.deletion;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.specifications.AdmCaseDeletionRequestSpecifications;
import uz.ciasev.ubdd_service.utils.filters.DateFilter;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.LongFilter;
import uz.ciasev.ubdd_service.utils.filters.StringFilter;

@Component
@AllArgsConstructor
public class AdmCaseDeletionRequestFilterBean {

    private final AdmCaseDeletionRequestSpecifications specifications;

    @Bean
    public FilterHelper<AdmCaseDeletionRequest> getAdmCaseDeletionRequestFilterHelper() {
        return new FilterHelper<AdmCaseDeletionRequest>(
                Pair.of("statusId", new LongFilter<AdmCaseDeletionRequest>(specifications::withStatusId)),
                Pair.of("deleteReasonId", new LongFilter<AdmCaseDeletionRequest>(specifications::withDeleteReasonId)),
                Pair.of("userId", new LongFilter<AdmCaseDeletionRequest>(specifications::withUserId)),
                Pair.of("adminId", new LongFilter<AdmCaseDeletionRequest>(specifications::withAdminId)),
                Pair.of("createdTimeFrom", new DateFilter<AdmCaseDeletionRequest>(specifications::createdAfter)),
                Pair.of("createdTimeTo", new DateFilter<AdmCaseDeletionRequest>(specifications::createdBefore)),
                Pair.of("editedTimeFrom", new DateFilter<AdmCaseDeletionRequest>(specifications::editedAfter)),
                Pair.of("editedTimeTo", new DateFilter<AdmCaseDeletionRequest>(specifications::editedBefore)),
                Pair.of("admCaseOrganId", new LongFilter<AdmCaseDeletionRequest>(specifications::withOrganId)),
                Pair.of("admCaseRegionId", new LongFilter<AdmCaseDeletionRequest>(specifications::withRegionId)),
                Pair.of("admCaseDistrictId", new LongFilter<AdmCaseDeletionRequest>(specifications::withDistrictId)),
                Pair.of("admCaseNumber", new StringFilter<AdmCaseDeletionRequest>(specifications::withAdmCaseNumber))
        );
    }
}
