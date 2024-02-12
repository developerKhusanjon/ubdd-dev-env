package uz.ciasev.ubdd_service.dto.internal.request.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReportQueryTypeUbddJadval1 extends ReportQueryType4 {

    Region region;

    @Override
    public Set<Organ> getOrgans() {
        return null;
    }

    @Override
    public Set<Department> getDepartments() {
        return null;
    }

    @Override
    public Set<Region> getRegions() {
        return region == null ? null : Set.of(region);
    }

}
