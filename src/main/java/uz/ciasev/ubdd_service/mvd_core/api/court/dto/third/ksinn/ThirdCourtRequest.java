package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"isEditing", "hearingTime"})
public class ThirdCourtRequest implements CourtBaseDTO {

    // Идентификатор административного дела в системи EMI
    private Long caseId;

    // Идентификатор материала в системи EMI
    private Long materialId;

    // Идентификатор процесса а системе X-SUD
    private Long claimId;

    // Идентификатор департамента суда. Нужен для определения географии
    private CourtTransfer court;

    // Инстанция суда
    private Long instance;

    // Признак, что запрос являеться коректеровкой
    private boolean isEditing;

    // Дата проведения слушанья
    private LocalDateTime hearingTime;

    private boolean isUseVcc;

    private boolean isProtest;

    // Статус дела в суде
    private CourtStatus status;

    // Номер дела в суде
    private String caseNumber;

    // ФИО судьи
    private String judgeInfo;

    // Признак приостановки дела. Должен быть согласован со статусом
    private boolean isPaused;

    // Признак материала
    private Boolean isMaterial;

    // Тип материала
    private CourtMaterialType materialType;

    // Идентификатор дела из каторого выделен данный материал
    private Long materialBaseClaimId;


    // Решения по нарушиелям
    private List<DefendantRequest> defendants;

    // Решения по уликам
    private List<EvidenceDecisionRequest> evidenceDecisions;


    // Данные передачи, пересмотра, обиеденения и выделения дела
    private MovementRequest movement;

    // Идентификатор дела, пересмотром каторого являеться тукуший запрос
    private Long claimReviewId;

    // Идентификатор дела, в каторое обьеденить текущее дело
    private Long claimMergeId;

    // Выделения протоколов из текущегодела
    private List<SeparationRequest> separations;

    public boolean isMaterial() {
        if (isMaterial == null) {
            return false;
        }
        return isMaterial;
    }
}
