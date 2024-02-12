package uz.ciasev.ubdd_service.service.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.report.*;
import uz.ciasev.ubdd_service.entity.report.ReportTemplate;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByAliasNotPresent;
import uz.ciasev.ubdd_service.repository.ReportTemplateRepository;
import uz.ciasev.ubdd_service.utils.DBHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static uz.ciasev.ubdd_service.service.report.ReportAlias.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {

    private final ReportSqlService reportSqlService;
    private final ReportTemplateRepository reportRepository;
    private final DBHelper dbHelper;

    public void getReportProtocolArticleByOrganAndRegion(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportProtocolArticle
                : reportSqlService::getSqlForReportProtocolArticleByRegion;

        getReportType1(
                REPORT_DEV,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportProtocolArticleByOrganAndRegionByViolationTime(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportProtocolArticleViolationTime
                : reportSqlService::getSqlForReportProtocolArticleByViolationTimeByRegion;

        getReportType1(
                REPORT_DEV,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportProtocolByViolatorType(User user, OutputStream outputStream, ReportQueryType1 params) throws Exception {
        getReportType1(
                PROTOCOL_BY_VIOLATOR_TYPE,
                reportSqlService::getSqlForReportProtocolByViolatorType,
                outputStream,
                user,
                params
        );
    }

    public void getReportDecisionByArticle(User user, OutputStream outputStream, ReportQueryType1 params) throws Exception {
        getReportType1(
                DECISION_ARTICLE,
                reportSqlService::getSqlForReportDecisionByArticle,
                outputStream,
                user,
                params
        );
    }

    public void getReportDecisionByRegionAndDistrict(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportDecisionByRegions
                : reportSqlService::getSqlForReportDecisionByDistricts;

        getReportType1(
                DECISION_BY_REGIONS_OR_DISTRICT,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportCourtDecisionByArticle(User user, OutputStream outputStream, ReportQueryType1 params) throws Exception {
        getReportType1(
                COURT_DECISION_ARTICLE,
                reportSqlService::getSqlForReportCourtDecisionByArticle,
                outputStream,
                user,
                params
        );
    }

    public void getReportCourtVcc(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportCourtVcc
                : reportSqlService::getSqlForReportCourtVccByRegion;

        getReportType1(
                COURT_VCC,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportCourtReconciliation(User user, OutputStream outputStream, ReportQueryType3 params) throws Exception {
        getReportType3(
                COURT_RECONCILIATION,
                reportSqlService::getSqlPrepareForReportCourtReconciliation,
                reportSqlService::getSqlForReportCourtReconciliation,
                outputStream,
                user,
                params
        );
    }

    public void getReportTerminationDecision(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportTerminationDecision
                : reportSqlService::getSqlForReportTerminationDecisionByRegion;

        getReportType1(
                TERMINATION_DECISION,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportInspectorsByOrganAndRegion(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportInspector
                : reportSqlService::getSqlForReportInspectorByRegion;

        getReportType1(
                INSPECTOR,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportByAgeCategory(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportAgeCategory
                : reportSqlService::getSqlForReportAgeCategoryByRegion;

        getReportType1(
                AGE_CATEGORY,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportDamagesAndCompensationsByArticle(User user, OutputStream outputStream, ReportQueryType1 params) throws Exception {
        getReportType1(
                DAMAGES_AND_COMPENSATIONS_BY_ARTICLE,
                reportSqlService::getSqlForReportDamagesAndCompensationsByArticle,
                outputStream,
                user,
                params
        );
    }

    public void getReportDecisionByViolationType(User user, OutputStream outputStream, ReportQueryType4 params) throws Exception {
        getReportType4(
                DECISION_BY_VIOLATION_TYPE,
                reportSqlService::getSqlForReportDecisionByViolationTypes,
                outputStream,
                user,
                params
        );
    }

    public void getReportUbddBase(User user, OutputStream outputStream, ReportQueryTypeUbdd params) throws Exception {
        getReportType1(
                UBDD_BASE,
                reportSqlService::getSqlForReportUbddBase,
                outputStream,
                user,
                params
        );
    }

    public void getReportByUbddJadval1(User user, OutputStream outputStream, ReportQueryTypeUbddJadval1 params) throws Exception {
        SqlSupplier<ReportContextT4> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportUbddJadval1
                : reportSqlService::getSqlForReportUbddJadval1ByRegion;

        getReportType4(
                REPORT_UBDD_JADVAL1,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportByUbddJadval2(User user, OutputStream outputStream, ReportQueryTypeUbddJadval1 params) throws Exception {
        SqlSupplier<ReportContextT4> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportUbddJadval2
                : reportSqlService::getSqlForReportUbddJadval2ByRegion;

        getReportType4(
                REPORT_UBDD_JADVAL2,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportByUbddJadva3(User user, OutputStream outputStream, ReportQueryTypeUbddJadval1 params) throws Exception {
        SqlSupplier<ReportContextT4> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportUbddJadval3
                : reportSqlService::getSqlForReportUbddJadval3ByRegion;

        getReportType4(
                REPORT_UBDD_JADVAL3,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportByMib(User user, OutputStream outputStream, ReportQueryType1 params) throws Exception {
        getReportType1(
                REPORT_MIB,
                reportSqlService::getSqlForReportByMib,
                outputStream,
                user,
                params
        );
    }

    public void getReportByWantedList(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        getReportType1(
                REPORT_WANTED_LIST,
                reportSqlService::getSqlForReportWantedList,
                outputStream,
                user,
                params
        );
    }

    public void getReportByCountWanted(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportCountWanted
                : reportSqlService::getSqlForReportCountWantedByRegion;

        getReportType1(
                REPORT_COUNT_WANTED,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportVictimDamages(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForgetReportVictimDamages
                : reportSqlService::getSqlForgetReportVictimDamagesByRegion;

        getReportType1(
                REPORT_VICTIM_DAMAGES,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportCourtDecisionByRegionsAndDistricts(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportCourtDecisionByRegions
                : reportSqlService::getSqlForReportCourtDecisionByDistricts;

        getReportType1(
                COURT_DECISION_BY_REGIONS_OR_DISTRICTS,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getUserPointsByArticles(User user, OutputStream outputStream, ReportQueryType1 params) throws Exception {
        getReportType1(
                REPORT_USER_POINTS,
                reportSqlService::getSqlForReportUserPointsByArticle,
                outputStream,
                user,
                params
        );
    }

    public void getMibSentResults(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportMibSentResultsByRegions
                : reportSqlService::getSqlForMibSentResultsByDistricts;


        getReportType1(
                REPORT_MIB_SENT_RESULTS,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getMibDeclinedResults(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportMibDeclinedResultsByRegions
                : reportSqlService::getSqlForMibDeclinedResultsByDistricts;


        getReportType1(
                REPORT_MIB_DECLINED_RESULTS,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    public void getReportViolationsOfUseNaturalResources(User user, OutputStream outputStream, ReportQueryType2 params) throws Exception {
        SqlSupplier<ReportContextT1> sqlbuilder = params.getRegion() == null
                ? reportSqlService::getSqlForReportViolationsOfUseNaturalResources
                : reportSqlService::getSqlForViolationsOfUseNaturalResourcesByRegion;

        getReportType1(
                REPORT_BY_VIOLATIONS_OF_USAGE_NATURAL_RESOURCES,
                sqlbuilder,
                outputStream,
                user,
                params
        );
    }

    private void getReportType3(ReportAlias alias, SqlSupplier<ReportContextT3> prepareSqlbuilder, SqlSupplier<ReportContextT3> sqlbuilder, OutputStream outputStream, User user, ReportQueryType3 filters) throws Exception {
        ReportContextT3 reportContext = new ReportContextT3(user, filters);

        String prepareSql = null;
        if (prepareSqlbuilder != null) {
            prepareSql = prepareSqlbuilder.getSql(reportContext);
        }

        String sql = sqlbuilder.getSql(reportContext);
        ReportParams params = new ReportParams(reportContext.getUser())
                .fromDate(reportContext.getFromDate())
                .toDate(reportContext.getToDate())
                .organs(reportContext.getOrgans())
                .departments(reportContext.getDepartments())
                .regions(reportContext.getRegions())
                .districts(reportContext.getDistricts())
                .articleParts(reportContext.getArticleParts());

        makeReport(outputStream, alias, prepareSql, sql, params, filters.getFormat());
    }

    private void getReportType4(ReportAlias alias, SqlSupplier<ReportContextT4> sqlbuilder, OutputStream outputStream, User user, ReportQueryType4 filters) throws Exception {
        ReportContextT4 reportContext = new ReportContextT4(user, filters);

        String sql = sqlbuilder.getSql(reportContext);
        ReportParams params = new ReportParams(reportContext.getUser())
                .fromDate(reportContext.getFromDate())
                .toDate(reportContext.getToDate())
                .organs(reportContext.getOrgans())
                .departments(reportContext.getDepartments())
                .regions(reportContext.getRegions())
                .districts(reportContext.getDistricts())
                .articleParts(reportContext.getArticleParts())
                .articleViolationTypes(reportContext.getArticleViolationTypes());


        makeReport(outputStream, alias, null, sql, params, filters.getFormat());
    }

    private void getReportType1(ReportAlias alias, SqlSupplier<ReportContextT1> sqlbuilder, OutputStream outputStream, User user, ReportQueryType1 filters) throws Exception {
        ReportContextT1 reportContext = new ReportContextT1(user, filters);

        String sql = sqlbuilder.getSql(reportContext);
        ReportParams params = new ReportParams(reportContext.getUser())
                .fromDate(reportContext.getFromDate())
                .toDate(reportContext.getToDate())
                .organs(reportContext.getOrgans())
                .departments(reportContext.getDepartments())
                .regions(reportContext.getRegions())
                .districts(reportContext.getDistricts())
                .articleParts(reportContext.getArticleParts());

        makeReport(outputStream, alias, null, sql, params, filters.getFormat());
    }

    private void makeReport(OutputStream outputStream, ReportAlias reportName, String preparedSql, String sql, ReportParams params, ReportFormat outputFormat) throws Exception {

        if (ReportFormat.SQL.equals(outputFormat)) {
            try (outputStream; PrintStream printStream = new PrintStream(outputStream)) {
                printStream.print(sql);
                if (preparedSql != null) {
                    printStream.print(";\n\n--Prepared Sql\n");
                    printStream.print(preparedSql);
                    printStream.print(";");
                }
            }

            return;
        }

        ReportFormat format = Optional.ofNullable(outputFormat).orElse(ReportFormat.PDF);

        InputStream reportTemplateSource = getTemplateFile(reportName);

        dbHelper.doWithConnection(connection -> {

            try {

                if (preparedSql != null) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(preparedSql)) {
                        preparedStatement.execute();
                    }
                }

                try (
                        PreparedStatement statement = connection.prepareStatement(sql);
                        ResultSet resultSet = statement.executeQuery()
                ) {
                    JRResultSetDataSource dataSource = new JRResultSetDataSource(resultSet);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(reportTemplateSource, params.getParamsMap(), dataSource);
                    exportReport(jasperPrint, outputStream, format);
                }

            } catch (Exception e) {
                log.error("Report error", e);
                throw new ValidationException(ErrorCode.REPORT_ERROR, e.getMessage());
            }

            return null;
        });
    }

    static private void exportReport(JasperPrint jasperPrint, OutputStream outputStream, ReportFormat format) throws JRException {
        JRAbstractExporter exporter;

        switch (format) {
            case PDF: {
                exporter = new JRPdfExporter();
                break;
            }
            case EXCEL: {
                exporter = new JRXlsxExporter();
                break;
            }
            default:
                throw new NotImplementedException("Report output format");
        }

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
    }

    private InputStream getTemplateFile(ReportAlias reportName) throws Exception {

//        return dbHelper.doWithConnection(connection -> {
//            InputStream inputStream = null;
//
//            try (
//                    PreparedStatement stat = connection.prepareStatement("select file from core_v0.report_template where alias = '" + reportName.name() + "'");
//                    ResultSet rs = stat.executeQuery();
//            ) {
//                if (rs != null) {
//                    while (rs.next()) {
//                        byte[] imgBytes = rs.getBytes(1);
//                        inputStream = new ByteArrayInputStream(imgBytes);
//                    }
//                }
//
//                if (inputStream == null) {
//                    throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.REPORT_FILE_NOT_FOUND);
//                }
//
//                return inputStream;
//
//            } catch (SQLException throwables) {
//                throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.REPORT_FILE_NOT_FOUND);
//            }
//
//        });

        return reportRepository.findByAlias(reportName)
                .map(ReportTemplate::getFile)
                .map(ByteArrayInputStream::new)
                .orElseThrow(() -> new EntityByAliasNotPresent(ReportTemplate.class, reportName.name()));
    }


}
