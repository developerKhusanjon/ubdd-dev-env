package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ArticleResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;

@Getter
public class ViolatorResponseDTO extends ViolatorListResponseDTO {

    private final AddressResponseDTO actualAddress;
    private final AddressResponseDTO postAddress;
    private final AddressResponseDTO birthAddress;
    private final List<ArticleResponseDTO> additionCaseArticleParts;

    public ViolatorResponseDTO(Violator violator,
                               Person person,
                               List<ArticleResponseDTO> additionCaseArticleParts,
                               AddressResponseDTO actualAddress,
                               AddressResponseDTO postAddress,
                               AddressResponseDTO birthAddress) {

        super(violator, person);
        this.additionCaseArticleParts = additionCaseArticleParts;
        this.actualAddress = actualAddress;
        this.postAddress = postAddress;
        this.birthAddress = birthAddress;
    }
}