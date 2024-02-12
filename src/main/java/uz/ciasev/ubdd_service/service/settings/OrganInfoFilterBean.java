package uz.ciasev.ubdd_service.service.settings;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.specifications.OrganInfoSpecifications;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.LongFilter;

@Component
@AllArgsConstructor
public class OrganInfoFilterBean {
    private final OrganInfoSpecifications specifications;

    @Bean
    public FilterHelper<OrganInfo> getOrganInfoFilterHelper() {
        return new FilterHelper<OrganInfo>(
                Pair.of("departmentId", new LongFilter<OrganInfo>(specifications::withDepartmentId)),
                Pair.of("organId", new LongFilter<OrganInfo>(specifications::withOrganId)),
                Pair.of("districtId", new LongFilter<OrganInfo>(specifications::withDistrictId)),
                Pair.of("regionId", new LongFilter<OrganInfo>(specifications::withRegionId))
        );
    }
}
