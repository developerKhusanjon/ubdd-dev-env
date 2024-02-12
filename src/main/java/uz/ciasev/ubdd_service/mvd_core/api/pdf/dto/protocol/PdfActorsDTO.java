package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol;

import lombok.Data;

@Data
public class PdfActorsDTO {

    private String fio;
    private String lastName;
    private String firstName;
    private String secondName;
    private String birthDate;
    private String signDate;
    private String sign;
    private Boolean isVictim = false;
}
