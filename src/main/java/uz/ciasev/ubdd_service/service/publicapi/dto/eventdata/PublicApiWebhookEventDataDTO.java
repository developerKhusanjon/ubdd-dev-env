package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class PublicApiWebhookEventDataDTO {

     private PublicApiWebhookEventDataMibExecutionDTO mibExecution;
     private PublicApiWebhookEventDataCourtDTO court;
     private PublicApiWebhookEventDataInvoiceDTO invoice;
     private PublicApiWebhookEventDataPaymentDTO payment;
     private PublicApiWebhookEventDataAdmCaseStatusDTO admCaseStatus;
     private PublicApiWebhookEventDataDecisionStatusDTO decisionStatus;
     private PublicApiWebhookEventDataPunishmentStatusDTO punishmentStatus;
     private PublicApiWebhookEventDataCompensationStatusDTO compensationStatus;
     private PublicApiWebhookEventDataDecisionDTO decision;

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataMibExecutionDTO mibExecution) {
          this.mibExecution = mibExecution;
     }

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataCourtDTO court) {
          this.court = court;
     }

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataInvoiceDTO invoice) {
          this.invoice = invoice;
     }

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataPaymentDTO payment) {
          this.payment = payment;
     }

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataAdmCaseStatusDTO admCaseStatus) {
          this.admCaseStatus = admCaseStatus;
     }


     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataDecisionStatusDTO decisionStatus) {
          this.decisionStatus = decisionStatus;
     }

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataPunishmentStatusDTO punishmentStatus) {
          this.punishmentStatus = punishmentStatus;
     }

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataCompensationStatusDTO compensationStatus) {
          this.compensationStatus = compensationStatus;
     }

     public PublicApiWebhookEventDataDTO(PublicApiWebhookEventDataDecisionDTO decision) {
          this.decision = decision;
     }

}
