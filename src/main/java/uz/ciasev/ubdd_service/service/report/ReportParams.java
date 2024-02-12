package uz.ciasev.ubdd_service.service.report;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportParams {

    private final static String  ALL_VALUE = "Hammasi";

    private final static String  USERNAME_PARAM_NAME = "username";
    private final static String  EXECUTED_TIME_PARAM_NAME = "executedTime";
    private final static String  FROM_DATE_PARAM_NAME = "fromDate";
    private final static String  TO_DATE_NAME = "toDate";
    private final static String  ARTICLE_PARTS_NAME_PARAM_NAME = "articlePartsName";
    private final static String  ARTICLE_VIOLATION_TYPES_NAME_PARAM_NAME = "articleViolationTypesName";
    private final static String  ORGANS_NAME_PARAM_NAME = "organsName";
    private final static String  DEPARTMENTS_NAME_PARAM_NAME = "departmentsName";
    private final static String  REGIONS_NAME_PARAM_NAME = "regionsName";
    private final static String  DISTRICTS_NAME_PARAM_NAME = "districtsName";

    private final Map<String, Object> jasperParams;

    public ReportParams(User user) {
        jasperParams = new HashMap<>();
        jasperParams.put(JRParameter.REPORT_LOCALE, Locale.FRANCE);
        jasperParams.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.FRANCE);
        jasperParams.put(USERNAME_PARAM_NAME, user.getUsername());
        jasperParams.put(EXECUTED_TIME_PARAM_NAME, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    public Map<String, Object> getParamsMap() {
        return jasperParams;
    }

    public ReportParams fromDate(LocalDate value) {
        setParam(FROM_DATE_PARAM_NAME, value);
        return this;
    }

    public ReportParams toDate(LocalDate value) {
        setParam(TO_DATE_NAME, value);
        return this;
    }

    public ReportParams organs(List<Organ> value) {
        setParam(ORGANS_NAME_PARAM_NAME, value);
        return this;
    }

    public ReportParams departments(List<Department> value) {
        setParam(DEPARTMENTS_NAME_PARAM_NAME, value);
        return this;
    }

    public ReportParams regions(List<Region> value) {
        setParam(REGIONS_NAME_PARAM_NAME, value);
        return this;
    }

    public ReportParams districts(List<District> value) {
        setParam(DISTRICTS_NAME_PARAM_NAME, value);
        return this;
    }

    public ReportParams articleParts(List<ArticlePart> value) {
        setParam(ARTICLE_PARTS_NAME_PARAM_NAME, value);
        return this;
    }

    public ReportParams articleViolationTypes(List<ArticleViolationType> value) {
        setParam(ARTICLE_PARTS_NAME_PARAM_NAME, value);
        return this;
    }

    private void setParam(String name, List<? extends AbstractDict> values) {
        if (values == null) {
            jasperParams.put(name, ALL_VALUE);
        } else {
            jasperParams.put(name, values.stream().map(AbstractDict::getDefaultName).collect(Collectors.joining(", ")));
        }
    };

    private void setParam(String name, LocalDate values) {
        jasperParams.put(name, values.format(DateTimeFormatter.ISO_DATE));
    };


}
