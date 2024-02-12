package uz.ciasev.ubdd_service.service.report;

@FunctionalInterface
interface SqlSupplier<T extends ReportContext> {
    String getSql(T context);
}
