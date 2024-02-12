package uz.ciasev.ubdd_service.repository.resolution.punishment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.DebtPunishmentSearchProjection;

import java.util.List;

public interface DebtPunishmentSearchRepository extends JpaRepository<Decision, Long> {

    @Query("SELECT d.id " +
            "FROM Decision d " +
            "WHERE d.statusId <> 12 " +
            "   AND d.violator.person.pinpp = :pinpp ")
    List<Long> findNotExecutedDecisionIsByPinpp(String pinpp);

    @Query("SELECT d.id " +
            "FROM Protocol p " +
            " JOIN Decision d ON d.violatorId = p.violatorDetail.violatorId " +
            "WHERE d.statusId <> 12 " +
            "   AND p.vehicleNumber = :vehicleNumber " +
            "   AND p.isDeleted = false ")
    List<Long> findNotExecutedDecisionIsByVehicleNumber(String vehicleNumber);

    @Query("SELECT p.decisionId as id, " +
            "p.decision.series as series, " +
            "p.decision.number as number, " +
            "p.decision.articleViolationTypeId as articleViolationTypeId, " +
            "p.decision.statusId as decisionStatusId, " +
            "p.decision.articlePartId as articlePartId, " +
            "p.decision.executionFromDate as executionFromDate, " +
            "p.decision.violator.person.firstNameLat as violatorFirstNameLat, " +
            "p.decision.violator.person.secondNameLat as violatorSecondNameLat, " +
            "p.decision.violator.person.lastNameLat as violatorLastNameLat, " +
            "p.decision.resolution.resolutionTime as resolutionTime, " +
            "p.decision.resolution.regionId as resolutionRegionId, " +
            "p.decision.resolution.districtId as resolutionDistrictId, " +
            "p.id as punishmentId, " +
            "p.amountText as mainPunishmentAmountText, " +
            "p.statusId as punishmentStatusId, " +
            "p.type.alias as punishmentType, " +
            "p.type.id as punishmentTypeId, " +
            "penalty.amount as mainPunishmentAmount, " +
            "penalty.discount70Amount as punishmentDiscount70Amount, " +
            "penalty.discount70ForDate as punishmentDiscount70ForDate, " +
            "penalty.discount50Amount as punishmentDiscount50Amount, " +
            "penalty.discount50ForDate as punishmentDiscount50ForDate, " +
            "penalty.paidAmount as punishmentPaidAmount, " +
            "penalty.lastPayTime as punishmentLastPayTime, " +
            "invoice.isActive as isInvoiceActive, " +
            "invoice.invoiceSerial as invoiceSerial, " +
            "rev.mayBeReturnedAfterDate as licenseRevocationEndDate, " +
            "getMibCaseNumberByDecision(p.decisionId) as mibCaseNumber, " +
            "getVehicleNumberByViolator(p.decision.violatorId) as vehicleNumber " +
            "FROM Punishment p " +
            "   LEFT JOIN LicenseRevocationPunishment rev ON p.id = rev.punishmentId " +
            "   LEFT JOIN PenaltyPunishment penalty ON p.id = penalty.punishmentId " +
            "   LEFT JOIN Invoice invoice ON invoice.penaltyPunishmentId = penalty.id " +
            "WHERE p.decision.resolution.isActive = true " +
            "   AND p.decision.resolution.admCase.isDeleted = false " +
            "   AND p.decision.resolution.admCase.organId = :organId " +
            "   AND p.decision.violator.isArchived = false " +
            "   AND p.decision.violator.person.isRealPinpp = TRUE " +
            "   AND p.statusId IN (2, 11) " +
            "   AND p.decision.statusId <> 12 " +
            "   AND p.punishmentTypeId IN :punishmentTypes " +
            "   AND p.decisionId IN :decisionsId "
    )
    Page<DebtPunishmentSearchProjection> findDebtPunishmentsByDecisionsId(List<Long> decisionsId,
                                                                          Long organId,
                                                                          List<Long> punishmentTypes,
                                                                          Pageable pageable);
}
