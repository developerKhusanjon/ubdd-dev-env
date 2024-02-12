package uz.ciasev.ubdd_service.service.resolution.punishment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.DebtPunishmentSearchDTO;
import uz.ciasev.ubdd_service.utils.validator.Inspector;


@Validated
public interface DebtPunishmentSearchService {

    Page<DebtPunishmentSearchDTO> findByPinpp(@Inspector User user, String pinpp, Pageable pageable);

    Page<DebtPunishmentSearchDTO> findByCarNumber(@Inspector User user, String carNumber, Pageable pageable);

    Iterable<DebtPunishmentSearchDTO> findByCarNumberAndPinpp(@Inspector User user, String pinpp, String carNumber, Pageable pageable);
}
