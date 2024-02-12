package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail.PdfViolationDTO;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail.PdfViolatorDTO;
import uz.ciasev.ubdd_service.entity.AdmEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PdfProtocolDTO extends PdfModel {

    private String createdDate;
    private String createdTime;

    private String protocolQr;
    private String protocolSeries;
    private String protocolNumber;
    private String protocolCreatedDate;
    private String violationAddress;

    private String organ;
    private String organLogo;
    private String organPlace;
    private String inspectorName;
    private String inspectorWorkCertificate;
    private String inspectorRank;
    private String inspectorPosition;
    private String inspectorSign;

//    private String article;
//    private String prime;
//    private String part;
//    @JsonProperty("isAgree")
//    private Boolean isAgree;
//    @JsonProperty("isSmsNotify")
//    private Boolean isSmsNotify;
//    @JsonProperty("isPostNotify")
//    private Boolean isPostNotify;
//    private String violationDate;
//    private String violationTime;
//    private String violatorSignature;
//    private String violatorSignDate;
//    private String additionalArticles;

    private String explanatory;
    private String fabula;

    private PdfViolatorDTO violator;
    private PdfViolationDTO violation;
    private List<PdfActorsDTO> actors = new ArrayList<>();

    private List<PdfDamageDTO> damage;
    private List<String> documents;
    private List<PdfEvidenceDTO> evidences;

    public PdfProtocolDTO(AdmEntity admEntity) {
        super(admEntity);
    }

}