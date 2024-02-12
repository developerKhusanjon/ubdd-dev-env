package uz.ciasev.ubdd_service.service.resolution.punishment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.resolution.PunishmentDocumentResponseDTO;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PunishmentDocument;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ResolutionNotActiveException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PunishmentDocumentRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PunishmentDocumentServiceImpl implements PunishmentDocumentService {

    private final PunishmentDocumentRepository repository;
    private final PunishmentService punishmentService;
    private final AdmCaseAccessService admCaseAccessService;
    private final ResolutionService resolutionService;

    @Override
    public PunishmentDocument findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(PunishmentDocument.class, id));
    }

    @Override
    @Transactional
    public void save(User user, Long punishmentId, PunishmentDocumentRequestDTO dto) {

        checkAccess(user, punishmentId);

        Punishment punishment = punishmentService.getById(punishmentId);

        PunishmentDocument doc = dto.buildDocument();
        doc.setPunishment(punishment);
        doc.setUser(user);
        repository.save(doc);
    }

    @Override
    @Transactional
    public void update(User user, Long id, PunishmentDocumentRequestDTO dto) {

        PunishmentDocument doc = findById(id);

        checkAccess(user, doc.getPunishment().getId());

        dto.applyTo(doc);
        repository.save(doc);
    }

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.ADM_CASE_DOCUMENT_DELETE)
    public PunishmentDocument delete(User user, Long id) {

        PunishmentDocument doc = findById(id);

        checkAccess(user, doc.getPunishment().getId());

        repository.deleteById(id);

        return doc;
    }

    @Override
    public List<PunishmentDocument> findByPunishmentId(User user, Long id) {

//        checkAccess(user, id);

        return repository.findAllByPunishmentId(id);
    }

    @Override
    public List<PunishmentDocumentResponseDTO> findDTOByPunishmentId(User user, Long id) {

        return findByPunishmentId(user, id).stream()
                .map(PunishmentDocumentResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void checkAccess(User user, Long punishmentId) {

        Resolution resolution = resolutionService.findByPunishmentId(punishmentId);

        if (!resolution.isActive()) {
            throw new ResolutionNotActiveException();
        }
        admCaseAccessService.checkAccessOnAdmCaseNoOrgan(user, resolution.getAdmCase());
    }
}
