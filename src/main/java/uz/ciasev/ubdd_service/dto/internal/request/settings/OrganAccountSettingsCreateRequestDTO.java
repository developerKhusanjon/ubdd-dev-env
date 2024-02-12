package uz.ciasev.ubdd_service.dto.internal.request.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.DictIdValueOrAllType;
import uz.ciasev.ubdd_service.dto.internal.request.DictIdListValueOrAllType;
import uz.ciasev.ubdd_service.dto.internal.request.OrganDepartmentRequest;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.BankAccountType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidOrganAccountSettings;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@ValidOrganAccountSettings
public class OrganAccountSettingsCreateRequestDTO extends OrganAccountSettingsUpdateRequestDTO implements OrganDepartmentRequest {

    @NotNull(message = ErrorCode.ORGAN_REQUIRED)
    @JsonProperty("organId")
    private Organ organ;

    @JsonProperty("departmentId")
    private Department department;

    @NotNull(message = ErrorCode.ACCOUNT_TYPE_REQUIRED)
    @JsonProperty("accountTypeId")
    private BankAccountType bankAccountType;

    @Valid
    @NotNull(message = ErrorCode.ARTICLE_PART_REQUIRED)
    @JsonProperty("articlePart")
    private DictIdListValueOrAllType<ArticlePart> relatedArticlePart;

    @Valid
    @NotNull(message = ErrorCode.REGION_REQUIRED)
    @JsonProperty("region")
    private DictIdValueOrAllType<Region> relatedRegion;

    @Valid
//    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    @JsonProperty("district")
    private DistrictIdListType relatedDistrict;

    @Override
    public Map<String, Object> constructJSON() {
        Map<String, Object> json = super.constructJSON();

        json.put("organId", organ.getId());
        json.put("departmentId", Optional.ofNullable(department).map(Department::getId).orElse(null));
        json.put("accountTypeId", bankAccountType.getId());
        json.put("articlePart", constructArticlePartsJSON());
        json.put("region", constructRegionJSON());
        json.put("district", constructDistrictsJSON());

        return json;
    }

    private Map<String, Object> constructArticlePartsJSON() {
        Map<String, Object> articleParts = new HashMap<>();
        articleParts.put("isAll", relatedArticlePart.getIsAll());
        articleParts.put("ids", relatedArticlePart.getValue().stream().map(ArticlePart::getId).collect(Collectors.toList()));

        return articleParts;
    }

    private Map<String, Object> constructRegionJSON() {
        Map<String, Object> region = new HashMap<>();
        region.put("isAll", relatedRegion.getIsAll());
        region.put("id", Optional.ofNullable(relatedRegion.getValue()).map(Region::getId).orElse(null));

        return region;
    }

    private Map<String, Object> constructDistrictsJSON() {
        if (relatedDistrict == null) return null;

        Map<String, Object> districts = new HashMap<>();
        districts.put("isAll", relatedDistrict.getIsAll());
        districts.put("isHead", relatedDistrict.getIsHead());
        districts.put("ids", relatedDistrict.getValue().stream().map(District::getId).collect(Collectors.toList()));

        return districts;
    }
}
