package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayerDTO {

    private Long id;
    private String name;
    private String taxid;
    private String email;
    private String phone;
    private String type;
    private String passport;
}
