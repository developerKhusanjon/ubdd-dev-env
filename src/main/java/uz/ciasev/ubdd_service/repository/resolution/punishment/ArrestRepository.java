package uz.ciasev.ubdd_service.repository.resolution.punishment;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.ArrestFullListProjection;

import java.util.List;

public interface ArrestRepository extends JpaRepository<ArrestPunishment, Long>, ArrestCustomRepository, JpaSpecificationExecutor<ArrestPunishment> {

    @Query("SELECT ar.punishment.id                                 as id " +
            ", ar.punishment.statusId                               as punishmentStatusId " +
            ", ar.punishment.decision.series                        as decisionSeries " +
            ", ar.punishment.decision.number                        as decisionNumber " +
            ", ar.punishment.decision.id                            as decisionId " +
            ", ar.punishment.decision.resolution.resolutionTime     as resolutionTime " +
            ", ar.punishment.decision.resolution.considerInfo       as resolutionConsiderInfo " +
            ", ar.punishment.decision.resolution.admCase.regionId   as regionId " +
            ", ar.punishment.decision.resolution.admCase.districtId as districtId " +
            ", ar.punishment.decision.violator.person.firstNameLat  as violatorFirstNameLat " +
            ", ar.punishment.decision.violator.person.secondNameLat as violatorSecondNameLat " +
            ", ar.punishment.decision.violator.person.lastNameLat   as violatorLastNameLat " +
            ", ar.punishment.decision.violator.person.birthDate     as violatorBirthDate " +
            ", ar.punishment.amountText                             as punishmentAmountText " +
            ", ar.inDate                                            as arrestInDate " +
            ", ar.outDate                                           as arrestOutDate " +
            ", ap.id                                                as arrestPlaceTypeId " +
            "FROM ArrestPunishment ar " +
            "   LEFT JOIN ArrestPlaceType ap" +
            "   ON ar.arrestPlaceType = ap " +
            "WHERE ar.id IN :ids")
    List<ArrestFullListProjection> findAllFullListProjectionById(@Param("ids") Iterable<Long> ids, Sort sort);
}
