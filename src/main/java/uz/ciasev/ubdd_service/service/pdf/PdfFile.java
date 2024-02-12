package uz.ciasev.ubdd_service.service.pdf;

import lombok.*;
import uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.PdfModel;

import java.time.format.DateTimeFormatter;
import java.util.Locale;


@Getter
@AllArgsConstructor
public class PdfFile {

    private PdfModel model;
    private byte[] content;

    public static PdfFile of(PdfModel model, byte[] content) {
        PdfFile rsl = new PdfFile(model, content);
        return rsl;
    }

    public String getFileName() {
        return String.format(
                "%s_%s_%s.pdf",
                model.getModelName().toLowerCase(Locale.ROOT),
                model.getModelId(),
                model.getGenerateTime().format(DateTimeFormatter.BASIC_ISO_DATE)
        );
    }
}
