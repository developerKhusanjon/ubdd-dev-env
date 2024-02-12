package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.karor_mail;

import lombok.Data;

@Data
public class PdfPostDataDTO {

    private String postAddressFrom;             // "Узбекистан Республикаси Давлат солик кумитаси ТОшкент щахар, А. Кодирий кучаси 13а-уй",
    private String organ;                       // "Узбекистан Республикаси Давлат солик кумитаси ТОшкент щахар, А. Кодирий кучаси 13а-уй",
    private String postPhoneFrom;               // "+998 (90) 353-00-48",
    private String postAddressToName;           //"МАМЕДОВ МИРЗОАХМАД НАБИЕВИЧ",
    private String postAddressTo;               // "Мирзо-Улугбек т., Кораниези к., 10а.у., 2 кв., Тошкент ш., 100200.",
    private String postPhoneTo;                 // "+998 (90) 353-00-48",
    private String postBarcode;                 // "base64String",
    private String date;
    private String time;
    private String postNumber;
    private Boolean isWarning = false;
}
