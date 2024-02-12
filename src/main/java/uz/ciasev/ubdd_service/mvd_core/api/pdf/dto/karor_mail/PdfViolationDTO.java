package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PdfViolationDTO {

    private String article;                 // "123",
    private String prim;                    // "1",
    private String part;                    // "1",
    private String violationType;           // "ФАВКУЛОДДА ВАЗИЯТДА ОЙНАНИ СИНД.БОЛГА, ЎТ ЎЧ,ТИБ.К., АВАРИЯ БЕЛГ,НУР КАЙТ БУТЛАШ";
    private List<String> additionalArticles;      // "123, 123, 321, 123",
    private String punishmentText;          // "Yol harakati",
    private String punishmentNum;           // "12.1",
    @JsonProperty("is33")
    private boolean is33;                    // false,
    @JsonProperty("is34")
    private boolean is34;                    // true,
    private String date;                    // "30.09.1990",
    private String fingerprint;             // "base64String",
    private String agreeFingerprint;        // "base64String",
    private String amount;                  // "70'000.00",
    private String amountText;              // "Етмиш минг сўм 00 тийин",
    private String damageAmount;            // "70'000.00",
    private String damageAmountText;        // "Етмиш минг сўм 00 тийин",
    private String arrest;                  // "Маъмурий қамоқ 15 сутка",
    private String photo1;            // "base64String",
    private String photo2;            // "base64String",
    @JsonProperty("isSmsNotify")
    private boolean isSmsNotify;             // false,
    @JsonProperty("isPostNotify")
    private boolean isPostNotify;            // false,
    @JsonProperty("isAgree")
    private boolean isAgree;                 // true,
    private String signDate;                // "30.02.2021"
    private String violationDate;
    private String violationTime;
    private String violatorSignature;
    private String violatorSignDate;
}
