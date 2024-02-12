package uz.ciasev.ubdd_service.service.main.protocol;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.util.Pair;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.ciasev.ubdd_service.dto.internal.request.protocol.*;
//import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
//import uz.ciasev.ubdd_service.entity.protocol.Juridic;
//import uz.ciasev.ubdd_service.entity.Person;
//import uz.ciasev.ubdd_service.entity.PersonDocument;
//import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
//import uz.ciasev.ubdd_service.entity.protocol.*;
//import uz.ciasev.ubdd_service.entity.user.User;
//import uz.ciasev.ubdd_service.entity.violator.Violator;
//import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
//import uz.ciasev.ubdd_service.event.AdmEventService;
//import uz.ciasev.ubdd_service.event.AdmEventType;
//import uz.ciasev.ubdd_service.service.main.protocol.ProtocolCreateAdditionalValidationService;
//import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
//import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
//import uz.ciasev.ubdd_service.service.damage.DamageMainService;
//import uz.ciasev.ubdd_service.service.juridic.JuridicService;
//import uz.ciasev.ubdd_service.service.main.ActorService;
//import uz.ciasev.ubdd_service.service.main.PersonDataService;
//import uz.ciasev.ubdd_service.service.protocol.*;
//import uz.ciasev.ubdd_service.service.validation.ProtocolValidationService;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
public class ProtocolCreateServiceImpl {}
//
//    private final ProtocolValidationService protocolValidationService;
//    private final AdmCaseService admCaseService;
//    private final JuridicService juridicService;
//    private final ProtocolService protocolService;
//    private final RepeatabilityService protocolRepeatabilityService;
//    private final DamageMainService damageMainService;
//    private final ActorService newActorService;
//    private final PersonDataService personDataService;
//    private final ProtocolRepository protocolRepository;
//    private final AdmEventService notificatorService;
//    private final ProtocolCreateAdditionalValidationService protocolCreateAdditionalValidationService;
//    private final ProtocolAdditionalService additionalService;
//
//    @Override
////    @Transactional(timeout = 60)
//    @Transactional
//    public Protocol createProtocol(User user, ProtocolRequestDTO protocolDTO) {
//
//        // EXTERNAL ID WORKAROUND
//        protocolCreateAdditionalValidationService.validateExternalId(protocolDTO);
//
//        Protocol protocolByExternalId = findByExternalId(user, protocolDTO);
//        if (protocolByExternalId != null) {
//            return protocolByExternalId;
//        }
//
//        protocolValidationService.validateRegistrationTime(protocolDTO);
//
//        //  Валидация протокола по данным инспектора
//        protocolValidationService.validateProtocolByUser(user, protocolDTO);
//
//        //  Получение личных данных нарушителя
//        ViolatorCreateRequestDTO violatorRequestDTO = protocolDTO.getViolator();
//        Pair<Person, ? extends PersonDocument> personWithDocument = personDataService.provideByPinppOrManualDocument(violatorRequestDTO);
//        Person person = personWithDocument.getFirst();
//        protocolValidationService.validateViolatorByProtocol(protocolDTO, person);
//
//        protocolValidationService.validateUniqueness(user, person,
//                protocolDTO.getArticlePart(), protocolDTO.getArticleViolationType());
//
//        //  Создание административного дела
//        AdmCase admCase = admCaseService.createEmptyAdmCase(user);
//
//        //  Создание нарушителя
//        Pair<Violator, ViolatorDetail> violatorWithDetail = newActorService.createViolatorWithDetail(user, personWithDocument, admCase, violatorRequestDTO);
//        ViolatorDetail violatorDetail = violatorWithDetail.getSecond();
//
//        //  Создание данных должностного лица
//        Optional<Juridic> juridic = Optional.ofNullable(protocolDTO.getJuridic()).map(j -> juridicService.create(user, j));
//
//        //  Создание протокола
//        List<ProtocolArticle> additionArticles = protocolDTO.getAdditionArticles().stream().map(QualificationArticleRequestDTO::buildProtocolArticle).collect(Collectors.toList());
//        Protocol protocol = protocolDTO.buildProtocol();
//        protocol.setJuridic(juridic.orElse(null));
//        Protocol savedProtocol = protocolService.create(user, violatorDetail, protocol, additionArticles);
//
//        //  Сохранение дополнительных данных в протоколе
//        saveRelated(user, savedProtocol, violatorWithDetail.getFirst(), protocolDTO);
//
//        notificatorService.fireEvent(AdmEventType.PROTOCOL_CREATE, savedProtocol);
//
//        return savedProtocol;
//    }
//
//    private void saveRelated(User user, Protocol protocol, Violator violator, ProtocolRequestDTO protocolDTO) {
//        protocolRepeatabilityService.create(user, protocol, protocolDTO.getRepeatabilityProtocolsId());
//        addGovDamage(user, violator, protocol, protocolDTO);
//        additionalService.createProtocolAdditional(user, protocol, protocolDTO.getAdditional());
//    }
//
//    private void addGovDamage(User user,
//                              Violator violator,
//                              Protocol protocol,
//                              ProtocolRequestDTO protocolRequestDTO) {
//        Long amount = protocolRequestDTO.getGovernmentDamageAmount();
//        if (Objects.nonNull(amount)) {
//            damageMainService.addGovernmentDamageToProtocol(user, protocol, violator, amount);
//        }
//    }
//
//    private Protocol findByExternalId(User user, ProtocolRequestDTO protocolDTO) {
//        if (protocolDTO.getExternalId() == null) {
//            return null;
//        }
//        return protocolRepository.findByExternalIdAndOrganId(protocolDTO.getExternalId(), user.getOrganId()).orElse(null);
//    }
//}