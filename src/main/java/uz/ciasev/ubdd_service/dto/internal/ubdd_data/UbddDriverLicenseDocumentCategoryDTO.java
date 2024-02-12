package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UbddDriverLicenseDocumentCategoryDTO implements Serializable {

    private String category;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String comment;
}
