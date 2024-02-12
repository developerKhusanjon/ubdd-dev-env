package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode
public class ThirdCourtDefendantRequestDTO {

    // Идентификатор нарушителя в системе X-SUD
    @NotNull(message = "defendantId empty")
    private Long defendantId;

    // Идентификатор нарушителя в системе EMI
//    @NotNull(message = "violatorId empty")
    private Long violatorId;

    // Признак участия в судебном процессе
    private Boolean isParticipated;

    // Тип решения (решение, возврат и т.д.)
    private Long finalResult;

    // Тип оснавной меры наказания
    private Long mainPunishment;

    // Тип дополнительной меры наказания
    private Long additionalPunishment;

    // Причина прекращения (как наше TerminationReason)
    private Long endBase;

    // Причина возврата судьи
    private Long returnReason;

    // Регион прокурора
    private Long prosecutorRegionId;

    // Сумма штрафа
    private Long fineTotal;

    // Данные скидки
    @Valid
    private ThirdCourtDiscountRequestDTO fineWithDiscount;

    // Признак применения 33 статьи
    private boolean article33Applied;

    // Признак применения 34 статьи
    private boolean article34Applied;

    // Продолжительность арреста в сутках
    private Integer arrest;

    // Продолжительность основной меры наказаниы
    private Integer punishmentDurationYear;
    private Integer punishmentDurationMonth;
    private Integer punishmentDurationDay;

    // Продолжительность дополнительной меры наказаниы
    private Integer additionalPunishmentDurationYear;
    private Integer additionalPunishmentDurationMonth;
    private Integer additionalPunishmentDurationDay;

    // Дата отсрочки исполнения
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate delayDate;

    // Установленные ущербы нарушителя
    private List<ThirdExactedDamageRequestDTO> exactedDamage;

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
    private Boolean isGranted;

    // Причина отказа по материалу
    private Long materialRejectBase;

    // Серия и номер решения (MVD), по каторому открыт материал
    private String resolutionSeries;
    private Long resolutionNumber;

    public Boolean getIsParticipated() {
        if (isParticipated == null) {
            return false;
        }

        return isParticipated;
    }

    public List<ThirdExactedDamageRequestDTO> getExactedDamage() {
        if (exactedDamage == null) return List.of();
        return exactedDamage;
    }
}
