package uz.ciasev.ubdd_service.service.notification.mail;

import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;

public interface MailTransGeographyService {

    MailTransGeography findByRegionAndDistrict(Long regionId, Long districtId);
}
