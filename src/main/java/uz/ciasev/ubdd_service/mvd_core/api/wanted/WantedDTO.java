package uz.ciasev.ubdd_service.mvd_core.api.wanted;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.wanted.WantedType;

@Data
@EqualsAndHashCode(of = {"id"})
public class WantedDTO {

    private Long id;
    private WantedType type;
    private Long pinpp;
    private String lastName;
    private String firstName;
    private String secondName;
    private String birthDate;
    private String articles;
    private String wantedCountry;
    private String searchCaseNumber;
    private String searchCaseDate;
    private String criminalCaseNumber;
    private String criminalCaseDate;
    private String searchInitiatorHead;
    private String searchInitiatorDepartment;
    private String preventiveMeasure;
    private String searchReason;
}
