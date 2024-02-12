package uz.ciasev.ubdd_service.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Component
public class ImageUtils {

    public static String convertByteToBase64(byte[] bytes) {
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] compress(byte[] bytes, int toHeight) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        Thumbnails.of(inputStream)
                .height(toHeight)
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }
}
