package uz.ciasev.ubdd_service.dto.internal.request.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.OrganDepartmentRequest;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class RegisteredByOrganArticlePartsRequestDTO implements OrganDepartmentRequest {

    @NotNull(message = ErrorCode.ORGAN_REQUIRED)
    @JsonProperty(value = "organId")
    private Organ organ;

    @JsonProperty(value = "departmentId")
    private Department department;

    private Set<RegisteredByOrganArticlePartRequestDTO> articleParts;
}
