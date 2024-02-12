package uz.ciasev.ubdd_service.service.history;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.history.ViolatorRebindRegistrationResponseDTO;
import uz.ciasev.ubdd_service.entity.history.ViolatorRebindRegistration;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.history.HistoryViewRepository;
import uz.ciasev.ubdd_service.specifications.ViolatorRebindHistorySpecification;

@Service
@RequiredArgsConstructor
public class HistoryViewServiceImpl implements HistoryViewService {
    private final HistoryViewRepository repository;
    private final ViolatorRebindHistorySpecification specification;

    @Override
    public Page<ViolatorRebindRegistrationResponseDTO> findAllViolatorRebindRegistration(User user, Pageable pageable, Long protocolId) {
        return repository.findAllPageableBySpecification(
                ViolatorRebindRegistration.class,
                specification.withProtocolId(protocolId),
                pageable).map(ViolatorRebindRegistrationResponseDTO::new);
    }
}
