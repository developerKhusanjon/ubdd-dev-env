package uz.ciasev.ubdd_service.repository.user;

import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.specifications.UserSpecifications;
import uz.ciasev.ubdd_service.utils.filters.BooleanFilter;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.LongFilter;
import uz.ciasev.ubdd_service.utils.filters.StringFilter;

@Component
public class UserFilterBean {

    @Bean
    public FilterHelper<User> getUserFilterHelper(UserSpecifications userSpecifications) {
        return new FilterHelper<User>(
                Pair.of("fio", new StringFilter<>(userSpecifications::withFIOLike)),
                Pair.of("username", new StringFilter<>(userSpecifications::withUsernameLike)),
                Pair.of("workCertificate", new StringFilter<>(userSpecifications::withWorkCertificateLike)),
                Pair.of("isActive", new BooleanFilter<>(userSpecifications::withIsActive)),
                Pair.of("isExternal", new BooleanFilter<>(userSpecifications::withIsExternal)),
                Pair.of("isOffline", new BooleanFilter<>(userSpecifications::withIsOffline)),
                Pair.of("organId", new LongFilter<>(userSpecifications::withOrganId)),
                Pair.of("regionId", new LongFilter<>(userSpecifications::withRegionId)),
                Pair.of("districtId", new LongFilter<>(userSpecifications::withDistrictId)),

                Pair.of("notOrganId", new LongFilter<>(userSpecifications::withoutOrganId)),
                Pair.of("notRegionId", new LongFilter<>(userSpecifications::withoutRegionId)),
                Pair.of("notDistrictId", new LongFilter<>(userSpecifications::withoutDistrictId)),

                Pair.of("roleId", new LongFilter<>(userSpecifications::withRole)),
                Pair.of("isSignatureActive", new BooleanFilter<>(userSpecifications::withSignatureCertificateActive))
        );
    }
}
