package uz.ciasev.ubdd_service.service.settings;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.BankAccountType;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganAccountSettingsCreateRequest extends OrganAccountSettingsUpdateRequest implements Place {

    private Organ organ;

    private Department department;

    private Region region;

    private District district;

    private ArticlePart articlePart;

    private BankAccountType bankAccountType;
}
