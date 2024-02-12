package uz.ciasev.ubdd_service.dto.internal.request.report;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import uz.ciasev.ubdd_service.service.report.ReportFormat;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class ReportQuery {

    private ReportFormat format;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    public ReportFormat getFormat() {
        return Optional.ofNullable(format).orElse(ReportFormat.PDF);
    }

    public String getFileName() {
        return ("report." + getFormat().getExtantion()).toLowerCase();
    }
}
