package uz.ciasev.ubdd_service.utils.generator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.exception.BarcodeGenerationError;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Component
public class QRGenerator {

    public String generateQRCode(String text, int width, int height) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            return ImageUtils.convertByteToBase64(outputStream.toByteArray());

        } catch (IOException | WriterException e) {
            throw new BarcodeGenerationError(ErrorCode.QR_CODE_GENERATION_ERROR, e.getMessage());
        }
    }

    public String generateBarcode(String text, int width, int height) {
        try {
            Code39Writer writer = new Code39Writer();
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.CODE_39, width, height);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            return ImageUtils.convertByteToBase64(outputStream.toByteArray());

        } catch (IOException | WriterException e) {
            throw new BarcodeGenerationError(ErrorCode.BAR_CODE_GENERATION_ERROR, e.getMessage());
        }
    }
}
