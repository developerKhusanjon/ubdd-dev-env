package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.mib;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.entity.AdmEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class PdfReturnRequestDTO extends PdfModel {
    private Long requestId;
    private String createdDate;
    private String createdTime;
    private String sendDate;
    private String sendTime;
    private String reasonName;
    private String comment;
    private String inspectorName;
    private String inspectorPosition;

    private String organName;
    private String organAddress;
    private String organLogo;

    private String decisionSeries;
    private String decisionNumber;
    private String qr;

    public PdfReturnRequestDTO(AdmEntity admEntity) {
        super(admEntity);
        this.createdDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.createdTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}
