package uz.ciasev.ubdd_service.service.dashboard;

import java.time.LocalDate;

public interface DashboardFulfillService {

    void fulfillDashboard(LocalDate from, LocalDate to);
}
