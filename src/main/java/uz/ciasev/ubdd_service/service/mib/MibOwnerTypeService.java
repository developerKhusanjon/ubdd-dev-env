package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.entity.mib.MibOwnerType;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;

public interface MibOwnerTypeService {

    MibOwnerType findByAlias(MibOwnerTypeAlias alias);

}
