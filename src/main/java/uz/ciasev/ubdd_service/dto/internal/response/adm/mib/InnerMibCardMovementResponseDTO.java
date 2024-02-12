package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class InnerMibCardMovementResponseDTO {

    private final Long mibRequestId;
    private final Long mibSendStatusId;
    private final String mibSendMessage;
    private final LocalDateTime mibSendTime;
    private final LocalDateTime mibAcceptTme;
    private final LocalDateTime mibAcceptTime;
    private final LocalDateTime mibReturnTime;
    private final String mibCaseNumber;
    private final Long mibCaseStatusId;
    private final Long amountOfRecovery;
    private final Long totalRecoveredAmount;
    private final List<PaymentData> payments;

    public InnerMibCardMovementResponseDTO() {
        this.mibRequestId = null;
        this.mibSendStatusId = null;
        this.mibCaseNumber = null;
        this.mibCaseStatusId = null;
        this.mibSendTime = null;
        this.mibAcceptTme = null;
        this.mibAcceptTime = null;
        this.mibReturnTime = null;
        this.mibSendMessage = null;
        this.amountOfRecovery = null;
        this.totalRecoveredAmount = null;
        this.payments = null;
    }

    public InnerMibCardMovementResponseDTO(MibCardMovement lastMovement) {
        this.mibRequestId = lastMovement.getMibRequestId();
        this.mibSendStatusId = lastMovement.getSendStatusId();
        this.mibCaseNumber = lastMovement.getMibCaseNumber();
        this.mibCaseStatusId = lastMovement.getMibCaseStatusId();
        this.mibSendTime = lastMovement.getSendTime();
        this.mibAcceptTme = lastMovement.getAcceptTime();
        this.mibAcceptTime = lastMovement.getAcceptTime();
        this.mibReturnTime = lastMovement.getReturnTime();
        this.mibSendMessage = lastMovement.getSendMessage();
        this.amountOfRecovery = lastMovement.getAmountOfRecovery();
        this.totalRecoveredAmount = lastMovement.getTotalRecoveredAmount();
        this.payments = lastMovement.getPayments();
    }
}
