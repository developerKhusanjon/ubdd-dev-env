package uz.ciasev.ubdd_service.repository.admcase.deletion;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequestProjection;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias;

import java.util.List;

public interface AdmCaseDeletionRequestRepository extends AdmCaseDeletionRequestCustomRepository, JpaRepository<AdmCaseDeletionRequest, Long>, JpaSpecificationExecutor<AdmCaseDeletionRequest> {

    boolean existsByAdmCaseIdAndStatus(Long admCaseId, AdmCaseDeletionRequestStatusAlias status);

    @Query("SELECT " +
            "  acdreq.id AS id" +
            ", ac.id AS admCaseId" +
            ", ac.statusId AS admCaseStatusId" +
            ", ac.createdTime AS admCaseCreatedTime" +
            ", ac.organId AS admCaseOrganId" +
            ", ac.regionId AS admCaseRegionId" +
            ", ac.districtId AS admCaseDistrictId" +
            ", acdreq.statusId AS statusId" +
            ", acdreq.createdTime AS createdTime" +
            ", su.id AS applicantId" +
            ", su.firstNameLat AS applicantFirstName" +
            ", su.lastNameLat AS applicantLastName" +
            ", su.secondNameLat AS applicantSecondName" +
            ", su.mobile AS applicantMobile" +
            ", su.rankId AS applicantRankId" +
            ", su.username AS applicantUsername" +
            ", acdreq.deleteReasonId AS deleteReasonId" +
            ", acdreq.documentBaseUri AS documentBaseUri" +
            ", acdreq.editedTime AS editedTime" +
            ", au.id AS adminId" +
            ", au.firstNameLat AS adminFirstName" +
            ", au.lastNameLat AS adminLastName" +
            ", au.secondNameLat AS adminSecondName" +
            ", au.rankId AS adminRankId" +
            ", au.username AS adminUsername" +
            ", acdreq.declineReasonId AS declineReasonId" +
            ", acdreq.declineComment AS declineComment" +
            ", acdreg.id AS registrationId" +
            ", acdreg.recoveredTime AS recoveredTime" +
            ", ru.id AS recoveredUserId" +
            ", ru.firstNameLat AS recoveredUserFirstName" +
            ", ru.lastNameLat AS recoveredUserLastName" +
            ", ru.secondNameLat AS recoveredUserSecondName" +
            ", ru.rankId AS recoveredUserRankId" +
            ", ru.username AS recoveredUserUsername " +
            "FROM AdmCaseDeletionRequest acdreq " +
            "JOIN AdmCase ac " +
            "ON acdreq.admCaseId = ac.id " +
            "JOIN User su " +
            "ON acdreq.userId = su.id " +
            "LEFT JOIN User au " +
            "ON acdreq.adminId = au.id " +
            "LEFT JOIN AdmCaseDeletionRegistration acdreg " +
            "ON acdreq.registrationId = acdreg.id " +
            "LEFT JOIN User ru " +
            "ON acdreg.recoveredUserId = ru.id " +
            "WHERE acdreq.id IN :ids")
    List<AdmCaseDeletionRequestProjection> findAllProjection(List<Long> ids, Sort sort);
}
