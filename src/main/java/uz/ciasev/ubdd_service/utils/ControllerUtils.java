package uz.ciasev.ubdd_service.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class ControllerUtils {

    @Deprecated
    public static void writeFileToResponse(byte[] fileContent, final HttpServletResponse response) throws IOException {
        writeFileToResponse(fileContent, ContentType.OCTET_STREAM, response);
    }

    @Deprecated
    public static void writeFileToResponse(byte[] fileContent, ContentType contentType, final HttpServletResponse response) throws IOException {
        writeFileToResponse(fileContent, contentType, null, response);
    }

    public static void writeFileToResponse(byte[] fileContent, ContentType contentType, String fileName, final HttpServletResponse response) throws IOException {
        response.setContentType(contentType.getMemoType());
        if (fileName != null) {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        }

        try (OutputStream out = response.getOutputStream()) {
            out.write(fileContent);
        }
    }

    public static ResponseEntity<byte[]> buildFileResponse(byte[] fileContent, String name) throws IOException {

        return buildFileResponse(fileContent, ContentType.OCTET_STREAM, name);
    }

    public static ResponseEntity<byte[]> buildFileResponse(byte[] fileContent, ContentType contentType, String fileName) throws IOException {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + fileName);
        responseHeaders.add("Content-Type", contentType.getMemoType());

        return new ResponseEntity<>(fileContent, responseHeaders, HttpStatus.OK);
    }

}
