package uz.ciasev.ubdd_service.service.notification.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.notification.sms.SmsRequestDTO;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification_;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.notification.sms.SmsNotificationRepository;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.specifications.SmsNotificationSpecifications;
import uz.ciasev.ubdd_service.utils.EncryptUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SmsNotificationServiceImpl implements SmsNotificationService {

    private final SmsNotificationRepository smsNotificationRepository;
    private final SmsNotificationSpecifications specifications;
    private final EncryptUtils encryptUtils;


    @Override
    public void sendSms(SmsRequestDTO requestDTO) {

        SmsNotification sms = buildBase(requestDTO)
                .build();

        send(sms);
    }

    @Override
    public void sendSecretSms(SmsRequestDTO requestDTO) {
        String openText = requestDTO.getMessage();
        requestDTO.setMessage("Sms text encrypted!");

        SmsNotification.SmsNotificationBuilder smsBuilder = buildBase(requestDTO);

        smsBuilder.isEncrypt(true);
        smsBuilder.encryptMessage(encryptUtils.encrypt(openText));

        SmsNotification sms = smsBuilder.build();
        send(sms);
    }

    @Override
    public List<SmsNotification> findByEntityAndEvent(AdmEntity entity, NotificationTypeAlias notificationTypeAlias) {
        return smsNotificationRepository.findAll(
//                specifications.withDeliveryStatusCode(SmsDeliveryCode.DELIVERED)
                  specifications.withEntityAlias(entity.getEntityNameAlias())
                        .and(specifications.withType(notificationTypeAlias))
                        .and(specifications.withEntityId(entity.getId())),
                Sort.by(SmsNotification_.sendTime.getName()).descending()
        );
    }

    @Override
    public List<SmsNotification> findByDecision(Long decisionId) {
        return smsNotificationRepository.findAll(
                specifications.withEntityAlias(EntityNameAlias.DECISION)
                        .and(specifications.withEntityId(decisionId))
        );
    }

    @Override
    public SmsNotification getById(Long id) {
        return smsNotificationRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(SmsNotification.class, id));
    }

    @Override
    public List<SmsNotification> getByIds(List<Long> ids) {
        return smsNotificationRepository.findAllById(ids);
    }

    private SmsNotification send(SmsNotification sms) {
        Organ organ = sms.getOrgan();

        if (Objects.isNull(organ.getSmsContract())) {
            throw new ValidationException(ErrorCode.ORGAN_SMS_CONTRACT_NO_SET);
        }

        sms.setIdPrefix(organ.getSmsContract());

        return smsNotificationRepository.saveAndFlush(sms);
    }


    private SmsNotification.SmsNotificationBuilder buildBase(SmsRequestDTO requestDTO) {

        return SmsNotification.builder()
                .notificationTypeAlias(requestDTO.getNotificationTypeAlias())
                .phoneNumber(requestDTO.getMobile())
                .message(requestDTO.getMessage())
                .organ(requestDTO.getOrgan())
                .entity(requestDTO.getEntity())
                .personId(requestDTO.getPersonId())

                .violatorId(requestDTO.getViolatorId())
                .admCaseId(requestDTO.getAdmCaseId())

                .userId(requestDTO.getUserId());
    }
}
