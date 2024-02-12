package uz.ciasev.ubdd_service.service.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.utils.ContentType;

@AllArgsConstructor
@Getter
public enum ReportFormat {

    PDF(ContentType.PDF, "pdf"), EXCEL(ContentType.EXCEL, "xlsx"), SQL(ContentType.TEXT, "sql");

    private final ContentType contentType;
    private final String extantion;
}
