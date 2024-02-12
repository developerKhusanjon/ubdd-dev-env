package uz.ciasev.ubdd_service.entity.wanted;

import lombok.*;
import uz.ciasev.ubdd_service.mvd_core.api.wanted.WantedDTO;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "wanted_protocols")
@NoArgsConstructor
public class WantedProtocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    private LocalDateTime createdTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WantedType type;

    @ManyToOne
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Long protocolId;

    private Long extId;
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
    private String inspectorPinpp;

    public WantedProtocol(Protocol protocol, String inspectorPinpp) {
        this.protocol = protocol;
        this.inspectorPinpp = inspectorPinpp;
    }

    public WantedProtocol(Protocol protocol, WantedDTO dto, String inspectorPinpp) {
        this(protocol, inspectorPinpp);
        apply(dto);
    }

    public void apply(WantedDTO dto) {

        this.type = dto.getType();
        this.extId = dto.getId();
        this.pinpp = dto.getPinpp();
        this.lastName = dto.getLastName();
        this.firstName = dto.getFirstName();
        this.secondName = dto.getSecondName();
        this.birthDate = dto.getBirthDate();
        this.articles = dto.getArticles();
        this.wantedCountry = dto.getWantedCountry();
        this.searchCaseNumber = dto.getSearchCaseNumber();
        this.searchCaseDate = dto.getSearchCaseDate();
        this.criminalCaseNumber = dto.getCriminalCaseNumber();
        this.criminalCaseDate = dto.getCriminalCaseDate();
        this.searchInitiatorHead = dto.getSearchInitiatorHead();
        this.searchInitiatorDepartment = dto.getSearchInitiatorDepartment();
        this.preventiveMeasure = dto.getPreventiveMeasure();
        this.searchReason = dto.getSearchReason();
    }

    public WantedDTO buildDTO() {

        WantedDTO rsl = new WantedDTO();

        rsl.setType(this.type);
        rsl.setId(this.extId);
        rsl.setPinpp(this.pinpp);
        rsl.setLastName(this.lastName);
        rsl.setFirstName(this.firstName);
        rsl.setSecondName(this.secondName);
        rsl.setBirthDate(this.birthDate);
        rsl.setArticles(this.articles);
        rsl.setWantedCountry(this.wantedCountry);
        rsl.setSearchCaseNumber(this.searchCaseNumber);
        rsl.setSearchCaseDate(this.searchCaseDate);
        rsl.setCriminalCaseNumber(this.criminalCaseNumber);
        rsl.setCriminalCaseDate(this.criminalCaseDate);
        rsl.setSearchInitiatorHead(this.searchInitiatorHead);
        rsl.setSearchInitiatorDepartment(this.searchInitiatorDepartment);
        rsl.setPreventiveMeasure(this.preventiveMeasure);
        rsl.setSearchReason(this.searchReason);

        return rsl;
    }
}
