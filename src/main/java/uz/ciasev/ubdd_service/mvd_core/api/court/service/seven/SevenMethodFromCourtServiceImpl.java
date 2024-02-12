package uz.ciasev.ubdd_service.mvd_core.api.court.service.seven;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtApiService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh.CourtDecisionFileRequestDTO;
import uz.ciasev.ubdd_service.entity.court.CourtFile;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.service.court.files.CourtFileService;
import uz.ciasev.ubdd_service.service.file.Category;
import uz.ciasev.ubdd_service.service.file.FileService;
import uz.ciasev.ubdd_service.service.resolution.cancellation.CancellationResolutionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SevenMethodFromCourtServiceImpl implements SevenMethodFromCourtService {


    private final FileService fileService;
    private final ResolutionService resolutionService;
    private final CancellationResolutionService cancellationResolutionService;
    private final CourtApiService courtApiService;
    private final CheckCourtDuplicateRequestService duplicateRequestService;
    private final CourtFileService courtFileService;


    /**
     * Седьмой метод суда
     * Суд оповещает, что к решению суда был прикреплен pdf фаил с решением.
     * У любогорешения суда есть фаил, в том числе и у возврата (17 статаус).
     *
     * @param requestDTO = CourtDecisionFileRequestDTO
     * @return CourtResponseDTO
     */
    @Override
    @Transactional
    public void accept(CourtRequestDTO<CourtDecisionFileRequestDTO> requestDTO) {

        var decisionFileRequest = requestDTO.getSendDocumentRequest();

        duplicateRequestService.checkAndRemember(decisionFileRequest);

        handle(decisionFileRequest);

    }

    private void handle(CourtDecisionFileRequestDTO decisionFileRequest) {

        // Сейчас суд вроде присылает файлы тольок после решения.
        // Если будет проблема, связнная с тем что мы сохранили фаил раньше чем решения и в результатае в решение не будет прикреплен фаил
        // то надо будет делать какой то крон/задачю после принятия решения, ктаорый для новых решений пытаеться найти фаил среди courtFile

        Long caseId = decisionFileRequest.getCaseId();
        Long claimId = decisionFileRequest.getClaimId();
        Long fileId = decisionFileRequest.getFileId();
        String fileUrl = saveFile(decisionFileRequest);

        CourtFile courtFile = courtFileService.create(caseId, claimId, fileId, fileUrl);

        // Если етсь решение, с таким клаймом, прикрепить фаил к решению как pdf решения
        resolutionService.findAllByCaseAndClaimIds(caseId, claimId)
                .forEach(resolution -> {
                    // В моент вынесения решения, в решение будет записан ранее сохраненый фаил.
                    // Поэтому если после эдитинга суд пришлет новый фаил для claimId, то надо обновить фаил текущего активного решения
                    if (resolution.isActive() || resolution.getCourtDecisionUri() == null) {
                        resolutionService.setCourtFile(resolution, courtFile);
                    }
                });

        // Если етсь отмена решения, с таким клаймом, прикрепить фаил к отмене
        cancellationResolutionService.findByCaseAndClaimIds(caseId, claimId)
                .forEach(cancellation -> {
                    cancellationResolutionService.setCourtFile(cancellation, courtFile);
                });

    }

    private String saveFile(CourtDecisionFileRequestDTO requestDTO) {
        byte[] decisionFile = courtApiService.getByteByUri(requestDTO.getUri());
        if (decisionFile == null) {
            throw new CourtValidationException("File content is empty");
        }
        var fileName = "court_decision_file_" + requestDTO.getFileId() + ".pdf";
        return fileService.save(Category.COURT_FILE, fileName, decisionFile);
    }
}
