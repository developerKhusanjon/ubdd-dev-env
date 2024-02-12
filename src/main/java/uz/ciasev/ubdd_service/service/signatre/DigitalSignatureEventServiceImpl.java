package uz.ciasev.ubdd_service.service.signatre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureEvent;
import uz.ciasev.ubdd_service.repository.signature.DigitalSignatureEventRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DigitalSignatureEventServiceImpl implements DigitalSignatureEventService {

    private final DigitalSignatureEventRepository repository;

    @Override
    @Transactional
    public DigitalSignatureEvent save(DigitalSignatureEvent event) {

        return repository.save(event);
    }
}
