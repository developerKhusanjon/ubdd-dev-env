package uz.ciasev.ubdd_service.dto.internal.response.adm.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

import java.time.LocalDateTime;

@Data
public class AdmCaseResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private String series;
    private String number;
    private Long organId;
    private Long departmentId;
    private Long regionId;
    private Long districtId;
    private Long statusId;
    private Boolean is308;

    public AdmCaseResponseDTO(AdmCase admCase) {
        this.id = admCase.getId();
        this.createdTime = admCase.getCreatedTime();
        this.series = admCase.getSeries();
        this.number = admCase.getNumber();
        this.organId = admCase.getOrganId();
        this.departmentId = admCase.getDepartmentId();
        this.regionId = admCase.getRegionId();
        this.districtId = admCase.getDistrictId();
        this.statusId = admCase.getStatus().getId();
        this.is308 = admCase.getIs308();
    }
}
