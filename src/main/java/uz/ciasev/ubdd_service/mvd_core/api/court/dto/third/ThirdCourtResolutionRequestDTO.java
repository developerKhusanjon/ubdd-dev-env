package uz.ciasev.ubdd_service.mvd_core.api.court.dto.third;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"hearingDate", "editing"})
public class ThirdCourtResolutionRequestDTO implements CourtBaseDTO {

    // Идентификатор административного дела в системи EMI
    @NotNull(message = "caseId empty")
    private Long caseId;

    // Идентификатор материала в системи EMI
    private Long materialId;

    // Идентификатор процесса а системе X-SUD
    @NotNull(message = "claimId empty")
    private Long claimId;

    // Идентификатор департамента суда. Нужен для определения географии
    private Long court;

    // Инстанция суда
    private Long instance;

    // Признак, что запрос являеться коректеровкой
    private boolean editing = false;

    // Дата проведения слушанья
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime hearingDate;

    @JsonProperty("useVCC")
    private boolean useVcc = false;

    private boolean isProtest;

    // Статус дела в суде
    private Long status;

    // Номер дела в суде
    private String caseNumber;

    // ФИО судьи
    private String judge;

    // Признак приостановки дела. Должен быть согласован со статусом
    private boolean isPaused;

    // Признак материала
    private Boolean isMaterial;

    // Тип материала
    private Long materialType;

    // Идентификатор дела из каторого выделен данный материал
    private Long materialPreviousClaimId;

    // Данные передачи, пересмотра, обиеденения и выделения дела
    private ThirdCourtCaseMovementRequestDTO caseMovement;

    // Решения по нарушиелям
    @Valid
    private List<ThirdCourtDefendantRequestDTO> defendant;

    // Решения по уликам
    @Valid
    private List<ThirdCourtEvidenceRequestDTO> evidenceList;

    public boolean isMaterial() {
        if (isMaterial == null) {
            return false;
        }
        return isMaterial;
    }
}
