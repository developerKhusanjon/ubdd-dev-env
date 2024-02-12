package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddListExcelProjection;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddListView;

import java.util.List;

public interface ProtocolUbddListViewRepository extends JpaRepository<ProtocolUbddListView, Long>, JpaSpecificationExecutor<ProtocolUbddListView> {
    Page<ProtocolUbddListView> findAllByIdIn(Iterable<Long> ids, Pageable pageable);
    List<ProtocolUbddListView> findAllByIdIn(Iterable<Long> ids, Sort sort);
    List<ProtocolUbddListView> findAllByIdIn(Iterable<Long> ids);
    @Query("SELECT pul.id AS id, " +
            "   jsonb_extract_path_text(admStatus.name, 'lat') AS statusLat, " +
            "   pul.vehicleNumber AS vehicleNumber, " +
            "   pul.violatorDocumentSeries AS violatorDocumentSeries, " +
            "   pul.violatorDocumentNumber AS violatorDocumentNumber, " +
            "   pul.oldSeries AS oldSeries, " +
            "   pul.oldNumber AS oldNumber, " +
            "   pul.registrationTime AS registrationTime," +
            "   pul.series AS series, " +
            "   pul.number AS number, " +
            "   pul.violationTime AS violationTime, " +
            "   jsonb_extract_path_text(article.name, 'lat') AS articleLat, " +
            "   jsonb_extract_path_text(articlePart.name, 'lat') AS articlePartLat, " +
            "   pul.vehicleColor AS vehicleColor, " +
            "   pul.vehicleBrand AS vehicleBrand, " +
            "   pul.violatorFirstName AS violatorFirstName, " +
            "   pul.violatorSecondName AS violatorSecondName, " +
            "   pul.violatorLastName AS violatorLastName, " +
            "   pul.violatorBirthDate AS violatorBirthDate, " +
            "   pul.violatorAddressFullText AS violatorAddressFullText, " +
            "   pul.violatorPinpp AS violatorPinpp, " +
            "   pul.violatorIsRealPinpp AS violatorIsRealPinpp, " +
            "   pul.violatorMobile AS violatorMobile, " +
            "   jsonb_extract_path_text(registeredRegion.name, 'lat') AS registeredRegionLat, " +
            "   jsonb_extract_path_text(consideredRegion.name, 'lat') AS consideredRegionLat, " +
            "   pul.registeredUserFio AS registeredUserFio, " +
            "   pul.consideredUserFio AS consideredUserFio, " +
            "   pul.isTablet AS isTablet " +
            "FROM ProtocolUbddListView AS pul" +
            "   LEFT JOIN AdmStatus admStatus ON pul.statusId = admStatus.id " +
            "   LEFT JOIN Article article ON pul.articleId = article.id " +
            "   LEFT JOIN ArticlePart articlePart ON pul.articlePartId = articlePart.id " +
            "   LEFT JOIN Region registeredRegion ON pul.registeredRegionId = registeredRegion.id " +
            "   LEFT JOIN Region consideredRegion ON pul.consideredRegionId = consideredRegion.id " +
            "WHERE pul.id IN :ids ")
    List<ProtocolUbddListExcelProjection> findAllUbddListExcelProjectionsByIds(List<Long> ids, Sort sort);
}
