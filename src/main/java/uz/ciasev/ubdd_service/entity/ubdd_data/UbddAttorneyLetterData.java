package uz.ciasev.ubdd_service.entity.ubdd_data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddAttorneyLetterDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ubdd_attorney_letter_data")
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@EntityListeners(AuditingEntityListener.class)
public class UbddAttorneyLetterData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    private LocalDateTime createdTime = LocalDateTime.now();

    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private LocalDateTime editedTime;

    private Long userId;

    //

    private String attorneyType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String notaryCode;
    private String notaryName;
    private String notaryOrganization;
    private String number;
    private LocalDate registrationDate;
    private LocalDateTime registrationTime;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "member")
    private JsonNode member;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "subject")
    private JsonNode subject;

    private String blankNumber;

    public UbddAttorneyLetterData(UbddAttorneyLetterDTO dto, ObjectMapper objectMapper) {

        this.attorneyType = dto.getAttorneyType();
        this.fromDate = dto.getFromDate();
        this.toDate = dto.getToDate();
        this.notaryCode = dto.getNotaryCode();
        this.notaryName = dto.getNotaryName();
        this.notaryOrganization = dto.getNotaryOrganization();
        this.number = dto.getNumber();
        this.registrationDate = dto.getRegistrationDate();
        this.registrationTime = dto.getRegistrationTime();
        this.member = objectMapper.convertValue(dto.getMember(), JsonNode.class);
        this.subject = objectMapper.convertValue(dto.getSubject(), JsonNode.class);
        this.blankNumber = dto.getBlankNumber();
    }
}
