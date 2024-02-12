package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.execution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.service.file.FileService;
import uz.ciasev.ubdd_service.service.mib.MibExecutionStatusDocumentService;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

import static uz.ciasev.ubdd_service.service.file.Category.MIB_EXECUTION_DOCUMENTS;

@Service
@RequiredArgsConstructor
public class MibApiExecutionDocumentFileProcessingImpl implements MibApiExecutionDocumentFileProcessing {

    private final MibExecutionStatusDocumentService mibExecutionStatusDocumentService;
    private final FileService fileService;

    @Override
    public void process(MibCardMovement cardMovement, MibRequestDTO requestDTO) {

        if (requestDTO.getDocument() == null) {
            return;
        };

        byte[] content = ConvertUtils.base64ToBytes(requestDTO.getDocument().getContent());
        String fileName = requestDTO.getDocument().getName();

        if (!fileName.endsWith(".pdf")) {
            fileName = fileName + ".pdf";
        }

        String uri = fileService.save(MIB_EXECUTION_DOCUMENTS, fileName, content);

        mibExecutionStatusDocumentService.create(cardMovement, requestDTO.getExecutionCaseStatus(), uri);
    }
}
