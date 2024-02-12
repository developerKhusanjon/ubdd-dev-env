package uz.ciasev.ubdd_service.dto.internal.request.evidence;

import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;

public interface EvidenceData {

    EvidenceCategory getEvidenceCategory();

    Measures getMeasure();

    Double getQuantity();

    Long getCost();

    Currency getCurrency();
}