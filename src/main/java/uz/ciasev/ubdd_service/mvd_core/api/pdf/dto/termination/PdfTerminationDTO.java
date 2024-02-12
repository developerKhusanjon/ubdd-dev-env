package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.termination;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

@Getter
@Setter
public class PdfTerminationDTO extends PdfModel {

    private String createdDate;
    private String createdTime;

    private String year;
    private String month;
    private String day;
    private String place;
    private String organName;
    private String secondName;
    private String firstName;
    private String lastName;
    private String article;
    private String violatorName;
    private String stateCircumstancesText;
    private String position;
    private String fullName;
    private String sign;

    private String autoCreateId;

    public PdfTerminationDTO(AdmEntity admEntity) {
        super(admEntity);
    }

    public String getModelName() {
        return String.join("_", "termination", super.getModelName());
    }
}
