package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtCaseFieldsResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdmCaseDetailResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long statusId;
    private final Boolean isDeleted;
    private final String series;
    private final String number;
    private final LocalDate openedDate;
    private final Long considerUserId;
    private final LocalDateTime consideredTime;
    private final String considerInfo;
    private final Long userId;
    private final Long organId;
    private final Long departmentId;
    private final Long regionId;
    private final Long districtId;
    private final String courtOutNumber;
    private final LocalDate courtOutDate;
    private final Long courtRegionId;
    private final Long courtDistrictId;
    private final Long courtConsideringBasisId;
    private final Long courtConsideringAdditionId;
    private final Long violationPlaceTypeId;
    private final String violationPlaceAddress;
    private final String fabula;
    private final boolean is308;

    private final InspectorResponseDTO user;
    private final List<String> permittedActions;

    private final CourtCaseFieldsResponseDTO courtCaseFields;

    private final Long mergedToAdmCaseId;

    private final AdmCaseDeletionRegistrationResponseDTO deletionRegistration;

    public AdmCaseDetailResponseDTO(AdmCase admCase,
                                    InspectorResponseDTO user,
                                    List<ActionAlias> permittedActions,
                                    CourtCaseFieldsResponseDTO courtCaseFields,
                                    AdmCaseDeletionRegistrationResponseDTO deletionRegistration) {
        this.id = admCase.getId();
        this.createdTime = admCase.getCreatedTime();
        this.editedTime = admCase.getEditedTime();
        this.statusId = admCase.getStatus().getId();
        this.isDeleted = admCase.isDeleted();
        this.series = admCase.getSeries();
        this.number = admCase.getNumber();
        this.openedDate = admCase.getOpenedDate();
        this.considerUserId = admCase.getConsiderUserId();
        this.consideredTime = admCase.getConsideredTime();
        this.considerInfo = admCase.getConsiderInfo();
        this.userId = admCase.getUserId();
        this.organId = admCase.getOrganId();
        this.departmentId = admCase.getDepartmentId();
        this.regionId = admCase.getRegionId();
        this.districtId = admCase.getDistrictId();
        this.courtOutNumber = admCase.getCourtOutNumber();
        this.courtOutDate = admCase.getCourtOutDate();
        this.courtRegionId = admCase.getCourtRegionId();
        this.courtDistrictId = admCase.getCourtDistrictId();
        this.courtConsideringBasisId = admCase.getCourtConsideringBasisId();
        this.courtConsideringAdditionId = admCase.getCourtConsideringAdditionId();
        this.violationPlaceTypeId = admCase.getViolationPlaceTypeId();
        this.violationPlaceAddress = admCase.getViolationPlaceAddress();
        this.fabula = admCase.getFabula();
        this.is308 = admCase.getIs308();
        this.courtCaseFields = courtCaseFields;
        this.user = user;
        this.permittedActions = permittedActions.stream().map(ActionAlias::name).collect(Collectors.toList());
        this.mergedToAdmCaseId = admCase.getMergedToAdmCaseId();
        this.deletionRegistration = deletionRegistration;
    }
}
