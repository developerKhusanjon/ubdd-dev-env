package uz.ciasev.ubdd_service.service.report;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.report.ReportQuery;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class ReportContext {

    private final User user;

    private final java.time.LocalDate fromDate;

    private final LocalDate toDate;

    public ReportContext(User user, ReportQuery params) {
        this.user = user;
//                переписать на месяц
        this.fromDate = params.getFromDate() == null ? LocalDate.now() : params.getFromDate();
        this.toDate = params.getToDate() == null ? LocalDate.now() : params.getToDate();
    }

    protected <T> List<T> normalizeInput(Collection<T> list) {
        if (list == null)
            return null;

        List<T> filteredList = list.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            return null;
        }

        return filteredList;
    }

}
