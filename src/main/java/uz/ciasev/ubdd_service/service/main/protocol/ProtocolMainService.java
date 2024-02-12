package uz.ciasev.ubdd_service.service.main.protocol;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.*;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.EditProtocolVehicleNumberRegistrationResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.ProtocolVehicleNumberEditDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

import java.util.List;

@Validated
public interface ProtocolMainService {

    VictimDetail addVictimToProtocol(User user, Long protocolId, VictimProtocolRequestDTO victimRequestDTO);

    ParticipantDetail addParticipantToProtocol(User user, Long protocolId, ParticipantProtocolRequestDTO participantRequestDTO);

    VictimDetail deleteVictimDetailFromProtocol(User user, Long victimId, Long protocolId, ChangeReasonRequestDTO changeReason);

    void deleteParticipantFromProtocol(User user, Long participantId, Long protocolId, ChangeReasonRequestDTO changeReason);

    void makeProtocolMain(User user, Long protocolId);

    Protocol editProtocolQualification(@Inspector User user, Long protocolId, QualificationRequestDTO reQualificationRequestDTO);

    @Transactional
    void editProtocolMainArticle(User user, Long protocolId, Long protocolArticleId);

    AdmCase separateProtocol(@Inspector User user, Long protocolId);

    void editProtocolViolationTime(User user, Long protocolId, ProtocolEditRequestDTO requestDTO);

    void setTrackNumber(User user, Long protocolId, String registrationNumber);

    void editPaperProps(User user, Long protocolId, PaperPropsRequestDTO requestDTO);

    void protocolVehicleNumberEdit(User user, Long protocolId, ProtocolVehicleNumberEditDTO dto);

    List<EditProtocolVehicleNumberRegistrationResponseDTO> findProtocolVehicleNumberHistory(User user, Long protocolId);
}
