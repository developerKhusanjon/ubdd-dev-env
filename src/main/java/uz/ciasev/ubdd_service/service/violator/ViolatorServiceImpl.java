package uz.ciasev.ubdd_service.service.violator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.SingleAddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationArticleRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.violator.ViolatorDetailRepository;
import uz.ciasev.ubdd_service.repository.violator.ViolatorRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.utils.types.ArticlePairJson;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViolatorServiceImpl implements ViolatorService {

    private final ViolatorRepository violatorRepository;
    private final ViolatorDetailRepository violatorDetailRepository;
    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final AddressService addressService;

    public Violator save(Violator violator) {
        return violatorRepository.saveAndFlush(violator);
    }

    @Override
    public Violator getOrSave(Violator violator) {

        Optional<Violator> violatorOptional = violatorRepository
                .findByAdmCaseIdAndPersonId(violator.getAdmCaseId(), violator.getPersonId());

        if (violatorOptional.isPresent()) {
            return violatorOptional.get();
        }

        violator.cleanForCopySave();

        violator.setActualAddress(addressService.copyOfId(violator.getActualAddressId()));
        violator.setPostAddress(addressService.copyOfId(violator.getPostAddressId()));
        violator.setNotificationViaMail(false);
        violator.setNotificationViaSms(false);

        return violatorRepository.saveAndFlush(violator);
    }

    @Override
    public List<Violator> findByAdmCaseId(Long admCaseId) {
        return violatorRepository.findByAdmCaseId(admCaseId);
    }

    @Override
    public Long findByAdmCaseIdAndPersonId(Long admCaseId, Long personId) {
        return violatorRepository.findIdByAdmCaseAndPersonIds(admCaseId, personId)
                .orElseThrow(() -> new EntityByParamsNotFound(Violator.class, "personId", personId, "admCaseId", admCaseId));
    }

    @Override
    public Violator findSingleByAdmCaseId(Long admCaseId) {
        List<Violator> violators = findByAdmCaseId(admCaseId);

        if (violators.isEmpty())
            throw new AdmCaseNotContainViolatorsException();

        if (violators.size() > 1)
            throw new AdmCaseNotSingleViolatorException();

        return violators.get(0);
    }

    @Override
    public Violator getById(Long id) {
        return violatorRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Violator.class, id));
    }

    @Override
    public void saveAll(List<Violator> violators) {
        violatorRepository.saveAll(violators);
    }

    @Override
    public List<Violator> findViolatorsByIds(List<Long> ids) {
        return violatorRepository.findAllById(ids);
    }

    @Override
    public void delete(Violator violator) {
        violatorRepository.delete(violator);
    }

    @Override
    @Transactional
    public Violator mergeTo(Violator violator, AdmCase toAdmCase) {
        Violator newViolator = getOrSave(violator.toBuilder().admCase(toAdmCase).build());
        violatorDetailRepository.mergeAllTo(violator.getId(), newViolator.getId());
        violatorRepository.mergedTo(violator.getId(), newViolator.getId());
        return newViolator;
    }

    @Override
    @DigitalSignatureCheck(event = SignatureEvent.VIOLATOR_EDIT)
    public Violator updateViolator(User user, Long id, ViolatorRequestDTO requestDTO) {
        return checkAndDo(
                user,
                ActionAlias.EDIT_VIOLATOR_DETAIL,
                id,
                violator -> {
                    requestDTO.applyTo(violator);
                    violator.setEarlierViolatedArticleParts(requestDTO.buildEarlierViolatedArticleParts());
                    violatorRepository.save(requestDTO.applyTo(violator));
                }
        );
    }

    @Override
    @DigitalSignatureCheck(event = SignatureEvent.VIOLATOR_EDIT)
    public Violator updateViolatorPostAddress(User user, Long id, SingleAddressRequestDTO requestDTO) {
        return checkAndDo(
                user,
                ActionAlias.EDIT_VIOLATOR_DETAIL,
                id,
                violator -> {
                    if (!violator.getPerson().isRealPinpp()) {
                        throw new ValidationException(ErrorCode.NOT_GCP_PERSON_BIRTH_ADDRESS_EDIT_PERMITTED);
                    }
                    addressService.update(violator.getPostAddressId(), requestDTO);
                }
        );
    }

    @Override
    @DigitalSignatureCheck(event = SignatureEvent.VIOLATOR_EDIT)
    public Violator updateViolatorActualAddress(User user, Long id, SingleAddressRequestDTO requestDTO) {
        return checkAndDo(
                user,
                ActionAlias.EDIT_VIOLATOR_DETAIL,
                id,
                violator -> addressService.update(violator.getActualAddressId(), requestDTO)
        );
    }

    @Override
    @DigitalSignatureCheck(event = SignatureEvent.VIOLATOR_EDIT)
    public Violator updateViolatorBirthAddress(User user, Long id, SingleAddressRequestDTO requestDTO) {
        return checkAndDo(
                user,
                ActionAlias.EDIT_VIOLATOR_DETAIL,
                id,
                violator -> addressService.update(violator.getPerson().getBirthAddressId(), requestDTO)
        );
    }

    private Violator checkAndDo(User user, ActionAlias action, Long violatorId, Consumer<Violator> consumer) {
        Violator violator = getById(violatorId);
        AdmCase admCase = admCaseService.getById(violator.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, action, admCase);
        consumer.accept(violator);
        return violator;
    }

    @Override
    public void updateEarlierViolatedArticleParts(User user, Long id, List<QualificationArticleRequestDTO> requestDTO) {
        if (requestDTO == null) {
            throw new ValidationException(ErrorCode.REQUIRED_PARAMS_MISSING);
        }

        List<ArticlePairJson> articles = requestDTO.stream().map(QualificationArticleRequestDTO::buildArticlePairJson).collect(Collectors.toList());

        checkAndDo(
                user,
                ActionAlias.EDIT_VIOLATOR_DETAIL,
                id,
                violator -> {
                    violator.setEarlierViolatedArticleParts(articles);
                    violatorRepository.save(violator);
                }
        );
    }

    @Override
    @Transactional
    public void setViolatorInn(Violator violator, String inn) {
        violatorRepository.setInn(violator.getId(), inn);
    }

    @Override
    @Transactional
    public void setViolatorPhoto(Violator violator, String photoUri) {
        violatorRepository.setPhotoUri(violator.getId(), photoUri);
    }
}