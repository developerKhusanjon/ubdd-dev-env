package uz.ciasev.ubdd_service.service.notification.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;
import uz.ciasev.ubdd_service.exception.transfer.DictTransferNotPresent;
import uz.ciasev.ubdd_service.repository.notification.mail.trans.MailTransGeographyRepository;

@Service
@RequiredArgsConstructor
public class MailTransGeographyServiceImpl implements MailTransGeographyService {

    private final MailTransGeographyRepository mailTransGeographyRepository;

    @Override
    public MailTransGeography findByRegionAndDistrict(Long regionId, Long districtId) {
        return mailTransGeographyRepository.findByRegionAndDistrict(regionId, districtId)
                .orElseThrow(() -> new DictTransferNotPresent(MailTransGeography.class, "regionId", regionId, "districtId", districtId));
    }
}
