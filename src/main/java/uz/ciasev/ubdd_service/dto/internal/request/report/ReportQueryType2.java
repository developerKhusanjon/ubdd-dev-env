package uz.ciasev.ubdd_service.dto.internal.request.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class ReportQueryType2 extends ReportQueryType1 {

    Region region;

    @Override
    public Set<Region> getRegions() {
        return region == null ? null : Set.of(region);
    }
}
