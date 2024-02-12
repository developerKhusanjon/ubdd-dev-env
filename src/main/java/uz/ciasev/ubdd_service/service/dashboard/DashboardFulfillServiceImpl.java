package uz.ciasev.ubdd_service.service.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.repository.dashboard.DashboardFulfillCustomRepository;
import uz.ciasev.ubdd_service.repository.dashboard.DashboardFulfillRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardFulfillServiceImpl implements DashboardFulfillService {

    private final DashboardFulfillRepository dashboardFulfillRepository;
    private final DashboardFulfillCustomRepository dashboardFulfillCustomRepository;

    @Override
    @Transactional
    public void fulfillDashboard(LocalDate from, LocalDate to) {

        int currY = LocalDate.now().getYear();
        int startY = from.getYear();
        int endY = to.getYear();

        if (startY == currY || endY == currY) {
            LocalDateTime currYearDateFrom = from(LocalDate.of(currY,1,1));
            LocalDateTime currYearDateTo = to(LocalDate.of(currY,12,31));
            fulfill(currYearDateFrom, currYearDateTo);
        }

        if (startY != currY || endY != currY) {
            LocalDateTime customDateFrom = from(LocalDate.of(startY,1,1));
            LocalDateTime customDateTo = to(LocalDate.of(endY,12,31));
            fulfillCustom(customDateFrom, customDateTo, currY);
        }
    }

    private void fulfill(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {

        dashboardFulfillRepository.protocolCount(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.admCaseCount(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.decisionCount(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.violatorCount(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.violatorCountByProtocol(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.victimCount(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.victimCountByProtocol(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.admCaseCountByStatus(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.decisionCountByStatus(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.terminationCount(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.penaltyChart(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.mibAmountByMonth(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.courtAmountByMonth(dateTimeFrom, dateTimeTo);
        dashboardFulfillRepository.protocolRepeatability(dateTimeFrom, dateTimeTo);
    }

    private void fulfillCustom(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, int currentYear) {

        dashboardFulfillCustomRepository.protocolCount(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.violatorCountByProtocol(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.victimCountByProtocol(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.admCaseCountByStatus(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.decisionCountByStatus(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.terminationCount(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.penaltyChart(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.mibAmountByMonth(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.courtAmountByMonth(dateTimeFrom, dateTimeTo, currentYear);
        dashboardFulfillCustomRepository.protocolRepeatability(dateTimeFrom, dateTimeTo, currentYear);
    }

    private LocalDateTime from(LocalDate date) {
        return date.atStartOfDay();
    }

    private LocalDateTime to(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }
}
