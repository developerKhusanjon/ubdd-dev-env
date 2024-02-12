package uz.ciasev.ubdd_service.dto.internal.response.signature;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureCertificateDTO;

import java.time.LocalDateTime;

@Getter
public class DigitalSignatureCertificateResponseDTO {

    private final Long id;

    private final LocalDateTime createdTime;

    private final LocalDateTime editedTime;

    private final Long userId;

    private final String serial;

    private final LocalDateTime issuedOn;

    private final LocalDateTime expiresOn;

    private final boolean isActive;

    private final String activityChangeReasonDesc;

    private final Boolean isVerifyPeriodComeToEnd;

    private final InspectorResponseDTO createdUser;

    public DigitalSignatureCertificateResponseDTO(DigitalSignatureCertificateDTO certificate, InspectorResponseDTO createdUser) {
        this.id = certificate.getId();
        this.createdTime = certificate.getCreatedTime();
        this.editedTime = certificate.getEditedTime();
        this.userId = certificate.getUserId();
        this.serial = certificate.getSerial();
        this.issuedOn = certificate.getIssuedOn();
        this.expiresOn = certificate.getExpiresOn();
        this.isActive = certificate.isActive();
        this.activityChangeReasonDesc = certificate.getActivityChangeReasonDesc();
        this.isVerifyPeriodComeToEnd = certificate.getIsVerifyPeriodComeToEnd();
        this.createdUser = createdUser;
    }
}
