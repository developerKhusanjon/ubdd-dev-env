package uz.ciasev.ubdd_service.repository.trans;

import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;

public interface MailTransGeographyAdminRepository extends AbstractTransEntityRepository<MailTransGeography> {

    boolean existsByRegionAndDistrict(Region region, District district);
}
