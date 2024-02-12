package uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDocumentRequestExternalDTO  {

    @Size(max = 10, message = ErrorCode.MAX_DOCUMENT_SERIES_LENGTH)
    @JsonProperty(value = "documentSeries")
    private String series;

    @Size(max = 32, message = ErrorCode.MAX_DOCUMENT_NUMBER_LENGTH)
    @JsonProperty(value = "documentNumber")
    private String number;

    @JsonProperty(value = "personDocumentTypeId")
    private Long personDocumentTypeId;

    @JsonProperty(value = "documentGivenAddress")
    private AddressRequestExternalDTO givenAddressDTO;

    @JsonProperty(value = "documentGivenDate")
    private LocalDate givenDate;

    @JsonProperty(value = "documentExpireDate")
    private LocalDate expireDate;
}