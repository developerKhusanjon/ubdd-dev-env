package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolSimpleListProjection;
import uz.ciasev.ubdd_service.utils.FioUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class RegisteredProtocolListDTO {

    private final Long id;
    private final String series;
    private final String number;
    private final LocalDateTime registrationTime;
    private final Long registeredOrganId;
    private final Long registeredDepartmentId;
    private final Long registeredDistrictId;
    private final Long registeredRegionId;
    private final LocalDateTime violationTime;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private final String pinpp;
    private final String fio;
    private final LocalDate birthDate;
    private final Long personDocumentTypeId;
    private final String documentSeriesNumber;
    private final boolean isJuridic;
    private final boolean isMain;
    private final Long violatorId;


    public RegisteredProtocolListDTO(ProtocolSimpleListProjection tuple) {
        this.id = tuple.getId();
        this.series = tuple.getSeries();
        this.number = tuple.getNumber();
        this.registrationTime = tuple.getRegistrationTime();
        this.registeredOrganId = tuple.getRegistrationOrganId();
        this.registeredDepartmentId = tuple.getRegistrationDepartmentId();
        this.registeredDistrictId = tuple.getRegistrationDistrictId();
        this.registeredRegionId = tuple.getRegistrationRegionId();
        this.violationTime = tuple.getViolationTime();
        this.articleId = tuple.getArticleId();
        this.articlePartId = tuple.getArticlePartId();
        this.articleViolationTypeId = tuple.getArticleViolationTypeId();
        this.pinpp = tuple.getViolatorPinpp();
        this.fio = FioUtils.buildFullFio(tuple.getViolatorFirstNameLat(), tuple.getViolatorSecondNameLat(), tuple.getViolatorLastNameLat());
        this.birthDate = tuple.getViolatorBirthDate();
        this.personDocumentTypeId = tuple.getViolatorDocumentTypeId();
        this.isJuridic = tuple.getIsJuridic();
        this.documentSeriesNumber = tuple.getViolatorDocumentSeries() + tuple.getViolatorDocumentNumber();
        this.isMain = tuple.getIsMain();
        this.violatorId = tuple.getViolatorId();
    }

}
