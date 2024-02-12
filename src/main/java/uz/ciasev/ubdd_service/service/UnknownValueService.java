package uz.ciasev.ubdd_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.service.address.AddressReferenceBookService;
import uz.ciasev.ubdd_service.service.settings.OrganInfoDescription;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UnknownValueService {

    private final AddressReferenceBookService addressReferenceBookService;

    public AddressRequestDTO getAddress() {
        return new AddressRequestDTO(addressReferenceBookService.getAddressReferenceBookByPlace(AddressReferenceBookService.Place.UNKNOWN));
    }

    public OrganInfoDescription getOrganInfoDescriptiveFields() {
        return new OrganInfoDescription() {
            @Override
            public String getAddress() {
                return getTextValue();
            }

            @Override
            public String getLandline() {
                return getTextValue();
            }

            @Override
            public String getPostIndex() {
                return getTextValue();
            }
        };
    }

    public String getMobile() {
        return "000000000000";
    }

    public String getTextValue() {
        return "NOMA'LUM MA'NO";
    }

    public String getDocumentSeries() {
        return "";
    }

    public LocalDate getDocumentDate() {
        return LocalDate.EPOCH;
    }

    public String getDocumentNumber() {
        return "NOMA'LUM";
    }

    public MultiLanguage getDictName() {
        return new MultiLanguage(
                "НЕИЗВЕСТНОЕ ЗНАЧЕНИЕ",
                "НОМАЪЛУМ МАЪНО",
                "NOMA'LUM MA'NO");
    }
}
