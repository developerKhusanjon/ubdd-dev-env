package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn;

import lombok.AccessLevel;
import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtArticleRequestDTO;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResult;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialRejectBase;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentType;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransDistrict;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
public class DefendantRequest {

    // Идентификатор нарушителя в системе X-SUD
    private Long defendantId;

    // Идентификатор нарушителя в системе EMI
    private Long violatorId;

    // Признак участия в судебном процессе
    private boolean isParticipated;

    // Тип решения (решение, возврат и т.д.)
    private CourtFinalResult finalResult;

    // Тип оснавной меры наказания
    private PunishmentType mainPunishmentType;

    // Тип дополнительной меры наказания
    private PunishmentType additionalPunishmentType;

    // Причина прекращения (как наше TerminationReason)
    private TerminationReason terminationReason;

    // Причина возврата судьи
    private CourtReturnReason returnReason;

    // Регион прокурора
    @Setter(AccessLevel.NONE)
    private Region prosecutorRegion;
    @Setter(AccessLevel.NONE)
    private District prosecutorDistrict;

    // Сумма штрафа
    private Long penaltyAmount;

    // Признак применения 33 статьи
    private boolean article33Applied;

    // Признак применения 34 статьи
    private boolean article34Applied;

    // Продолжительность арреста в сутках
    private Integer arrestDurationDay;

    // Продолжительность основной меры наказаниы
    private Integer punishmentDurationYear;
    private Integer punishmentDurationMonth;
    private Integer punishmentDurationDay;

    // Продолжительность дополнительной меры наказаниы
    private Integer additionalPunishmentDurationYear;
    private Integer additionalPunishmentDurationMonth;
    private Integer additionalPunishmentDurationDay;

    // Дата отсрочки исполнения
    private LocalDate executeBeforeDate;

    // Установленные ущербы нарушителя
    private List<CompensationRequest> compensations;

    // Результаты по статьям
    private List<ThirdCourtArticleRequestDTO> articleResults;

    // Список возмездно изятых улик
    private List<Long> withdrawalEvidences;

    // Список вконфисковынных улик
    private List<Long> confiscationEvidences;


    //---АППЕЛЯЦИИ---//

    // Результат апеляции (Отказанно/Иззмененао/Прекращено)
    private Long cassationAdditionalResult;

    // Причина отмены постановления вынесеного предидущим судом
    private Long cancellingReason;

    // Причина изменения постановления вынесеного предидущим судом
    private Long changingReason;


    //---МАТЕРИАЛЫ---//

    // Признак удовлетворения материала
    private Boolean materialIsGranted;

    // Причина отказа по материалу
    private CourtMaterialRejectBase materialRejectBase;

    // Серия и номер решения (MVD), по каторому открыт материал
    private String resolutionSeries;
    private Long resolutionNumber;

    public void setProsecutorDistrict(Optional<CourtTransDistrict> courtTransDistrictOpt) {
        courtTransDistrictOpt.ifPresent(courtTransDistrict -> {
            this.prosecutorRegion = courtTransDistrict.getRegion();
            this.prosecutorDistrict = courtTransDistrict.getDistrict();
        });
    }
}
