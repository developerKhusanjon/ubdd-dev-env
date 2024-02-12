package uz.ciasev.ubdd_service.entity.publicapi;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.Organ;

import javax.persistence.*;

@Data
@Entity
@Table(name = "public_api_issuers")
public class PublicApiIssuer {

    @Id
    private String issuer;

    @ManyToOne
    @JoinColumn(name = "organ_id")
    private Organ organ;

    private String keyFile;

    private Boolean isActive;
}
