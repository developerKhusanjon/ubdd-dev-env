package uz.ciasev.ubdd_service.service.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.response.history.ViolatorRebindRegistrationResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

public interface HistoryViewService {

    Page<ViolatorRebindRegistrationResponseDTO> findAllViolatorRebindRegistration(User user, Pageable pageable, Long protocolId);
}
