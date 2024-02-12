package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;

import java.time.LocalDateTime;

@Getter
public class MibCardReturnRequestListResponseDTO {
    private final Long id;
    private final Long mibRequestId;
    private final String mibCaseNumber;
    private final LocalDateTime acceptTime;
    private final Long regionId;
    private final Long districtId;
    private final Long mibCaseStatusId;

    private final Long reasonId;
    private final String comment;
    private final LocalDateTime editedTime;
    private final String sendMessage;

    public MibCardReturnRequestListResponseDTO(MibCardMovementReturnRequest returnRequest) {
        MibCardMovement movement = returnRequest.getMovement();

        this.id = returnRequest.getId();

        this.mibRequestId = movement.getMibRequestId();
        this.mibCaseNumber = movement.getMibCaseNumber();
        this.acceptTime = movement.getAcceptTime();
        this.regionId = movement.getRegionId();
        this.districtId = movement.getDistrictId();
        this.mibCaseStatusId = movement.getMibCaseStatusId();

        this.reasonId = returnRequest.getReasonId();
        this.comment = returnRequest.getComment();
        this.editedTime = returnRequest.getEditedTime();
        this.sendMessage = returnRequest.getSendMessage();
    }
}
