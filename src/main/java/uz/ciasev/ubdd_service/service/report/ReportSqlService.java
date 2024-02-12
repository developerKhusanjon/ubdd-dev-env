package uz.ciasev.ubdd_service.service.report;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.report.ReportSql;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.exception.notfound.EntityByAliasNotPresent;
import uz.ciasev.ubdd_service.repository.ReportSqlRepository;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.service.report.SqlAlias.*;

@Service
@AllArgsConstructor
public class ReportSqlService {
    private final static String REGISTRATION_DATE_PARAM = "registration_date_param";
    private final static String PROTOCOL_CREATION_DATE_PARAM = "protocol_creation_date_param";
    private final static String FILTER_ORGANS_PARAM = "filter_organs_param";
    private final static String FILTER_DEPARTMENTS_PARAM = "filter_departments_param";
    private final static String FILTER_REGIONS_PARAM = "filter_regions_param";
    private final static String FILTER_DISTRICTS_PARAM = "filter_districts_param";
    private final static String FILTER_ARTICLE_PARTS_PARAM = "filter_article_parts_param";
    private static final String FILTER_STATUS_PARAM = "filter_statuses_param";
    private static final String FILTER_DECISION_TYPE_PARAM = "filter_decision_type_param";
    private final static String FILTER_VIOLATION_TYPES_PARAM = "filter_article_violation_types_param";

    private final ReportSqlRepository reportSqlRepository;

    public String getSqlForReportProtocolArticle(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
//
//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_PROTOCOL_ARTICLE).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_PROTOCOL_ARTICLE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery().replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportProtocolArticleByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_PROTOCOL_ARTICLE_BY_REGION).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_PROTOCOL_ARTICLE_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery().replaceFirst("::WHERE::", where);
    }

    public String getSqlForReportProtocolArticleViolationTime(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.violation_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
//
//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_PROTOCOL_ARTICLE).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_PROTOCOL_ARTICLE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery().replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportProtocolArticleByViolationTimeByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.violation_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_PROTOCOL_ARTICLE_BY_REGION).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_PROTOCOL_ARTICLE_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery().replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportProtocolByViolatorType(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(PROTOCOL_BY_VIOLATOR_TYPE_REPORT).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(PROTOCOL_BY_VIOLATOR_TYPE_REPORT);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery().replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportDecisionByArticle(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(DECISION_BY_ARTICLE)
//                .replaceFirst("::WHERE::", where)
//                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

        ReportSql reportSql = getSql(DECISION_BY_ARTICLE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }


    public String getSqlForReportDecisionByRegions(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(DECISION_BY_ARTICLE)
//                .replaceFirst("::WHERE::", where)
//                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

        ReportSql reportSql = getSql(DECISION_BY_REGIONS);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }


    public String getSqlForReportDecisionByDistricts(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(DECISION_BY_ARTICLE)
//                .replaceFirst("::WHERE::", where)
//                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

        ReportSql reportSql = getSql(DECISION_BY_DISTRICTS);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportCourtDecisionByArticle(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(COURT_DECISION_BY_ARTICLE).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(COURT_DECISION_BY_ARTICLE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }

    public String getSqlForReportCourtVcc(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(COURT_VCC).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(COURT_VCC);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }

    public String getSqlForReportCourtVccByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(COURT_VCC_BY_REGION).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(COURT_VCC_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }

    public String getSqlForReportCourtReconciliation(ReportContextT3 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_STATUS_PARAM, "p.adm_status_id");
        names.put(FILTER_DECISION_TYPE_PARAM, "p.decision_type_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(COURT_RECONCILIATION).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(COURT_RECONCILIATION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }

    public String getSqlPrepareForReportCourtReconciliation(ReportContextT3 reportContextT3) {
        return getSql(PREPARE_COURT_RECONCILIATION).getQuery();
    }

    public String getSqlForReportTerminationDecision(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "d.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "ac.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "ac.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "ac.department_id");
        names.put(FILTER_REGIONS_PARAM, "ac.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "ac.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(TERMINATION_DECISION)
//                .replaceFirst("::WHERE::", where)
//                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

        ReportSql reportSql = getSql(TERMINATION_DECISION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

    }

    public String getSqlForReportTerminationDecisionByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "d.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "ac.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "ac.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "ac.department_id");
        names.put(FILTER_REGIONS_PARAM, "ac.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "ac.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(TERMINATION_DECISION_BY_REGION)
//                .replaceFirst("::WHERE::", where)
//                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

        ReportSql reportSql = getSql(TERMINATION_DECISION_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportInspector(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_PROTOCOL_INSPECTOR).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_PROTOCOL_INSPECTOR);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportInspectorByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_PROTOCOL_INSPECTOR_BY_REGION).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_PROTOCOL_INSPECTOR_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportAgeCategory(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_BY_AGE_CATEGORY).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_BY_AGE_CATEGORY);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportAgeCategoryByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_BY_AGE_CATEGORY_BY_REGION).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_BY_AGE_CATEGORY_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportDamagesAndCompensationsByArticle(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.created_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(REPORT_BY_DAMAGES_AND_COMPENSATIONS_BY_ARTICLE).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_BY_DAMAGES_AND_COMPENSATIONS_BY_ARTICLE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }


    public String getSqlForReportDecisionByViolationTypes(ReportContextT4 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(PROTOCOL_CREATION_DATE_PARAM, "aa.protocol_date");
        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");
        names.put(FILTER_VIOLATION_TYPES_PARAM, "aa.article_violation_type_id");

        ReportSql reportSql = getSql(DECISION_BY_VIOLATION_TYPE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportViolationsOfUseNaturalResources(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.violation_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

        ReportSql reportSql = getSql(REPORT_BY_VIOLATIONS_OF_USAGE_NATURAL_RESOURCES);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery().replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }


    public String getSqlForViolationsOfUseNaturalResourcesByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.violation_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

        ReportSql reportSql = getSql(REPORT_BY_VIOLATIONS_OF_USAGE_NATURAL_RESOURCES_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery().replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }


    public String getSqlForReportUbddBase(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_REGIONS_PARAM, "a.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "a.district_id");

        ReportSql reportSql = getSql(UBDD_BASE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceAll("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportUbddJadval1(ReportContextT4 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.protocol_article_part_id");
        names.put(FILTER_VIOLATION_TYPES_PARAM, "p.protocol_violation_id");

        ReportSql reportSql = getSql(REPORT_UBDD_JADVAL1);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceAll("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportUbddJadval1ByRegion(ReportContextT4 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.protocol_article_part_id");
        names.put(FILTER_VIOLATION_TYPES_PARAM, "p.protocol_violation_id");

        ReportSql reportSql = getSql(REPORT_UBDD_JADVAL1_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceAll("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportUbddJadval2(ReportContextT4 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(REGISTRATION_DATE_PARAM, "p.protocol_date");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.protocol_article_part_id");
        names.put(FILTER_VIOLATION_TYPES_PARAM, "p.protocol_violation_id");

        ReportSql reportSql = getSql(REPORT_UBDD_JADVAL2);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceAll("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportUbddJadval2ByRegion(ReportContextT4 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(REGISTRATION_DATE_PARAM, "p.protocol_date");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.protocol_article_part_id");
        names.put(FILTER_VIOLATION_TYPES_PARAM, "p.protocol_violation_id");

        ReportSql reportSql = getSql(REPORT_UBDD_JADVAL2_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceAll("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportUbddJadval3(ReportContextT4 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(REGISTRATION_DATE_PARAM, "p.protocol_date");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.protocol_article_part_id");
        names.put(FILTER_VIOLATION_TYPES_PARAM, "p.protocol_violation_id");

        ReportSql reportSql = getSql(REPORT_UBDD_JADVAL3);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceAll("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportUbddJadval3ByRegion(ReportContextT4 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(REGISTRATION_DATE_PARAM, "p.protocol_date");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.protocol_article_part_id");
        names.put(FILTER_VIOLATION_TYPES_PARAM, "p.protocol_violation_id");

        ReportSql reportSql = getSql(REPORT_UBDD_JADVAL3_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceAll("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportByMib(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(DECISION_BY_ARTICLE)
//                .replaceFirst("::WHERE::", where)
//                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

        ReportSql reportSql = getSql(REPORT_MIB);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportWantedList(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.registration_time");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(COURT_VCC).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_WANTED_LIST);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }

    public String getSqlForReportCountWanted(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.registration_time");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(COURT_VCC).replaceFirst("::WHERE::", where);

        ReportSql reportSql = getSql(REPORT_COUNT_WANTED);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportCountWantedByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.registration_time");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

        ReportSql reportSql = getSql(REPORT_COUNT_WANTED_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForgetReportVictimDamages(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.registration_time");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");

        ReportSql reportSql = getSql(REPORT_VICTIM_DAMAGES);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForgetReportVictimDamagesByRegion(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.registration_time");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");

        ReportSql reportSql = getSql(REPORT_VICTIM_DAMAGES_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportCourtDecisionByRegions(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

        ReportSql reportSql = getSql(COURT_DECISION_BY_REGIONS);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }

    public String getSqlForReportCourtDecisionByDistricts(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "aa.decision_date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "aa.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "aa.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "aa.department_id");
        names.put(FILTER_REGIONS_PARAM, "aa.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "aa.district_id");

        ReportSql reportSql = getSql(COURT_DECISION_BY_DISTRICTS);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where);
    }

    public String getSqlForReportUserPointsByArticle(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(REGISTRATION_DATE_PARAM, "p.registration_time::date");
        names.put(FILTER_ARTICLE_PARTS_PARAM, "p.article_part_id");
        names.put(FILTER_ORGANS_PARAM, "p.organ_id");
        names.put(FILTER_DEPARTMENTS_PARAM, "p.department_id");
        names.put(FILTER_REGIONS_PARAM, "p.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "p.district_id");

//        String where = buildWhere(names, reportContext);
//        return getSql(DECISION_BY_ARTICLE)
//                .replaceFirst("::WHERE::", where)
//                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));

        ReportSql reportSql = getSql(USER_POINTS_BY_ARTICLE);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportMibSentResultsByRegions(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_ORGANS_PARAM, "t.organ_id");
        names.put(FILTER_REGIONS_PARAM, "t.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "t.district_id");

        ReportSql reportSql = getSql(REPORT_MIB_SENT_RESULTS_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForMibSentResultsByDistricts(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_ORGANS_PARAM, "t.organ_id");
        names.put(FILTER_REGIONS_PARAM, "t.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "t.district_id");

        ReportSql reportSql = getSql(REPORT_MIB_SENT_RESULTS_BY_DISTRICTS);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForReportMibDeclinedResultsByRegions(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_ORGANS_PARAM, "tt.organ_id");
        names.put(FILTER_REGIONS_PARAM, "tt.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "tt.district_id");

        ReportSql reportSql = getSql(REPORT_MIB_DECLINED_RESULTS_BY_REGION);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    public String getSqlForMibDeclinedResultsByDistricts(ReportContextT1 reportContext) {
        Map<String, String> names = new HashMap<>();

        names.put(FILTER_ORGANS_PARAM, "tt.organ_id");
        names.put(FILTER_REGIONS_PARAM, "tt.region_id");
        names.put(FILTER_DISTRICTS_PARAM, "tt.district_id");

        ReportSql reportSql = getSql(REPORT_MIB_DECLINED_RESULTS_BY_DISTRICTS);

        String where = buildWhere(Optional.ofNullable(reportSql.getParamNames()).orElse(names), reportContext);
        return reportSql.getQuery()
                .replaceFirst("::WHERE::", where)
                .replaceAll("::FROM_DATE::", reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                .replaceAll("::TO_DATE::", reportContext.getToDate().format(DateTimeFormatter.ISO_DATE));
    }

    private String buildWhere(Map<String, String> names, ReportContextT4 reportContext) {
        StringBuilder where = new StringBuilder(buildWhere(names, (ReportContextT1) reportContext));

        if (reportContext.getArticleViolationTypes() != null) {
            where.append(" and ")
                    .append(names.get(FILTER_VIOLATION_TYPES_PARAM))
                    .append(" in (")
                    .append(reportContext.getArticleViolationTypes().stream().map(ArticleViolationType::getId).map(String::valueOf).collect(Collectors.joining(",")))
                    .append(")");
        }

        return where.toString();
    }

    private String buildWhere(Map<String, String> names, ReportContextT3 reportContext) {
        StringBuilder where = new StringBuilder(buildWhere(names, (ReportContextT1) reportContext));

        if (reportContext.getAdmStatuses() != null) {
            where.append(" and ")
                    .append(names.get(FILTER_STATUS_PARAM))
                    .append(" in (")
                    .append(reportContext.getAdmStatuses().stream().map(AdmStatus::getId).map(String::valueOf).collect(Collectors.joining(",")))
                    .append(")");
        }

        if (reportContext.getDecisionType() != null) {
            where.append(" and ")
                    .append(names.get(FILTER_DECISION_TYPE_PARAM))
                    .append(" = ")
                    .append(reportContext.getDecisionType().getId())
                    .append(" ");
        }

        return where.toString();
    }

    private String buildWhere(Map<String, String> names, ReportContextT1 reportContext) {
        StringBuilder where = new StringBuilder(buildWhere(names, (ReportContext) reportContext));

        if (Objects.nonNull(reportContext.getArticleParts()) && names.containsKey(FILTER_ARTICLE_PARTS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_ARTICLE_PARTS_PARAM))
                    .append(" in (")
                    .append(reportContext.getArticleParts().stream().map(ArticlePart::getId).map(String::valueOf).collect(Collectors.joining(",")))
                    .append(")");
        }

        if (Objects.nonNull(reportContext.getOrgans()) && names.containsKey(FILTER_ORGANS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_ORGANS_PARAM))
                    .append(" in (")
                    .append(reportContext.getOrgans().stream().map(Organ::getId).map(String::valueOf).collect(Collectors.joining(",")))
                    .append(")");
        }

        if (Objects.nonNull(reportContext.getDepartments()) && names.containsKey(FILTER_DEPARTMENTS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_DEPARTMENTS_PARAM))
                    .append(" in (")
                    .append(reportContext.getDepartments().stream().map(Department::getId).map(String::valueOf).collect(Collectors.joining(",")))
                    .append(")");
        }

        if (Objects.nonNull(reportContext.getRegions()) && names.containsKey(FILTER_REGIONS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_REGIONS_PARAM))
                    .append(" in (")
                    .append(reportContext.getRegions().stream().map(Region::getId).map(String::valueOf).collect(Collectors.joining(",")))
                    .append(")");
        }

        if (Objects.nonNull(reportContext.getDistricts()) && names.containsKey(FILTER_DISTRICTS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_DISTRICTS_PARAM))
                    .append(" in (")
                    .append(reportContext.getDistricts().stream().map(District::getId).map(String::valueOf).collect(Collectors.joining(",")))
                    .append(")");
        }

        return where.toString();
    }

    private String buildWhere(Map<String, String> names, ReportContext reportContext) {
        StringBuilder where = new StringBuilder(" 1 = 1 ");

        if (names.containsKey(REGISTRATION_DATE_PARAM)) {
            where.append(" and ")
                    .append(names.get(REGISTRATION_DATE_PARAM))
                    .append(" >= '")
                    .append(reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                    .append("' and ")
                    .append(names.get(REGISTRATION_DATE_PARAM))
                    .append(" <= '")
                    .append(reportContext.getToDate().format(DateTimeFormatter.ISO_DATE))
                    .append("'");
        }


        if (names.containsKey(PROTOCOL_CREATION_DATE_PARAM)) {
            where.append(" and ")
                    .append(names.get(PROTOCOL_CREATION_DATE_PARAM))
                    .append(" >= '")
                    .append(reportContext.getFromDate().format(DateTimeFormatter.ISO_DATE))
                    .append("' and ")
                    .append(names.get(PROTOCOL_CREATION_DATE_PARAM))
                    .append(" <= '")
                    .append(reportContext.getToDate().format(DateTimeFormatter.ISO_DATE))
                    .append("'");
        }

        if (Objects.nonNull(reportContext.getUser().getOrganId()) && names.containsKey(FILTER_ORGANS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_ORGANS_PARAM))
                    .append(" = ")
                    .append(reportContext.getUser().getOrganId());
        }

        if (Objects.nonNull(reportContext.getUser().getDepartmentId()) && names.containsKey(FILTER_DEPARTMENTS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_DEPARTMENTS_PARAM))
                    .append(" = ")
                    .append(reportContext.getUser().getDepartmentId());
        }

        if (Objects.nonNull(reportContext.getUser().getRegionId()) && names.containsKey(FILTER_REGIONS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_REGIONS_PARAM))
                    .append(" = ")
                    .append(reportContext.getUser().getRegionId());
        }

        if (Objects.nonNull(reportContext.getUser().getDistrictId()) && names.containsKey(FILTER_DISTRICTS_PARAM)) {
            where.append(" and ")
                    .append(names.get(FILTER_DISTRICTS_PARAM))
                    .append(" = ")
                    .append(reportContext.getUser().getDistrictId());
        }

        return where.toString();
    }

    private ReportSql getSql(SqlAlias alias) {
        return reportSqlRepository
                .findByAlias(alias.name())
//                .map(ReportSql::getQuery)
                .orElseThrow(() -> new EntityByAliasNotPresent(ReportSql.class, alias.name()));
    }
}
