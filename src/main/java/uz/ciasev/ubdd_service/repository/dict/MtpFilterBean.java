package uz.ciasev.ubdd_service.repository.dict;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.specifications.dict.MtpSpecifications;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.LongFilter;

@Component
@AllArgsConstructor
public class MtpFilterBean {
    private final MtpSpecifications mtpSpecifications;

    @Bean
    public FilterHelper<Mtp> getMtpFilterHelper() {
        return new FilterHelper<Mtp>(
                Pair.of("districtId", new LongFilter<Mtp>(mtpSpecifications::withDistrictId)),
                Pair.of("regionId", new LongFilter<Mtp>(mtpSpecifications::withRegionId))
        );
    }
}
