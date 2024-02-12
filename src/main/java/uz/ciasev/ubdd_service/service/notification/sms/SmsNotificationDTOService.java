package uz.ciasev.ubdd_service.service.notification.sms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SmsRequestDTO;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.search.sms.SmsFullListResponseDTO;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignaturePasswordDTO;

import java.util.List;
import java.util.Map;

public interface SmsNotificationDTOService {

    SmsRequestDTO makeMibDTO(Decision decision, Organ organ);

//    NotificationSmsUserRequestDTO makeUserDTO(Violator violator, Organ organ);

    List<SmsRequestDTO> makeCourtDTO(CourtCaseChancelleryData admCaseRequestDTO, Organ organ);

    SmsRequestDTO makePenaltyDecisionDTO(Decision decision,
                                         List<Compensation> compensations,
                                         Organ organ);

    SmsRequestDTO makeProtocolDTO(Protocol protocol, Organ organ);

    SmsRequestDTO makeUserDigitalSignatureCertificatePasswordDTO(User user, DigitalSignaturePasswordDTO signaturePassword, Organ organ);

    Page<SmsFullListResponseDTO> globalSearchByFilter(Map<String, String> filterValues, Pageable pageable);
}
