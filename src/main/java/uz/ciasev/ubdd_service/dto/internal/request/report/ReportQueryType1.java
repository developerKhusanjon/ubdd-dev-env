package uz.ciasev.ubdd_service.dto.internal.request.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class ReportQueryType1 extends ReportQuery {

    private Set<ArticlePart> articleParts;

    private Set<Organ> organs;

    private Set<Department> departments;

    private Set<Region> regions;

    private Set<District> districts;
}
