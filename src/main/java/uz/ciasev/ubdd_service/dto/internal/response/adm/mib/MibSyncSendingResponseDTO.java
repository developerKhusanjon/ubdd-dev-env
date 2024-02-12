package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.mib.MibSverkaSending;

import java.time.LocalDateTime;

@Getter
public class MibSyncSendingResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final String sourceType;
    private final String controlSerial;
    private final String controlNumber;
    private final LocalDateTime passTime;
    private final Integer passCount;
    private final Boolean pass;
    private final Boolean hasError;
    private final Boolean isApiError;
    private final String errorText;

    public MibSyncSendingResponseDTO(MibSverkaSending sverka) {
        this.id = sverka.getId();
        this.createdTime = sverka.getCreatedTime();
        this.sourceType = sverka.getSourceType();
        this.controlSerial = sverka.getControlSerial();
        this.controlNumber = sverka.getControlNumber();
        this.passTime = sverka.getPassTime();
        this.passCount = sverka.getPassCount();
        this.pass = sverka.getPass();
        this.hasError = sverka.getHasError();
        this.isApiError = sverka.getIsApiError();
        this.errorText = sverka.getErrorText();
    }
}
