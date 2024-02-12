package uz.ciasev.ubdd_service.dto.internal.response.adm;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.PersonListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.entity.document.Document;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public class DocumentDetailResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final InspectorResponseDTO user;
    private final Long admCaseId;
    private final Long personId;
    private final Long documentTypeId;
    private final String description;
    private final LocalFileUrl url;
    private final String extension;

    private final PersonListResponseDTO person;

    public DocumentDetailResponseDTO(Document document, PersonListResponseDTO person, InspectorResponseDTO user) {
        this.id = document.getId();
        this.createdTime = document.getCreatedTime();
        this.editedTime = document.getEditedTime();
        this.user = user;
        this.admCaseId = document.getAdmCaseId();
        this.personId = document.getPersonId();
        this.documentTypeId = document.getDocumentTypeId();
        this.description = document.getDescription();
        this.url = LocalFileUrl.ofNullable(document.getUrl());
        this.extension = document.getExtension();

        this.person = person;
    }
}