package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.specifications.OrganAccountSettingsSpecifications;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.LongFilter;

@Component
@RequiredArgsConstructor
public class OrganAccountSettingsFilterBean {

    private final OrganAccountSettingsSpecifications specifications;

    @Bean
    FilterHelper<OrganAccountSettings> getBean() {
        return new FilterHelper<>(
                Pair.of("organId", new LongFilter<>(specifications::withOrganId)),
                Pair.of("departmentId", new LongFilter<>(specifications::withDepartmentId)),
                Pair.of("regionId", new LongFilter<>(specifications::withRegionId)),
                Pair.of("districtId", new LongFilter<>(specifications::withDistrictId)),
                Pair.of("penaltyDepId", new LongFilter<>(specifications::withPenaltyBillingId)),
                Pair.of("compensationDepId", new LongFilter<>(specifications::withCompensationBillingId))
        );

    }
}
