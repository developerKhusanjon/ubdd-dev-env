package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.ProtocolType;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransCitizenshipType;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransGeography;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.exception.transfer.DictTransferNotPresent;
import uz.ciasev.ubdd_service.exception.transfer.MibGeographyNotPresent;
import uz.ciasev.ubdd_service.repository.mib.MibCitizenshipTypeRepository;
import uz.ciasev.ubdd_service.repository.mib.trans.MibTransGeographyRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MibDictTransferServiceImpl implements MibDictTransferService {

    private final MibCitizenshipTypeRepository citizenshipTypeRepository;
    private final MibTransGeographyRepository geographyRepository;

    @Override
    public Long transOrgan(Organ organ) {
        return organ.getId();
    }

    @Override
    public Long transArticlePart(ArticlePart articlePart) {
        return articlePart.getId();
    }

    @Override
    public Long transCitizenType(CitizenshipType citizenshipType) {
        return citizenshipTypeRepository
                .findByInternalId(citizenshipType.getId())
                .map(MibTransCitizenshipType::getExternalId)
                .orElseThrow(() -> new DictTransferNotPresent(MibTransCitizenshipType.class, citizenshipType));
    }

    @Override
    public Long transGender(Gender gender) {
        return gender.getId();
    }

    @Override
    public Long transOccupationId(Long occupationId) {
        return occupationId;
    }

    @Override
    public Long transPersonDocumentType(PersonDocumentType personDocumentType) {
        return personDocumentType.getId();
    }    @Override


    public Long transRegion(Long regionId, Long districtId) {
        return geographyRepository
                .findByRegionIdAndDistrictId(regionId, districtId)
                .map(MibTransGeography::getExternalId)
                .orElseThrow(() -> new MibGeographyNotPresent(regionId, districtId));
    }

    @Override
    public Long transRegion(Region region, District district) {
        return transRegion(region.getId(), Optional.ofNullable(district).map(District::getId).orElse(null));
    }

    @Override
    public Long transRegion(Address actualAddress) {
        return transRegion(actualAddress.getRegionId(), actualAddress.getDistrictId());
    }

    @Override
    public Long transDocumentType(DocumentType documentType) {
        return documentType.getId();
    }

    @Override
    public ProtocolType getProtocolType(Protocol protocol) {
        if (!protocol.getOrgan().isGai()) {
            return ProtocolType.ADM;
        }

        return ProtocolType.GAI;
    }
}
