package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ResolutionListResponseDTO extends AbstractResolutionResponseDTO {

    private final Long id;
    private final boolean isActive;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final String series;
    private final String number;
    private final Long statusId;
    private final LocalDate executedDate;



    public ResolutionListResponseDTO(Resolution resolution, CancellationResolution cancellationResolution) {
        super(resolution, cancellationResolution);
        this.id = resolution.getId();
        this.isActive = resolution.isActive();
        this.createdTime = resolution.getCreatedTime();
        this.editedTime = resolution.getEditedTime();
        this.statusId = resolution.getStatus().getId();
        this.executedDate = resolution.getExecutedDate();
        this.series = resolution.getSeries();
        this.number = resolution.getNumber();
    }
}

