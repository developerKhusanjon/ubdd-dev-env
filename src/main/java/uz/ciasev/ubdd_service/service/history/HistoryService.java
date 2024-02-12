package uz.ciasev.ubdd_service.service.history;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.ProtocolVehicleNumberEditDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDataBind;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.history.*;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
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
import uz.ciasev.ubdd_service.service.execution.BillingEntity;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HistoryService {

    void registerAdmCaseEvent(AdmCaseRegistrationType eventType,
                              AdmCase fromAdmCase,
                              AdmCase toAdmCase,
                              List<Protocol> protocols);

    void registerViolatorRebind(User user, List<Protocol> protocols, Person newPerson, PersonDocument newDocument);

    void registerProtocolQualification(Protocol protocol, QualificationRequestDTO newQualification, QualificationRegistrationType type);

    void registerEditProtocolQualification(User user, Protocol protocol, Optional<Juridic> oldJuridic, List<Long> oldRepetability, QualificationRequestDTO newQualification);

    void registerEditProtocolMainArticle(User user, Protocol protocol, ProtocolArticle protocolArticle);

    void registerAdmCaseEvent(AdmCaseRegistrationType eventType, AdmCase admCase, AdmStatusAlias fromStatus);

    void registerUserEditing(String action, User oldUser, User newUser, @Nullable Collection<Role> roles);

    void registerDigitalSignatureEvent(DigitalSignatureRegistrationAction eventType, DigitalSignatureCertificate certificate, Map<String, String> detail);

    void registerProtocolVehicleNumberEdit(User user, Long protocolId, String oldVehicleNumber, ProtocolVehicleNumberEditDTO dto);

    void registerUbddDataBind(User user, Long protocolId, UbddDataToProtocolBind bind, UbddDataBind dto);

    void registerMibCardDocumentRemove(MibCardDocument document);

    void manualSetOfClimeId(Long caseId, Long climeId);

    void registerDictionaryAdminAction(AbstractDict entity, DictAdminHistoricAction action, Map<String, Object> detail);

    void registerTransDictionaryAdminAction(AbstractTransEntity entity, TransDictAdminHistoricAction action, Map<String, Object> detail);

    void registerBrvUpdate(Brv brv);

    void deleteManualExecution(BillingEntity entity, ManualExecutionDeleteRegistrationType type);

    OrganAccountSettingsActionRegistration registerOrganAccountSettingsAction(User user, OrganAccountSettingsHistoricAction action, @Nullable OrganAccountSettingsUpdateRequestDTO requestDTO);
}
