package uz.ciasev.ubdd_service.service.dashboard;

import uz.ciasev.ubdd_service.dto.internal.response.dasboard.DashboardDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dasboard.DashboardFilterDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDate;

public interface DashboardService {

    DashboardDTO buildDTO(User user, LocalDate dateFrom, LocalDate dateTo, DashboardFilterDTO filter);
}
