package uz.ciasev.ubdd_service.service.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.JuridicRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.ProtocolVehicleNumberEditDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataBind;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.history.*;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.settings.Brv;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.repository.history.OrganAccountSettingsActionRegistrationRepository;
import uz.ciasev.ubdd_service.service.execution.BillingEntity;
import uz.ciasev.ubdd_service.service.user.SystemUserService;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final SystemUserService userService;

    @PersistenceContext
    private final EntityManager entityManager;

    private final OrganAccountSettingsActionRegistrationRepository organAccountSettingsActionRepository;

    @Override
    public void registerAdmCaseEvent(AdmCaseRegistrationType eventType,
                                     AdmCase fromAdmCase,
                                     AdmCase toAdmCase,
                                     List<Protocol> protocols) {
        User user = userService.getCurrentUser();

        for (Protocol protocol : protocols) {
            saveRegistration(user, () -> {
                return AdmCaseMergeSeparationRegistration.builder()
                        .type(eventType)
                        .fromAdmCaseId(fromAdmCase.getId())
                        .toAdmCaseId(toAdmCase.getId())
                        .protocolId(protocol == null ? null : protocol.getId())
                        .build();
            });
        }
    }

    @Override
    public void registerViolatorRebind(User user, List<Protocol> protocols, Person newPerson, PersonDocument newDocument) {
        for (Protocol protocol : protocols) {
            ViolatorDetail vd = protocol.getViolatorDetail();
            saveRegistration(user, () -> {
                return ViolatorRebindRegistration.builder()
                        .protocolId(protocol.getId())
                        .fromPinpp(vd.getViolator().getPerson().getPinpp())
                        .toPinpp(newPerson.getPinpp())
                        .fromDocument(vd.getPersonDocumentType().getDefaultName() + " " + vd.getDocumentSeries() + vd.getDocumentNumber() + "/" + vd.getDocumentGivenDate())
                        .toDocument(newDocument.getPersonDocumentType().getDefaultName() + " " + newDocument.getSeries() + newDocument.getNumber() + "/" + newDocument.getGivenDate())
                        .build();
            });
        }
    }

    @Override
    public void registerEditProtocolQualification(User user, Protocol protocol, Optional<Juridic> oldJuridic, List<Long> oldRepetability, QualificationRequestDTO newQualification) {
        saveRegistration(user, () -> {
            return EditProtocolQualificationRegistration.builder()
                    .action("editQualification")
                    .protocolId(protocol.getId())
                    .fromJuridic(protocol.getIsJuridic())
                    .toJuridic(newQualification.getIsJuridic())
                    .fromArticlePartId(protocol.getArticlePartId())
                    .toArticlePartId(newQualification.getArticlePart().getId())
                    .fromArticleViolationTypeId(protocol.getArticleViolationTypeId())
                    .toArticleViolationTypeId(
                            Optional.ofNullable(newQualification.getArticleViolationType()).map(ArticleViolationType::getId).orElse(null)
                    )
                    .fromInn(oldJuridic.map(Juridic::getInn).orElse(null))
                    .toInn(Optional.ofNullable(newQualification.getJuridic()).map(JuridicRequestDTO::getInn).orElse(null))
                    .fromRepeatabilityProtocolsId(oldRepetability)
                    .toRepeatabilityProtocolsId(newQualification.getRepeatabilityProtocolsId())
                    .build();
        });
    }

    @Override
    public void registerProtocolQualification(Protocol protocol, QualificationRequestDTO newQualification, QualificationRegistrationType type) {

        User user = userService.getCurrentUser();

            saveRegistration(user, () -> EditProtocolQualificationRegistration.builder()
                    .action(type.name())
                .protocolId(protocol.getId())
                .toJuridic(newQualification.getIsJuridic())
                .toArticlePartId(newQualification.getArticlePart().getId())
                .toArticleViolationTypeId(Optional.ofNullable(newQualification.getArticleViolationType()).map(ArticleViolationType::getId).orElse(null))
                .toAdditionArticles(ConvertUtils.protocolArticleDTOToJson(newQualification.getAdditionArticles()))
                .toInn(Optional.ofNullable(newQualification.getJuridic()).map(JuridicRequestDTO::getInn).orElse(null))
                .toRepeatabilityProtocolsId(newQualification.getRepeatabilityProtocolsId())
                .build());
    }

    @Override
    public void registerEditProtocolMainArticle(User user, Protocol protocol, ProtocolArticle protocolArticle) {
        saveRegistration(user, () -> {
            return EditProtocolQualificationRegistration.builder()
                    .action(QualificationRegistrationType.CHECK_MAIN_ARTICLE.name())
                    .protocolId(protocol.getId())
                    .toArticlePartId(protocolArticle.getArticlePartId())
                    .toArticleViolationTypeId(protocolArticle.getArticleViolationTypeId())
                    .build();
        });
    }

    private void saveRegistration(User user, Supplier<Registration> supplier) {
        Registration registration = supplier.get();

        registration.setUserId(user == null ? null : user.getId());
        registration.setCreatedTime(LocalDateTime.now());

        entityManager.persist(registration);
    }

    @Override
    public void registerAdmCaseEvent(AdmCaseRegistrationType eventType, AdmCase admCase, AdmStatusAlias fromStatus) {

        User user = userService.getCurrentUser();

        saveRegistration(user, () -> {
            return AdmCaseConsiderTransferRegistration.builder()
                    .type(eventType)
                    .admCaseId(admCase.getId())
                    .fromStatus(fromStatus)
                    .status(admCase.getStatus().getAlias())
                    .considerUserId(admCase.getConsiderUserId())
                    .regionId(admCase.getRegionId())
                    .districtId(admCase.getDistrictId())
                    .organId(admCase.getOrganId())
                    .departmentId(admCase.getDepartmentId())
                    .build();
        });
    }

    @Override
    public void registerUserEditing(String action, User oldUser, User newUser, Collection<Role> roles) {

        User user = userService.getCurrentUser();
        List<Long> mappedRoles = Optional.ofNullable(roles)
                .map(roles1 -> roles1
                        .stream()
                        .map(Role::getId)
                        .collect(Collectors.toList()))
                .orElse(null);

        saveRegistration(user, () -> {
            UserEditRegistration.UserEditRegistrationBuilder registration = UserEditRegistration.builder()
                    .action(action)
                    .onUserId(newUser.getId())
                    .toUsername(newUser.getUsername())
                    .toPerson(newUser.getPersonId())
                    .toOrganId(newUser.getOrganId())
                    .toRegionId(newUser.getRegionId())
                    .toDistrictId(newUser.getDistrictId())
                    .toDepartmentId(newUser.getDepartmentId())
                    .toStatusId(newUser.getStatusId())
                    .toIsConsider(newUser.isConsider())
                    .toIsActive(newUser.isActive())
                    .roles(mappedRoles);


            if (oldUser != null) {
                registration.fromUsername(oldUser.getUsername())
                        .fromPerson(oldUser.getPersonId())
                        .fromOrganId(oldUser.getOrganId())
                        .fromRegionId(oldUser.getRegionId())
                        .fromDistrictId(oldUser.getDistrictId())
                        .fromDepartmentId(oldUser.getDepartmentId())
                        .fromStatusId(oldUser.getStatusId())
                        .fromIsConsider(oldUser.isConsider())
                        .fromIsActive(oldUser.isActive());
            }

            return registration.build();
        });
    }

    @Override
    public void registerDigitalSignatureEvent(DigitalSignatureRegistrationAction action, DigitalSignatureCertificate certificate, Map<String, String> detail) {

        User user = userService.getCurrentUser();

        saveRegistration(user, () -> {
            return DigitalSignatureRegistration.builder()
                    .action(action.name())
                    .certificateSerial(certificate.getSerial())
                    .certificateId(certificate.getId())
                    .forUserId(certificate.getUserId())
                    .details(detail)
                    .build();
        });
    }

    @Override
    public void registerProtocolVehicleNumberEdit(User user, Long protocolId, String oldVehicleNumber, ProtocolVehicleNumberEditDTO dto) {

        saveRegistration(user, () -> EditProtocolVehicleNumberRegistration.builder()
                .protocolId(protocolId)
                .fromVehicleNumber(oldVehicleNumber)
                .toVehicleNumber(dto.getNewVehicleNumber())
                .changeReason(dto.getChangeReason())
                .build()
        );
    }

    @Override
    public void registerUbddDataBind(User user, Long protocolId, UbddDataToProtocolBind bind, UbddDataBind dto) {

        saveRegistration(user, () -> UbddDataBindRegistration.builder()
                .protocolId(protocolId)
                .fromUbddDrivingLicenseDataId(bind.getUbddDrivingLicenseDataId())
                .fromUbddTexPassDataId(bind.getUbddTexPassDataId())
                .fromUbddTintingDataId(bind.getUbddTintingDataId())
                .fromUbddInsuranceDataId(bind.getUbddInsuranceDataId())
                .fromVehicleArrestId(bind.getVehicleArrestId())
                .fromUbddAttorneyLetterDataId(bind.getUbddAttorneyLetterDataId())
                .toUbddDrivingLicenseDataId(dto.getUbddDrivingLicenseDataId())
                .toUbddTexPassDataId(dto.getUbddTexPassDataId())
                .toUbddTintingDataId(dto.getUbddTintingDataId())
                .toUbddInsuranceDataId(dto.getUbddInsuranceDataId())
                .toVehicleArrestId(dto.getVehicleArrestId())
                .toUbddAttorneyLetterDataId(dto.getUbddAttorneyLetterDataId())
                .build()
        );
    }

    @Override
    public void registerMibCardDocumentRemove(MibCardDocument document) {
        User user = userService.getCurrentUser();

        String info = String.format("type:%s,user:%s,path:%s", document.getDocumentTypeId(), document.getUserId(), document.getUri());

        saveRegistration(user, () -> new EntityRemovalRegistration(document, info));
    }

    @Override
    public void manualSetOfClimeId(Long caseId, Long climeId) {
        User user = userService.getCurrentUser();

        saveRegistration(user, () -> {
            return AdmCaseManualSetClimeIdRegistration.builder()
                    .admCaseId(caseId)
                    .climeId(climeId)
                    .build();
        });
    }

    @Override
    public void deleteManualExecution(BillingEntity entity, ManualExecutionDeleteRegistrationType type) {
        User user = userService.getCurrentUser();

        saveRegistration(user, () -> {
            return ManualExecutionDeleteRegistration.builder()
                    .entityId(entity.getId())
                    .entityType(entity.getEntityNameAlias())
                    .type(type)
                    .build();
        });
    }

    @Override
    public OrganAccountSettingsActionRegistration registerOrganAccountSettingsAction(User user, OrganAccountSettingsHistoricAction action, @Nullable OrganAccountSettingsUpdateRequestDTO requestDTO) {

        if (action.isHideDetail() && requestDTO != null) {
            throw new ImplementationException("requestDTO in method HistoryService.registerOrganAccountSettingsAction must be null for action with isHideDetail = true");
        }

        Map<String, Object> detail = Optional.ofNullable(requestDTO).map(dto -> dto.constructJSON()).orElse(null);

        return organAccountSettingsActionRepository.save(new OrganAccountSettingsActionRegistration(user, action, detail));
    }

    @Override
    public void registerDictionaryAdminAction(AbstractDict entity, DictAdminHistoricAction action, Map<String, Object> detail) {
        User user = userService.getCurrentUser();

        saveRegistration(user, () -> {
            return DictionaryAdminActionRegistration.builder()
                    .action(action)
                    .entityId(entity.getId())
                    .entityType(entity.getClass().getSimpleName())
                    .detail(detail)
                    .build();
        });
    }

    @Override
    public void registerTransDictionaryAdminAction(AbstractTransEntity entity, TransDictAdminHistoricAction action, Map<String, Object> detail) {
        User user = userService.getCurrentUser();

        saveRegistration(user, () -> {
            return TransDictionaryAdminActionRegistration.builder()
                    .action(action)
                    .entityId(entity.getId())
                    .entityType(entity.getClass().getSimpleName())
                    .detail(detail)
                    .build();
        });
    }

    @Override
    public void registerBrvUpdate(Brv brv) {
        User user = userService.getCurrentUser();

        saveRegistration(user, () -> {
            return BrvUpdateRegistration.builder()
                    .brvId(brv.getId())
                    .amount(brv.getAmount())
                    .build();
        });
    }
}
