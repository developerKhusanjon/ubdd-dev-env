package uz.ciasev.ubdd_service.service.notification;

import uz.ciasev.ubdd_service.dto.internal.response.notification.DecisionNotificationListDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibNotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.DecisionNotification;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;

public interface DecisionNotificationService {

    List<DecisionNotification> findMibNotificationByDecision(Decision decision);

    DecisionNotification getByChannelAndId(MibNotificationTypeAlias typeAlias, Long id);

    List<? extends DecisionNotification> getAllByChannelAndIds(MibNotificationTypeAlias typeAlias, List<Long> id);

    byte[] getFileContentByIdAndChannel(Long id, MibNotificationTypeAlias typeAlias);

    List<DecisionNotificationListDTO> findAllNotificationByDecision(Long decisionId);
}
