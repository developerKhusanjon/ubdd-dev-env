package uz.ciasev.ubdd_service.service.violation_event.annulment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.violation_event.ViolationEventAnnulmentRequestDTO;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventAnnulment;
import uz.ciasev.ubdd_service.repository.violation_event.ViolationEventAnnulmentRepository;

@Service
@RequiredArgsConstructor
public class ViolationEventAnnulmentServiceImpl implements ViolationEventAnnulmentService {

    private final ViolationEventAnnulmentRepository repository;

    @Override
    public ViolationEventAnnulment create(ViolationEventAnnulmentRequestDTO requestDTO) {
        ViolationEventAnnulment annulment =  new ViolationEventAnnulment(
                requestDTO.getReason(),
                requestDTO.getComment(),
                requestDTO.getDocumentBaseUri()
        );
        repository.save(annulment);
        return annulment;
    }
}
