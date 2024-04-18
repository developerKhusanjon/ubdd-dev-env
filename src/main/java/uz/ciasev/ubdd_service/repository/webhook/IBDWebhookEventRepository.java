package uz.ciasev.ubdd_service.repository.webhook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookAddressProjection;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookArticlesProjection;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookEvent;
import uz.ciasev.ubdd_service.entity.webhook.ibd.IBDWebhookProtocolDecisionProjection;

import java.util.List;
import java.util.Optional;

public interface IBDWebhookEventRepository extends JpaRepository<IBDWebhookEvent, Long> {

    @Query("SELECT " +
            "country.id AS countryId, " +
            "region.id AS regionId, " +
            "district.id AS districtId, " +
            "address.fullAddressText AS fullAddressText, " +
            "jsonb_extract_path_text(country.name, 'lat') AS countryName, " +
            "jsonb_extract_path_text(region.name, 'lat') AS regionName, " +
            "jsonb_extract_path_text(district.name, 'lat') AS districtName " +
            "FROM Address AS address " +
            "JOIN Country AS country ON country.id = address.countryId " +
            "JOIN Region AS region ON region.id = address.regionId " +
            "JOIN District AS district ON district.id = address.districtId " +
            "WHERE address.id =:id")
    Optional<IBDWebhookAddressProjection> getAddressById(Long id);

    @Query(value = "SELECT pa.protocol_id as protocolId " +
            ", ar.short_name->>'lat' as articleName " +
            ", ap.short_name->>'lat' as articlePartShortName " +
            ", av.short_name->>'lat' as articleViolationTypeShortName " +
            ", pa.is_main as isMain " +
            ", pa.article_id as articleId " +
            ", av.id as articleViolationTypeId " +
            ", ap.id as articlePartId " +
            "FROM core_v0.protocol_article pa " +
            "   LEFT JOIN core_v0.d_article ar " +
            "   ON pa.article_part_id = ar.id " +
            "   LEFT JOIN core_v0.d_article_part ap " +
            "   ON pa.article_part_id = ap.id " +
            "   LEFT JOIN core_v0.d_article_violation_type av " +
            "   ON pa.article_violation_type_id = av.id " +
            "WHERE pa.protocol_id IN :ids " +
            "ORDER BY isMain DESC", nativeQuery = true)
    List<IBDWebhookArticlesProjection> getProtocolArticlesByProtocolIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT p.id , p.article_id FROM core_v0.protocol p WHERE p.id IN(:ids) ORDER BY p.is_main DESC ", nativeQuery = true)
    List<Long[]> getProtocolArticle(@Param("ids") List<Long> ids);

    @Query("SELECT " +
            "protocol.id AS protocolId, " +
            "admCase.id AS admCaseId, " +

            "admCase.statusId AS caseStatusId, " +
            "jsonb_extract_path_text(caseStatus.name, 'lat') AS caseStatusName, " +

            "protocol.series AS protocolSeries, " +
            "protocol.number AS protocolNumber, " +
            "protocol.createdTime AS createdTime, " +

            "jsonb_extract_path_text(organ.name, 'lat')  AS organValue, " +
            "organ.id AS registeredOrganId, " +
            "user.regionId  AS organRegionId, " +
            "user.districtId  AS organDistrictId, " +

            "concat(inspectorPerson.firstNameLat,' ',inspectorPerson.secondNameLat,' ', inspectorPerson.lastNameLat) as inspectorFio," +
            "inspectorPerson.pinpp as inspectorPinpp," +
            "inspectorPerson.isRealPinpp AS isRealInspectorPinpp, " +
            "protocol.fabula AS fabula, " +

            "protocol.violationTime as violationTime," +
            "jsonb_extract_path_text(region.name, 'lat')  AS regionValue, " +
            "region.id  AS regionId, " +
            "jsonb_extract_path_text(district.name, 'lat')  AS districtValue, " +
            "district.id  AS districtId, " +
            "jsonb_extract_path_text(mtp.name, 'lat')  AS mtpValue, " +
            "mtp.id AS mtpId, " +

            "violatorPerson.citizenshipTypeId AS citizenshipTypeId, " +
            "violatorDetail.documentSeries AS violatorDocumentSeries, " +
            "violatorDetail.documentNumber AS violatorDocumentNumber, " +
            "violatorPerson.birthDate AS violatorBirthDate, " +
            "violatorDetail.documentGivenDate AS violatorDocumentGivenDate, " +
            "violatorDetail.documentExpireDate AS violatorDocumentExpireDate, " +
            "violatorDetail.documentGivenAddressId AS violatorDocumentGivenAddressId, " +
            "violatorDetail.personDocumentTypeId AS documentTypeId, " +
            "jsonb_extract_path_text(violatorGender.name, 'lat') AS violatorGenderName, " +
            "violatorGender.id AS genderId, " +
            "violatorPerson.nationalityId AS nationalityTypeId, " +
            "jsonb_extract_path_text(occupation.name, 'lat') AS violatorOccupationName, " +
            "occupation.id AS occupationId, " +
            "violatorPerson.lastNameLat AS violatorLastNameLat, " +
            "violatorPerson.firstNameLat AS violatorFirstNameLat, " +
            "violatorPerson.secondNameLat AS violatorSecondNameLat, " +
            "violatorPerson.lastNameKir AS violatorLastNameKir, " +
            "violatorPerson.firstNameKir AS violatorFirstNameKir, " +
            "violatorPerson.secondNameKir AS violatorSecondNameKir, " +
            "violatorPerson.pinpp AS violatorPinpp, " +
            "violatorPerson.isRealPinpp AS violatorIsRealPinpp, " +

            "violatorPerson.birthAddressId AS violatorBirthAddressId, " +

            "violatorDetail.residenceAddressId AS violatorResidenceAddressId, " +

            "violatorEmployment.position AS violatorEmploymentPosition, " +
            "violatorEmployment.place AS violatorEmploymentPlace, " +
            "violator.mobile AS violatorMobile, " +
            "violator.photoUri AS violatorPhotoUri, " +

            "decision.id AS decisionId, " +
            "punishment.punishmentTypeId AS mainPunishmentTypeId, " +
            "punishment.amountText AS mainPunishmentAmount " +
            "FROM Protocol AS protocol " +

            "JOIN Region AS region ON protocol.regionId = region.id " +
            "JOIN District AS district ON protocol.districtId = district.id " +
            "JOIN Mtp AS mtp ON protocol.mtpId = mtp.id " +

            "JOIN ViolatorDetail AS violatorDetail ON protocol.violatorDetailId = violatorDetail.id " +
            "JOIN Violator AS violator ON violatorDetail.violatorId = violator.id " +
            "JOIN AdmCase AS admCase ON violator.admCaseId = admCase.id " +
            "JOIN AdmStatus AS caseStatus ON admCase.statusId = caseStatus.id " +
            "JOIN Organ AS acOrgan ON admCase.organId = acOrgan.id " +
            "JOIN Person AS violatorPerson ON violator.personId = violatorPerson.id " +
            "LEFT JOIN User AS user ON protocol.userId = user.id " +
            "LEFT JOIN AdmCaseMovement AS admMovement ON admMovement.admCaseId = admCase.id " +
            "LEFT JOIN Person AS inspectorPerson ON user.personId = inspectorPerson.id " +
            "LEFT JOIN Resolution AS resolution ON resolution.admCaseId = admCase.id AND resolution.isActive = TRUE " +
            "LEFT JOIN Decision AS decision ON resolution.id = decision.resolutionId " +
            "LEFT JOIN Punishment AS punishment  ON decision.id = punishment.decisionId AND punishment.isMain = true " +

            "LEFT JOIN Address AS violatorDocumentGivenAddress ON violatorDocumentGivenAddress.id = violatorDetail.documentGivenAddressId " +
            "LEFT JOIN Gender AS violatorGender ON violatorGender.id = violatorPerson.genderId " +

            "LEFT JOIN LastEmploymentInfo AS violatorEmployment ON violatorEmployment.id = violatorDetail.lastEmploymentInfoId " +
            "LEFT JOIN Occupation AS occupation ON occupation.id = violatorDetail.occupationId " +

            "LEFT JOIN Organ AS organ ON protocol.organId = organ.id " +

            "WHERE protocol.id in :ids ")
    List<IBDWebhookProtocolDecisionProjection> getProjectionsByProtocolId(@Param("ids") Iterable<Long> ids);
}
