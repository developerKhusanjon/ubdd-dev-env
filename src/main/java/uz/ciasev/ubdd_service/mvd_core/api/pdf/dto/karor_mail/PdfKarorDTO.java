package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import lombok.Data;

@Data
public class PdfKarorDTO {

    private String qr;
    private String series;                      // "AND",
    private String number;                      // "200730000002",
    private String protocolDate;                // "10.06.2019",
    private String protocolTime;                // "16:10",
//    private String violationDate;               // "30.09.2021",
    private String violationAddress;            // "Tashkent shahar, Mirzo-Ulugber tumani",
    private String organ;                       //  Organ name",
    private String organPlace;                  //": "Андижон вилояти, Андижон ш.",
    private String organLogo;                   //": "base64String",
    private String organInspectorName;          //: "Мамедов Шухрат Алишерович",
    private String organInspectorPosition;      // "Inspector",
    private String organInspectorRank;          // "Rank",
    private String workCertificate;             //"AAA 123412312",
    private String organInspectorSign;
}
