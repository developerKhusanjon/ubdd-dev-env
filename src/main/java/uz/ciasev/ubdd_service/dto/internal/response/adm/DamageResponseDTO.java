package uz.ciasev.ubdd_service.dto.internal.response.adm;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimListResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.damage.Damage;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.time.LocalDateTime;

@Getter
public class DamageResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long violatorId;
    private final Long admCaseId;
    private final Long personId;
    private final String firstNameLat;
    private final String secondNameLat;
    private final String lastNameLat;
    private final Long victimTypeId;
    private final Long totalAmount;
    private final Long extinguishedAmount;

    private final VictimListResponseDTO victim;
    private final InvoiceResponseDTO invoice;

    public DamageResponseDTO(Damage damage, Person person, VictimListResponseDTO victim, Violator violator, InvoiceResponseDTO invoice) {
        this.id = damage.getId();
        this.createdTime = damage.getCreatedTime();
        this.editedTime = damage.getEditedTime();
        this.violatorId = damage.getViolatorId();
        this.admCaseId = violator.getAdmCaseId();
        this.personId = violator.getPersonId();
        this.firstNameLat = person.getFirstNameLat();
        this.secondNameLat = person.getSecondNameLat();
        this.lastNameLat = person.getLastNameLat();
        this.victimTypeId = damage.getVictimTypeId();
        this.totalAmount = damage.getTotalAmount();
        this.extinguishedAmount = damage.getExtinguishedAmount();

        this.victim = victim;
        this.invoice = invoice;
    }
}

