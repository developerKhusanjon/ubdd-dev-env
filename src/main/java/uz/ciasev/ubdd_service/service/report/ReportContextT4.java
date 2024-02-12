package uz.ciasev.ubdd_service.service.report;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.request.report.ReportQueryType4;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

@Getter
public class ReportContextT4 extends ReportContextT1 {

    private final List<ArticleViolationType> articleViolationTypes;

    public ReportContextT4(User user, ReportQueryType4 params) {
        super(user, params);
        this.articleViolationTypes = normalizeInput(params.getArticleViolationTypes());
    }
}
