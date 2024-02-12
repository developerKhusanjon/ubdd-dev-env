package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.projection.CourtTransferredArticleProjection;
import uz.ciasev.ubdd_service.entity.court.projection.CourtProtocolProjection;
import uz.ciasev.ubdd_service.entity.court.projection.CourtViolatorTransferredArticleProjection;

import javax.persistence.Tuple;
import java.util.List;

public interface CourtFirstMethodRepository extends JpaRepository<AdmCase, Long> {

    @Query("SELECT " +
            "   pa.protocolId as protocolId," +
            "   pa.protocol.violationTime as violationTime, " +
            "   pa.articleViolationTypeId as externalArticleViolationTypeId, " +
            "   pa.articleId as internalArticleId, " +
            "   pa.articlePartId as internalArticlePartId, " +
            "   ca.id as transferId, " +
            "   ca.externalArticleId as externalArticleId, " +
            "   ca.externalArticlePartId as externalArticlePartId " +
            "FROM ProtocolArticle pa " +
            " LEFT JOIN CourtTransArticle ca ON ca.articlePart = pa.articlePart " +
            "WHERE pa.protocol.violatorDetail.violatorId = :violatorId ")
    List<CourtViolatorTransferredArticleProjection> findCourtArticlesByViolatorId(Long violatorId);

    @Query(value = "SELECT " +
            "   ca.id AS transferId, " +
            "   ca.external_article_id AS externalArticleId, " +
            "   ca.external_article_part_id AS externalArticlePartId, " +
            "   violator_article->>'articleViolationTypeId' AS externalArticleViolationTypeId, " +
            "   violator_article->>'articlePartId' AS internalArticlePartId " +
            "FROM {h-schema}violator AS v, jsonb_array_elements(earlier_violated_article_parts) WITH ORDINALITY arr(violator_article, position) " +
            " LEFT JOIN {h-schema}court_trans_article ca ON ca.internal_article_part_id = CAST (violator_article->>'articlePartId' AS INT) " +
            "WHERE v.id = :violatorId ",
            nativeQuery = true)
    List<CourtTransferredArticleProjection> findCourtConvictedBeforeArticlesByViolatorId(Long violatorId);

    @Query("SELECT DISTINCT vd.protocolId " +
            "FROM VictimDetail vd " +
            "WHERE vd.victimId = :victimId ")
    List<Long> findAllUniqueVictimProtocolNumbers(Long victimId);

    @Query("SELECT DISTINCT vd.protocolId " +
            "FROM ParticipantDetail vd " +
            "WHERE vd.participantId = :participantId ")
    List<Long> findAllUniqueParticipantProtocolNumbers(Long participantId);

    @Query(value = "SELECT " +
            "              ac.id as case_id, " +
            "              e.id as evidence_id, " +
            "              ec.id as evcat_id, " +
            "              p.last_name_lat, " +
            "              p.first_name_lat, " +
            "              p.second_name_lat, " +
            "              concat_ws(' ', p.last_name_lat, p.first_name_lat, p.second_name_lat) as fio, " +
            "              ec.name->>'lat' as name, " +
            "              e.description, " +
            "              e.quantity, " +
            "              e.measure_id, " +
            "              e.currency_id, " +
            "              e.cost " +
            "         FROM {h-schema}adm_case ac  " +
            "         JOIN {h-schema}evidence e on ac.id = e.adm_case_id " +
            "         LEFT JOIN {h-schema}d_evidence_category ec on e.evidence_category_id = ec.id" +
            "         LEFT JOIN {h-schema}person p on e.person_id = p.id " +
            "        WHERE ac.id = :id ",
//            "          AND ac.is_deleted = FALSE",
            nativeQuery = true)
    List<Tuple> findCourtEvidenceInfoByAdmCaseId(Long id);

    @Query("SELECT " +
            "   p.id as id, " +
            "   p.createdTime as createdTime, " +
            "   cg.externalDistrictId as courtDistrictId, " +
            "   p.mtpId as mtpId, " +
            "   p.isJuridic as isJuridic, " +
            "   j.inn as juridicInn, " +
            "   j.organizationName as juridicOrganizationName, " +
            "   p.violatorDetail.documentSeries as violatorDocumentSeries, " +
            "   p.violatorDetail.documentNumber as violatorDocumentNumber, " +
            "   p.violatorDetail.personDocumentTypeId as violatorDocumentTypeId, " +
            "   p.violatorDetail.occupationId as occupationId, " +
            "   p.violatorDetail.employmentPlace as employmentPlace, " +
            "   p.violatorDetail.employmentPosition as employmentPosition " +
            "FROM Protocol p " +
            "   LEFT JOIN p.juridic j " +
            "   LEFT JOIN CourtTransGeography cg ON cg.districtId = p.districtId " +
            "WHERE p.isMain = TRUE " +
            "   AND p.violatorDetail.violatorId = :violatorId ")
    CourtProtocolProjection findCourtProjectionByViolatorId(Long violatorId);


//    @Query(value = "SELECT " +
//            "               p.id, " +
//            "               pa.article_violation_type_id, " +
//            "               p.violation_time, " +
//            "               (SELECT cta.external_article_id " +
//            "                  FROM {h-schema}court_trans_article cta " +
//            "                 WHERE pa.article_id = cta.internal_article_id " +
//            "                   AND pa.article_part_id = cta.internal_article_part_id) as external_article_id, " +
//            "               (SELECT cta.external_article_part_id " +
//            "                  FROM {h-schema}court_trans_article cta " +
//            "                 WHERE pa.article_id = cta.internal_article_id " +
//            "                   AND pa.article_part_id = cta.internal_article_part_id) as external_article_part_id " +
//            "         FROM {h-schema}adm_case ac " +
//            "         JOIN {h-schema}violator v ON ac.id = v.adm_case_id " +
//            "         LEFT JOIN {h-schema}violator_detail vd ON v.id = vd.violator_id " +
//            "         LEFT JOIN {h-schema}protocol p ON vd.id = p.violator_detail_id " +
//            "         LEFT JOIN {h-schema}protocol_article pa ON p.id = pa.protocol_id " +
//            "        WHERE ac.id = :id " +
////            "          AND ac.is_deleted = FALSE " +
////            "          AND p.is_deleted = FALSE " +
//            "          AND v.id = :vid",
//            nativeQuery = true)
//    List<Tuple> findCourtArticlesByAdmCaseAndViolatorId(@Param("id") Long id,
//                                                        @Param("vid") Long vid);
//
//    @Query(value = "SELECT " +
//            "              v.id, " +
//            "              p.id as person_id, " +
//            "              p.pinpp, " +
//            "              p.is_real_pinpp, " +
//            "              p.last_name_lat, " +
//            "              p.first_name_lat, " +
//            "              p.second_name_lat, " +
//            "              p.last_name_kir, " +
//            "              p.first_name_kir, " +
//            "              p.second_name_kir, " +
//            "              p.birth_date, " +
//            "              ct.is_foreign, " +
//            "              p.citizenship_type_id, " +
//            "              p.gender_id, " +
//            "              (SELECT ctg.external_country_id " +
//            "                 FROM {h-schema}court_trans_geography ctg " +
//            "                WHERE COALESCE(ctg.internal_country_id, null, -1) = COALESCE(a.country_id, -1) " +
//            "                  AND COALESCE(ctg.internal_region_id, null, -1) = COALESCE(a.region_id, -1)" +
//            "                  AND COALESCE(ctg.internal_district_id, null, -1) = COALESCE(a.district_id, -1)) as external_country_id, " +
//            "              (SELECT ctg.external_region_id " +
//            "                 FROM {h-schema}court_trans_geography ctg " +
//            "                WHERE ctg.internal_country_id = COALESCE(a.country_id, null) " +
//            "                  AND COALESCE(ctg.internal_region_id, NULL, -1) = COALESCE(a.region_id, null, -1) " +
//            "                  AND COALESCE(ctg.internal_district_id, NULL, -1) = COALESCE(a.district_id, null, -1)) as external_region_id, " +
//            "              (SELECT ctg.external_district_id " +
//            "                 FROM {h-schema}court_trans_geography ctg " +
//            "                WHERE ctg.internal_country_id = COALESCE(a.country_id, null) " +
//            "                  AND COALESCE(ctg.internal_region_id, NULL, -1) = COALESCE(a.region_id, null, -1) " +
//            "                  AND COALESCE(ctg.internal_district_id, NULL, -1) = COALESCE(a.district_id, null, -1)) as external_district_id " +
//            "         FROM {h-schema}adm_case ac  " +
//            "         JOIN {h-schema}victim v on ac.id = v.adm_case_id " +
//            "         LEFT JOIN {h-schema}person p on v.person_id = p.id " +
//            "         LEFT JOIN {h-schema}address a on p.birth_address_id = a.id  " +
//            "         LEFT JOIN {h-schema}d_citizenship_type as ct ON p.citizenship_type_id = ct.id " +
//            "        WHERE ac.id = :id ",
////            "          AND ac.is_deleted = FALSE",
//            nativeQuery = true)
//    List<Tuple> findCourtVictimInfoByAdmCaseId(@Param("id") Long id);
//
//    @Query(value = "SELECT " +
//            "              adm.document_type_id, " +
//            "              adm.document_series, " +
//            "              adm.document_number, " +
//            "              adm.mobile, " +
//            "              adm.external_district_id, " +
//            "              adm.external_region_id, " +
//            "              adm.address " +
//            "         FROM (SELECT " +
//            "                       vd.document_type_id, " +
//            "                       (SELECT ctg.external_district_id " +
//            "                          FROM {h-schema}court_trans_geography ctg " +
//            "                         WHERE ctg.internal_country_id = COALESCE(a.country_id, null) " +
//            "                           AND COALESCE(ctg.internal_region_id, NULL, -1) = COALESCE(a.region_id, null, -1) " +
//            "                           AND COALESCE(ctg.internal_district_id, NULL, -1) = COALESCE(a.district_id, null, -1)) as external_district_id, " +
//            "                       (SELECT ctg.external_region_id " +
//            "                          FROM {h-schema}court_trans_geography ctg " +
//            "                         WHERE ctg.internal_country_id = COALESCE(a.country_id, null) " +
//            "                           AND COALESCE(ctg.internal_region_id, NULL, -1) = COALESCE(a.region_id, null, -1) " +
//            "                           AND COALESCE(ctg.internal_district_id, NULL, -1) = COALESCE(a.district_id, null, -1)) as external_region_id, " +
//            "                       vd.document_series, " +
//            "                       vd.document_number, " +
//            "                       v.mobile, " +
//            "                       a.address " +
//            "                 FROM {h-schema}adm_case ac  " +
//            "                 LEFT JOIN {h-schema}victim v on ac.id = v.adm_case_id " +
//            "                 LEFT JOIN {h-schema}victim_detail vd on vd.victim_id = v.id " +
//            "                 LEFT JOIN {h-schema}address a on v.actual_address_id = a.id  " +
//            "                WHERE ac.id = :id " +
////            "                  AND ac.is_deleted = FALSE " +
//            "                  AND v.id = :vid LIMIT 1) as adm",
//            nativeQuery = true)
//    Tuple findCourtVictimDetailByAdmCaseAndVictimIds(@Param("id") Long id,
//                                                     @Param("vid") Long vid);
//
//    @Query(value = "SELECT " +
//            "              adm.participant_type_id, " +
//            "              adm.id, " +
//            "              adm.person_id, " +
//            "              adm.pinpp, " +
//            "              adm.is_real_pinpp, " +
//            "              adm.last_name_lat, " +
//            "              adm.first_name_lat, " +
//            "              adm.second_name_lat, " +
//            "              adm.last_name_kir, " +
//            "              adm.first_name_kir, " +
//            "              adm.second_name_kir, " +
//            "              adm.birth_date " +
//            "         FROM (SELECT " +
//            "                       p.participant_type_id, " +
//            "                       p.id, " +
//            "                       p2.id as person_id, " +
//            "                       p2.pinpp, " +
//            "                       p2.is_real_pinpp, " +
//            "                       p2.last_name_lat, " +
//            "                       p2.first_name_lat, " +
//            "                       p2.second_name_lat, " +
//            "                       p2.last_name_kir, " +
//            "                       p2.first_name_kir, " +
//            "                       p2.second_name_kir, " +
//            "                       p2.birth_date " +
//            "                 FROM {h-schema}adm_case ac  " +
//            "                 JOIN {h-schema}participant p on ac.id = p.adm_case_id " +
//            "                 LEFT JOIN {h-schema}person p2 on p.person_id = p2.id " +
//            "                WHERE ac.id = :id " +
////            "                  AND ac.is_deleted = FALSE " +
//            ") as adm",
//            nativeQuery = true)
//    List<Tuple> findCourtParticipantInfoByAdmCaseId(@Param("id") Long id);
//
//    @Query(value = "SELECT " +
//            "              pd.document_type_id, " +
//            "              pd.document_series, " +
//            "              pd.document_number, " +
//            "              p.mobile, " +
//            "              (SELECT ctg.external_district_id " +
//            "                 FROM {h-schema}court_trans_geography ctg " +
//            "                WHERE ctg.internal_country_id = COALESCE(a.country_id, null) " +
//            "                  AND COALESCE(ctg.internal_region_id, NULL, -1) = COALESCE(a.region_id, null, -1) " +
//            "                  AND COALESCE(ctg.internal_district_id, NULL, -1) = COALESCE(a.district_id, null, -1)) as external_district_id, " +
//            "              a.address " +
//            "         FROM {h-schema}adm_case ac  " +
//            "         LEFT JOIN {h-schema}participant p on ac.id = p.adm_case_id " +
//            "         LEFT JOIN {h-schema}participant_detail pd on pd.participant_id = p.id " +
//            "         LEFT JOIN {h-schema}address a on p.actual_address_id = a.id  " +
//            "        WHERE ac.id = :id " +
//            "          AND p.id = :pid " +
////            "          AND ac.is_deleted = FALSE " +
//            "        LIMIT 1",
//            nativeQuery = true)
//    Tuple findCourtParticipantDetailByAdmCaseAndParticipantIds(@Param("id") Long id,
//                                                               @Param("pid") Long pid);
//
//    @Query("SELECT " +
//            "   v.id as id, " +
//            "   v.mobile as mobile, " +
//            "   v.healthStatusId as healthStatusId, " +
//            "   v.educationLevelId as educationLevelId, " +
//            "   v.violationRepeatabilityStatusId as violationRepeatabilityStatusId, " +
//            "   v.maritalStatusId as maritalStatusId, " +
//            "   v.childrenAmount as childrenAmount, " +
//            "   v.person.id as personId, " +
//            "   v.person.pinpp as pinpp, " +
//            "   v.person.isRealPinpp as isRealPinpp, " +
//            "   v.person.lastNameLat as lastNameLat, " +
//            "   v.person.firstNameLat as firstNameLat, " +
//            "   v.person.secondNameLat as secondNameLat, " +
//            "   v.person.lastNameKir as lastNameKir, " +
//            "   v.person.firstNameKir as firstNameKir, " +
//            "   v.person.secondNameKir as secondNameKir, " +
//            "   v.person.birthAddress.countryId as birthAddressCountryId, " +
//            "   v.person.genderId as genderId, " +
//            "   v.person.birthDate as birthDate, " +
//            "   v.person.citizenshipTypeId as citizenshipTypeId, " +
//            "   v.person.citizenshipType.alias as citizenshipTypeAlias, " +
//            "   courtNationality.externalId as courtNationalityId, " +
//            "   cgba.externalCountryId as birthAddressCourtCountryId, " +
//            "   cgba.externalRegionId as birthAddressCourtRegionId, " +
//            "   cgba.externalDistrictId as birthAddressCourtDistrictId, " +
//            "   cgaa.externalRegionId as actualAddressCourtRegionId, " +
//            "   cgaa.externalDistrictId as actualAddressCourtDistrictId, " +
//            "   v.actualAddress.address as actualAddressText, " +
//            "   v.postAddress.address as postAddressText " +
//            "FROM Violator v " +
//            "   LEFT JOIN CourtTransNationality courtNationality ON v.person.nationalityId = courtNationality.internalId " +
//            "   LEFT JOIN CourtTransGeography cgba ON " +
//            "       COALESCE(cgba.countryId, null, -1) = COALESCE(v.person.birthAddress.countryId, -1) " +
//            "       AND COALESCE(cgba.regionId, null, -1) = COALESCE(v.person.birthAddress.regionId, -1) " +
//            "       AND COALESCE(cgba.districtId, null, -1) = COALESCE(v.person.birthAddress.districtId, -1) " +
//            "   LEFT JOIN CourtTransGeography cgaa ON " +
//            "       COALESCE(cgaa.countryId, null, -1) = COALESCE(v.actualAddress.countryId, -1) " +
//            "       AND COALESCE(cgaa.regionId, null, -1) = COALESCE(v.actualAddress.regionId, -1) " +
//            "       AND COALESCE(cgaa.districtId, null, -1) = COALESCE(v.actualAddress.districtId, -1) " +
//            "WHERE v.admCaseId = :admCaseId")
//    List<CourtViolatorProjection> findAllCourtViolatorProjectionByAdmCaseId(@Param("admCaseId") Long admCaseId);
//
//    @Query(value = "SELECT " +
//            "              item_obj->>'articleId' AS articleId, " +
//            "              item_obj->>'articlePartId' AS articlePartId, " +
//            "              item_obj->>'articleViolationTypeId' AS violationId " +
//            "         FROM {h-schema}violator AS v, jsonb_array_elements(earlier_violated_article_parts) WITH ORDINALITY arr(item_obj, position) " +
//            "        WHERE v.id = :id",
//            nativeQuery = true)
//    List<CourtTransferredArticleProjection> findCourtConvictedBeforeArticlesByViolatorId(@Param("id") Long id);
}
