package uz.ciasev.ubdd_service.service.protocol;

import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.RepeatabilityResponseDTO;
import uz.ciasev.ubdd_service.entity.protocol.Repeatability;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.RepeatabilityPdfProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface RepeatabilityService {

    List<Repeatability> replace(User user, Protocol protocol, List<Long> protocolsId);

    @Transactional
    List<Repeatability> create(User user, Decision decision, List<Long> protocolsId);

    List<RepeatabilityPdfProjection> findRepeatabilityForProtocolPdf(Protocol protocol);

    List<RepeatabilityPdfProjection> findRepeatabilityForDecisionPdf(Decision decision, Protocol mainProtocol);

    List<RepeatabilityResponseDTO> findRepeatability(Long protocolId, Long decisionId);

    List<Long> getProtocolRepeatabilityProtocolId(Long protocolId);

    boolean hasProtocolRepeatability(Protocol protocol);

}
