package uz.ciasev.ubdd_service.mvd_core.api.court.dto.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsRegistrationRequest;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class CourtMaterialRegistrationRequestDTO implements CourtBaseDTO, CourtMaterialFieldsRegistrationRequest {

    @NotNull(message = "CASE_ID_REQUIRED")
    private Long caseId;

    @NotNull(message = "CLAIM_ID_REQUIRED")
    private Long claimId;

    @NotNull(message = "VIOLATORS_ID_REQUIRED")
    @JsonProperty("violators")
    private List<Long> personsId;

    private Long materialPreviousClaimId;

    private String resolutionSeries;

    private String resolutionNumber;

    @NotNull(message = "MATERIAL_TYPE_REQUIRED")
    private CourtMaterialType materialType;

    @NotNull(message = "STATUS_REQUIRED")
    private CourtStatus status;

    @NotNull(message = "REG_NUMBER_REQUIRED")
    private String regNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "REG_DATE_REQUIRED")
    private LocalDate regDate;

    @Override
    public String getRegistrationNumber() {
        return regNumber;
    }

    @Override
    public LocalDate getRegistrationDate() {
        return regDate;
    }
}
