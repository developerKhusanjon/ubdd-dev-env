package uz.ciasev.ubdd_service.service.search.mib;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Map;

public interface MibSearchService {

    // Список решений, по каторым можно отправить смс с оповещением Об отправке в МИБ
    List<Long> findAllDecisionForMibPreSend();
    Map<Long, String> findAllDecisionByProtocolNumbers();

    // Список решений, каторые надо отправить в МИБ в автоматическом режиме
    Page<Long> getDecisionsForAutoSendToMib(Pageable pageable);

    List<Long> findUserPenaltyDecisionForMibIds(User user, Map<String, String> filters, Pageable pageable);

    // Список штрафов, каторые по сроку уже можно отпраивть в миб
    List<PenaltyForSendToMibResponseDTO> findUserPenaltyDecisionForMibList(User user, Map<String, String> filters, Pageable pageable);
}
