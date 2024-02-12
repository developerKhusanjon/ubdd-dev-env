package uz.ciasev.ubdd_service.service.report;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.request.report.ReportQueryType1;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

@Getter
public class ReportContextT1 extends ReportContext {

    private final List<ArticlePart> articleParts;

    private final List<Organ> organs;

    private final List<Department> departments;

    private final List<Region> regions;

    private final List<District> districts;

    public ReportContextT1(User user, ReportQueryType1 params) {
        super(user, params);
        this.articleParts = normalizeInput(params.getArticleParts());
        this.organs = normalizeInput(params.getOrgans());
        this.departments = normalizeInput(params.getDepartments());
        this.regions = normalizeInput(params.getRegions());
        this.districts = normalizeInput(params.getDistricts());
    }
}
