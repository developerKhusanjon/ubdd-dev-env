package uz.ciasev.ubdd_service.mvd_core.api.mib.api.service.transfer;

import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.ProtocolType;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

public interface MibDictTransferService {

    Long transOrgan(Organ organ);

    Long transArticlePart(ArticlePart articlePart);

    Long transCitizenType(CitizenshipType citizenshipType);

    Long transGender(Gender gender);

    Long transOccupationId(Long occupation);

    Long transPersonDocumentType(PersonDocumentType personDocumentType);

    Long transRegion(Region region, District district);

    Long transRegion(Long region, Long district);

    ProtocolType getProtocolType(Protocol protocol);

    Long transDocumentType(DocumentType documentType);

    Long transRegion(Address actualAddress);
}
