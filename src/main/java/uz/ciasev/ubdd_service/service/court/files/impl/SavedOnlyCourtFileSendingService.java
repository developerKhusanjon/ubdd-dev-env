package uz.ciasev.ubdd_service.service.court.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtFileRequestDTO;
import uz.ciasev.ubdd_service.entity.document.CourtDocumentProjection;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.repository.document.DocumentRepository;
import uz.ciasev.ubdd_service.service.file.Category;
import uz.ciasev.ubdd_service.service.file.FileService;
import uz.ciasev.ubdd_service.service.pdf.PdfFile;
import uz.ciasev.ubdd_service.service.pdf.PdfService;

import java.util.List;
import java.util.stream.Collectors;

@Deprecated
@Slf4j
@Service
@RequiredArgsConstructor
public class SavedOnlyCourtFileSendingService implements CourtFileSendingService {

    private final DocumentRepository documentRepository;
    private final FileService fileService;
    private final PdfService pdfService;

    @Override
    public List<FirstCourtFileRequestDTO> buildFiles(Long admCaseId) {
        List<CourtDocumentProjection> documents = documentRepository.findAllCourtProjectionByAdmCaseId(admCaseId);
        return documents.stream()
                .map(document -> {
                    FirstCourtFileRequestDTO file = new FirstCourtFileRequestDTO();
                    file.setFileId(document.getId());
                    file.setFileUri(document.getUrl());
                    file.setFileName(document.getPersonFIO());
                    file.setFileType(document.getFailFormatCode());
                    file.setDocumentTypeId(document.getDocumentTypeId());
                    return file;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String buildInvoicePath(Invoice invoice) {
        PdfFile invoicePdf = pdfService.getInvoiceForCourt(invoice.getInvoiceId());
        return fileService.save(Category.COURT_INVOICE, invoicePdf.getFileName(), invoicePdf.getContent());
    }
}
