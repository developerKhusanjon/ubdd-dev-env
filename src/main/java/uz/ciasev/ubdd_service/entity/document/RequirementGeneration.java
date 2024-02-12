package uz.ciasev.ubdd_service.entity.document;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.dto.internal.ProtocolGroupByPersonDTO;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "requirement_generation")
@Data
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@NoArgsConstructor
public class RequirementGeneration implements AdmEntity {

    @Transient
    private EntityNameAlias entityNameAlias = EntityNameAlias.REQUIREMENT;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstNameKir;
    private String secondNameKir;
    private String lastNameKir;
    private String firstNameLat;
    private String secondNameLat;
    private String lastNameLat;
    private LocalDate birthDate;
    private String actualAddress;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Long> protocols;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, String> searchParams;

    public RequirementGeneration(User user, ProtocolGroupByPersonDTO person, List<Long> protocols, Map<String, String> searchParams) {
        this.user = user;
        this.protocols = protocols;
        this.searchParams = searchParams;

        this.firstNameKir = person.getFirstNameKir();
        this.secondNameKir = person.getSecondNameKir();
        this.lastNameKir = person.getLastNameKir();
        this.firstNameLat = person.getFirstNameLat();
        this.secondNameLat = person.getSecondNameLat();
        this.lastNameLat = person.getLastNameLat();
        this.birthDate = person.getBirthDate();
        this.actualAddress = person.getActualAddress();
    }
}
