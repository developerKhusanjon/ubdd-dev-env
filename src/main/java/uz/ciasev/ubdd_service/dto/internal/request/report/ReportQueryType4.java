package uz.ciasev.ubdd_service.dto.internal.request.report;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class ReportQueryType4 extends ReportQueryType1 {

    private Set<ArticleViolationType> articleViolationTypes;

}
