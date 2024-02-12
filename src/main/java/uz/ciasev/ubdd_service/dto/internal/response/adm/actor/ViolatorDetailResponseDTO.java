package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ViolatorDetailResponseDTO {

    @JsonUnwrapped
    private final InnerViolatorResponseDTO violator;

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final String signature;
    private final Long personDocumentTypeId;
    private final String documentSeries;
    private final String documentNumber;
    private final Long documentGivenAddressId;
    private final LocalDate documentGivenDate;
    private final LocalDate documentExpireDate;
    private final Long f1AddressId;
    private final Long residenceAddressId;
    private final Long ageCategoryId;
    private final Long occupationId;
    private final String employmentPlace;
    private final String employmentPosition;

    @JsonUnwrapped
    private final LastEmploymentInfoDTO lastEmployment;
    private final Long intoxicationTypeId;
    private final String additionally;
    private final Long externalSystemId;
    private final String externalId;
    private final LocalFileUrl photoUrl;

    private final AddressResponseDTO documentGivenAddress;


    public ViolatorDetailResponseDTO(ViolatorDetail violatorDetail,
                                     LastEmploymentInfoDTO lastEmploymentInfoDTO,
                                     Violator violator,
                                     Person person,
                                     AddressResponseDTO documentGivenAddress) {


        this.violator = new InnerViolatorResponseDTO(violator, person);

        this.id = violatorDetail.getId();
        this.createdTime = violatorDetail.getCreatedTime();
        this.editedTime = violatorDetail.getEditedTime();
        this.userId = violatorDetail.getUserId();
        this.signature = violatorDetail.getSignature();
        this.personDocumentTypeId = violatorDetail.getPersonDocumentTypeId();
        this.documentSeries = violatorDetail.getDocumentSeries();
        this.documentNumber = violatorDetail.getDocumentNumber();
        this.documentGivenAddressId = violatorDetail.getDocumentGivenAddressId();
        this.documentGivenDate = violatorDetail.getDocumentGivenDate();
        this.documentExpireDate = violatorDetail.getDocumentExpireDate();
        this.f1AddressId = violatorDetail.getF1AddressId();
        this.residenceAddressId = violatorDetail.getResidenceAddressId();
        this.ageCategoryId = violatorDetail.getAgeCategoryId();
        this.occupationId = violatorDetail.getOccupationId();
        this.employmentPlace = violatorDetail.getEmploymentPlace();
        this.employmentPosition = violatorDetail.getEmploymentPosition();
        this.lastEmployment = lastEmploymentInfoDTO;
        this.intoxicationTypeId = violatorDetail.getIntoxicationTypeId();
        this.additionally = violatorDetail.getAdditionally();
        this.externalSystemId = violatorDetail.getExternalSystemId();
        this.externalId = violatorDetail.getExternalId();
        this.photoUrl = LocalFileUrl.ofNullable(violator.getPhotoUri());

        this.documentGivenAddress = documentGivenAddress;
    }
}