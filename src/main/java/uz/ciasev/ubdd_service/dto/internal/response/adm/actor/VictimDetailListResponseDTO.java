package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VictimDetailListResponseDTO {

    @JsonUnwrapped
    private final InnerVictimResponseDTO victim;

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long protocolId;
    private final Long personDocumentTypeId;
    private final String documentSeries;
    private final String documentNumber;
    private final LocalDate documentGivenDate;
    private final LocalDate documentExpireDate;
    private final Long documentGivenAddressId;
    private final Long ageCategoryId;
    private final Long occupationId;
    private final String employmentPlace;
    private final String employmentPosition;

    @JsonUnwrapped
    private final LastEmploymentInfoDTO lastEmployment;

    private final Long intoxicationTypeId;
    private final Long f1AddressId;
    private final Long residenceAddressId;
    private final LocalFileUrl photoUrl;
    private final String externalId;
    private final Long externalSystemId;

    public VictimDetailListResponseDTO(VictimDetail victimDetail, LastEmploymentInfoDTO lastEmploymentInfoDTO, Victim victim, Person person) {

        this.victim = new InnerVictimResponseDTO(victim, person);

        this.id = victimDetail.getId();
        this.createdTime = victimDetail.getCreatedTime();
        this.editedTime = victimDetail.getEditedTime();
        this.userId = victimDetail.getUserId();
        this.protocolId = victimDetail.getProtocolId();
        this.personDocumentTypeId = victimDetail.getPersonDocumentTypeId();
        this.documentSeries = victimDetail.getDocumentSeries();
        this.documentNumber = victimDetail.getDocumentNumber();
        this.documentGivenDate = victimDetail.getDocumentGivenDate();
        this.documentExpireDate = victimDetail.getDocumentExpireDate();
        this.photoUrl = LocalFileUrl.ofNullable(victim.getPhotoUri());
        this.documentGivenAddressId = victimDetail.getDocumentGivenAddressId();
        this.f1AddressId = victimDetail.getF1AddressId();
        this.residenceAddressId = victimDetail.getResidenceAddressId();
        this.ageCategoryId = victimDetail.getAgeCategoryId();
        this.occupationId = victimDetail.getOccupationId();
        this.employmentPlace = victimDetail.getEmploymentPlace();
        this.employmentPosition = victimDetail.getEmploymentPosition();
        this.lastEmployment = lastEmploymentInfoDTO;
        this.intoxicationTypeId = victimDetail.getIntoxicationTypeId();
        this.externalId = victimDetail.getExternalId();
        this.externalSystemId = victimDetail.getExternalSystemId();
    }
}
