package uz.ciasev.ubdd_service.dto.internal.response.dasboard;

import lombok.Data;

@Data
public class DashboardNumbersDTO {

    private Long totalAdmCases;
    private Long totalProtocols;
    private Long totalViolators;
    private Long totalVictims;
    private Long totalResolutions;
    private Long totalPhotoVideo;

    private Long admCaseRegistered;
    private Long admCaseConsidered;
    private Long admCaseMerged;
    private Long admCasePreparingToCourt;
    private Long admCaseSendToCourt;
    private Long admCaseBackFromCourt;
    private Long admCaseSendToOrgan;
    private Long admCaseBackFromOrgan;
    private Long admCaseExecutionAwaits;
    private Long admCaseInExecution;
    private Long admCaseExecuted;

    private Long resolutionExecutionAwaits;
    private Long resolutionInExecution;
    private Long resolutionExecuted;
    private Long resolutionSendToMib;
    private Long resolutionBackFromMib;
    private Long resolutionJudicialAppeal;

    private Long canceled271;
    private Long canceled21;

    private Long byTablet;
    private Long byDesktop;
}
