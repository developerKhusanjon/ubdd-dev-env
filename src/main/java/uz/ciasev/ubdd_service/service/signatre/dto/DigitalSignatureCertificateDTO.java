package uz.ciasev.ubdd_service.service.signatre.dto;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;

import java.time.LocalDateTime;

import static uz.ciasev.ubdd_service.entity.EntityNameAlias.DIGITAl_SIGNATURE;

@Getter
public class DigitalSignatureCertificateDTO  implements AdmEntity {

    private final Long id;

    private final LocalDateTime createdTime;

    private final LocalDateTime editedTime;

    private final Long createdUserId;

    private final Long userId;

    private final String serial;

    private final LocalDateTime issuedOn;

    private final LocalDateTime expiresOn;

    private final boolean isActive;

    private final String activityChangeReasonDesc;

    private final Boolean isVerifyPeriodComeToEnd;

    public DigitalSignatureCertificateDTO(DigitalSignatureCertificate certificate, boolean isVerifyPeriodComeToEnd) {
        this.id = certificate.getId();
        this.createdTime = certificate.getCreatedTime();
        this.editedTime = certificate.getEditedTime();
        this.createdUserId = certificate.getCreatedUserId();
        this.userId = certificate.getUserId();
        this.serial = certificate.getSerial();
        this.issuedOn = certificate.getIssuedOn();
        this.expiresOn = certificate.getExpiresOn();
        this.isActive = certificate.isActive();
        this.activityChangeReasonDesc = certificate.getActivityChangeReasonDesc();
        this.isVerifyPeriodComeToEnd = isVerifyPeriodComeToEnd;
    }

    @Override
    public EntityNameAlias getEntityNameAlias() {
        return DIGITAl_SIGNATURE;
    }
}
