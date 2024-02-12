package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.adm.InvoiceResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimListResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CompensationListResponseDTO {

//     private final Long admCaseId;
     private final String firstName;
     private final String secondName;
     private final String lastName;
     private final LocalDate birthDate;
     private final Long citizenshipTypeId;

     private final Long id;
     private final LocalDateTime createdTime;
     private final LocalDateTime editedTime;
//     private final Boolean isActive;
//     private final Long userId;
//     private final Long resolutionId;
     private final Long violatorId;
     private final Long victimTypeId;
     private final Long victimId;
     private final Long amount;
     private final Long paidAmount;
     private final LocalDateTime lastPayTime;
     private final String executionOrganName;
     private final LocalDate executionDate;
     private final Long statusId;

     private final VictimListResponseDTO victim;
     private final InvoiceResponseDTO invoice;

     public CompensationListResponseDTO(Compensation compensation,
                                        Violator violator,
                                        Person person,
                                        VictimListResponseDTO victim,
                                        InvoiceResponseDTO invoice) {
          this.firstName = person.getFirstNameLat();
          this.secondName = person.getSecondNameLat();
          this.lastName = person.getLastNameLat();
          this.birthDate = person.getBirthDate();
          this.citizenshipTypeId = person.getCitizenshipTypeId();

//          this.admCaseId = resolution.getAdmCaseId();
          this.id = compensation.getId();
          this.createdTime = compensation.getCreatedTime();
          this.editedTime = compensation.getEditedTime();
//          this.isActive = compensation.isActive();
//          this.userId = resolution.getUserId();
//          this.resolutionId = compensation.getResolutionId();
          this.violatorId = violator.getId();
          this.victimTypeId = compensation.getVictimTypeId();
          this.victimId = compensation.getVictimId();
          this.amount = compensation.getAmount();
          this.paidAmount = compensation.getPaidAmount();
          this.lastPayTime = compensation.getLastPayTime();
          this.executionOrganName = compensation.getExecutionOrganName();
          this.executionDate = compensation.getExecutionDate();
          this.statusId = compensation.getStatusId();

          this.victim =  victim;
          this.invoice = invoice;
     }
}

