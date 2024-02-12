package uz.ciasev.ubdd_service.service.validation;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.ArticleRequest;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.*;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimRequestDTO;
import uz.ciasev.ubdd_service.entity.AdmProtocol;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolDates;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Optional;

@Validated
public interface ProtocolValidationService {

    void validateViolatorByProtocol(AdmProtocol protocol, Person violator);

    void validateProtocolByUser(User user, ProtocolRequestDTO protocolDTO);

    void validateRegionDistrictByUser(User user, RegionDistrictRequest request);

    void validateRaidRegionByUser(User user, RegionDistrictRequest regionDistrictRequest);

    void validateQualificationByUser(@Inspector User user, QualificationRequestDTO protocolDTO);

    void validateParticipantByProtocol(Protocol protocol, Person person, @Nullable ParticipantProtocolRequestDTO participantRequestDTO);

    void validateVictimByProtocol(Protocol protocol, Person victim, @Nullable VictimRequestDTO victimRequestDTO);

    boolean validateJuridic(QualificationRequestDTO protocol);

    Optional<String> checkArticleWithParticipantType(ArticlePart articlePart, Boolean isJuridic);

    boolean validateFirstDateLessThenSecond(LocalDateTime violationTime, LocalDateTime registrationTime);

    void validateVictimHasNoDamage(Long protocolId, Long victimId);

    void checkProtocolDates(ProtocolDates requestDTO);

    void validatePaperProtocol(User inspector, PaperProtocolRequestDTO requestDTO);

    void checkBlankSeriesAndNumberByOrgan(Organ organ, PaperPropsRequestDTO requestDTO, Optional<Protocol> editedProtocol);

    void validateMainArticle(User user, Protocol protocol, ProtocolArticle protocolArticle);

    Optional<String> validateArticleViolationTypePresence(ArticleRequest requestDTO);
}
