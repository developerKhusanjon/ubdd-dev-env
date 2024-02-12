package uz.ciasev.ubdd_service.entity.protocol;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "v_violation_list")
@NoArgsConstructor
@AllArgsConstructor
public class ViolationListView {

    @Id
    private Long id;

    private String protocolSeries;

    private String protocolNumber;

    private Long protocolOrganId;

    private Long protocolDepartmentId;

    private Long protocolRegionId;

    private Long protocolDistrictId;

    private LocalDateTime violationTime;

    private LocalDateTime registrationTime;

    private Long protocolArticleId;

    private Long protocolArticlePartId;

    private Long protocolArticleViolationTypeId;

    private String vehicleNumber;

    private String violatorFirstName;

    private String violatorSecondName;

    private String violatorLastName;

    private LocalDate violatorBirthDate;

    private Long admCaseStatusId;

    private Long decisionId;

    private Long decisionStatusId;

    private String decisionSeries;

    private String decisionNumber;

    private LocalDateTime resolutionTime;

    private Long resolutionOrganId;

    private Long resolutionDepartmentId;

    private Long resolutionRegionId;

    private Long resolutionDistrictId;

    private Long decisionArticleId;

    private Long decisionArticlePartId;

    private Long decisionArticleViolationTypeId;

    private Long decisionTypeId;

    private Long terminationReasonId;

    private Long mainPunishmentTypeId;

    private String mainPunishmentAmountText;

    private Long additionPunishmentTypeId;

    private String additionPunishmentAmountText;

}
