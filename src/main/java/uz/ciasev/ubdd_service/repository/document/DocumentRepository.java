package uz.ciasev.ubdd_service.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.document.CourtDocumentProjection;
import uz.ciasev.ubdd_service.entity.document.Document;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findAllByAdmCaseId(Long admCaseId);

    @Query("SELECT d " +
            " FROM Document d " +
            "WHERE d.admCaseId = :admCaseId " +
            "  AND d.documentType.alias = :alias " +
            "  AND d.fileFormat.extension IN :formats ")
    List<Document> findByAdmCaseIdAndDocumentType(Long admCaseId,
                                                  DocumentTypeAlias alias,
                                                  List<String> formats);

    @Modifying
    @Query(value = "UPDATE Document SET admCaseId = :toAdmCaseId WHERE admCaseId = :fromAdmCaseId")
    void mergeAllTo(@Param("fromAdmCaseId") Long fromAdmCaseId, @Param("toAdmCaseId") Long toAdmCaseId);

    @Query("SELECT " +
            "   d.id as id, " +
            "   d.url as url, " +
            "   d.documentTypeId as documentTypeId, " +
            "   d.documentType.alias as documentTypeAlias, " +
            "   d.fileFormat.code as failFormatCode, " +
            "   p.lastNameLat as personLastName, " +
            "   p.firstNameLat as personFirstName, " +
            "   p.secondNameLat as personSecondName " +
            "FROM Document d LEFT JOIN Person p ON d.personId = p.id " +
            "WHERE d.admCaseId = :admCaseId")
    List<CourtDocumentProjection> findAllCourtProjectionByAdmCaseId(@Param("admCaseId") Long admCaseId);

    @Query("SELECT " +
            "   d.id as id, " +
            "   d.url as url, " +
            "   d.documentTypeId as documentTypeId, " +
            "   d.documentType.alias as documentTypeAlias, " +
            "   d.fileFormat.code as failFormatCode, " +
            "   p.lastNameLat as personLastName, " +
            "   p.firstNameLat as personFirstName, " +
            "   p.secondNameLat as personSecondName " +
            "FROM Document d LEFT JOIN Person p ON d.personId = p.id " +
            "WHERE d.admCaseId = :admCaseId " +
            "AND d.documentType.preventAttach = FALSE ")
    List<CourtDocumentProjection> findNotGeneratedCourtProjectionByAdmCaseId(@Param("admCaseId") Long admCaseId);
}
