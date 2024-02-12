package uz.ciasev.ubdd_service.dto.internal.request.evidence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;
import uz.ciasev.ubdd_service.utils.validator.ValidEvidenceData;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;

import javax.validation.constraints.NotNull;

@Data
@ValidEvidenceData
public class EvidenceRequestDTO implements EvidenceData {

    private Long personId;

    @NotNull(message = ErrorCode.EVIDENCE_CATEGORY_REQUIRED)
    @ActiveOnly(message = ErrorCode.EVIDENCE_CATEGORY_DEACTIVATED)
    @JsonProperty(value = "evidenceCategoryId")
    private EvidenceCategory evidenceCategory;

    @ActiveOnly(message = ErrorCode.MEASURE_DEACTIVATED)
    @JsonProperty(value = "measureId")
    private Measures measure;

    private Double quantity;

    @MoneyAmount(required = false, message = ErrorCode.EVIDENCE_COST_INVALID)
    private Long cost;

    @ActiveOnly(message = ErrorCode.CURRENCY_DEACTIVATED)
    @JsonProperty(value = "currencyId")
    private Currency currency;

    private String description;

    @ValidFileUri(message = ErrorCode.EVIDENCE_URI_INVALID)
    private String evidencePhotoUri;

    public Evidence buildEvidence() {
        Evidence evidence = new Evidence();
        evidence.setEvidenceCategory(this.evidenceCategory);
        evidence.setMeasure(this.measure);
        evidence.setQuantity(this.quantity);
        evidence.setCost(this.cost);
        evidence.setCurrency(this.currency);
        evidence.setDescription(this.description);
        evidence.setEvidencePhotoUri(this.evidencePhotoUri);

        return evidence;
    }

    public Evidence applyTo(Evidence evidence) {
        evidence.setEvidenceCategory(this.evidenceCategory);
        evidence.setMeasure(this.measure);
        evidence.setQuantity(this.quantity);
        evidence.setCost(this.cost);
        evidence.setCurrency(this.currency);
        evidence.setDescription(this.description);
        evidence.setEvidencePhotoUri(this.evidencePhotoUri);

        return evidence;
    }
}