package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import lombok.Data;

@Data
public class FirstCourtFileRequestDTO {

    private Long fileId;
    private String fileUri;
    private String fileName;
    private String fileType;
    private Long documentTypeId;
}