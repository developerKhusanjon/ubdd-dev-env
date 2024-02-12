package uz.ciasev.ubdd_service.dto.internal;

import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.LastEmploymentInfo;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
public class LastEmploymentInfoDTO {

    @Size(max = 200, message = ErrorCode.MAX_LAST_EMPLOYMENT_PLACE_LENGTH)
    private String lastEmploymentPlace;

    @Size(max = 9, message = ErrorCode.MAX_LAST_EMPLOYMENT_PLACE_INN_LENGTH)
    private String lastEmploymentPlaceInn;

    @Size(max = 200, message = ErrorCode.MAX_LAST_EMPLOYMENT_PLACE_ADDRESS_LENGTH)
    private String lastEmploymentPlaceAddress;

    private Integer lastEmploymentExternalPositionId;

    @Size(max = 200, message = ErrorCode.MAX_LAST_EMPLOYMENT_POSITION_LENGTH)
    private String lastEmploymentPosition;

    @Size(max = 10, message = ErrorCode.MAX_LAST_EMPLOYMENT_PAY_RATE_LENGTH)
    private String lastEmploymentPayRate;

    @Size(max = 10, message = ErrorCode.MAX_LAST_EMPLOYMENT_CONTRACT_NUMBER_LENGTH)
    private String lastEmploymentContractNumber;

    private LocalDate lastEmploymentContractDate;

    @Size(max = 10, message = ErrorCode.MAX_LAST_EMPLOYMENT_INITIATION_DECREE_LENGTH)
    private String lastEmploymentInitiationDecreeNumber;

    @Size(max = 10, message = ErrorCode.MAX_LAST_EMPLOYMENT_TERMINATION_DECREE_LENGTH)
    private String lastEmploymentTerminationDecreeNumber;

    private LocalDate lastEmploymentFromDate;

    private LocalDate lastEmploymentToDate;

    private Boolean isEmployed;

    public boolean isEmpty() {
        return Stream.of(lastEmploymentPlace,
                        lastEmploymentPlaceInn,
                        lastEmploymentPlaceAddress,
                        lastEmploymentExternalPositionId,
                        lastEmploymentPosition,
                        lastEmploymentPayRate,
                        lastEmploymentContractNumber,
                        lastEmploymentContractDate,
                        lastEmploymentInitiationDecreeNumber,
                        lastEmploymentTerminationDecreeNumber,
                        lastEmploymentFromDate,
                        lastEmploymentToDate,
                        isEmployed)
                .allMatch(Objects::isNull);
    }

    public LastEmploymentInfoDTO(LastEmploymentInfo info) {
        this.lastEmploymentPlace = info.getPlace();
        this.lastEmploymentPlaceInn = info.getPlaceInn();
        this.lastEmploymentPlaceAddress = info.getPlaceAddress();
        this.lastEmploymentExternalPositionId = info.getExternalPositionId();
        this.lastEmploymentPosition = info.getPosition();
        this.lastEmploymentPayRate = info.getPayRate();
        this.lastEmploymentContractNumber = info.getContractNumber();
        this.lastEmploymentContractDate = info.getContractDate();
        this.lastEmploymentInitiationDecreeNumber = info.getInitiationDecreeNumber();
        this.lastEmploymentTerminationDecreeNumber = info.getTerminationDecreeNumber();
        this.lastEmploymentFromDate = info.getFromDate();
        this.lastEmploymentToDate = info.getToDate();
        this.isEmployed = info.getIsEmployed();
    }

    public LastEmploymentInfo buildWith(User user) {
        LastEmploymentInfo lastEmploymentInfo = new LastEmploymentInfo();

        lastEmploymentInfo.setUser(user);
        lastEmploymentInfo.setPlace(this.lastEmploymentPlace);
        lastEmploymentInfo.setPlaceInn(this.lastEmploymentPlaceInn);
        lastEmploymentInfo.setPlaceAddress(this.lastEmploymentPlaceAddress);
        lastEmploymentInfo.setExternalPositionId(this.lastEmploymentExternalPositionId);
        lastEmploymentInfo.setPosition(this.lastEmploymentPosition);
        lastEmploymentInfo.setPayRate(this.lastEmploymentPayRate);
        lastEmploymentInfo.setContractNumber(this.lastEmploymentContractNumber);
        lastEmploymentInfo.setContractDate(this.lastEmploymentContractDate);
        lastEmploymentInfo.setInitiationDecreeNumber(this.lastEmploymentInitiationDecreeNumber);
        lastEmploymentInfo.setTerminationDecreeNumber(this.lastEmploymentTerminationDecreeNumber);
        lastEmploymentInfo.setFromDate(this.lastEmploymentFromDate);
        lastEmploymentInfo.setToDate(this.lastEmploymentToDate);
        lastEmploymentInfo.setIsEmployed(this.isEmployed);

         return lastEmploymentInfo;
    }
}
