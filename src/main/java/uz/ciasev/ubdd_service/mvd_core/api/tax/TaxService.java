package uz.ciasev.ubdd_service.mvd_core.api.tax;

import uz.ciasev.ubdd_service.mvd_core.api.tax.dto.TaxCompanyCoreDTO;
import uz.ciasev.ubdd_service.mvd_core.api.tax.dto.TaxPersonDTO;

public interface TaxService {

    TaxCompanyCoreDTO getJuridicByInn(String inn);

    TaxPersonDTO getPersonByPinpp(String pinpp);

    String getPersonInnByPinpp(String pinpp);
}
