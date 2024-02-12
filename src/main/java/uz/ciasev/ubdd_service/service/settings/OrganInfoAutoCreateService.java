package uz.ciasev.ubdd_service.service.settings;

import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;

public interface OrganInfoAutoCreateService {

    Organ createForNew(Organ organ);

    Region createForNew(Region region);

    District createForNew(District district);

    Department createForNew(Department department);
}
