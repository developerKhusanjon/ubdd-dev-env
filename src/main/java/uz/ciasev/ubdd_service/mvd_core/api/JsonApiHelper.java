package uz.ciasev.ubdd_service.mvd_core.api;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
public class JsonApiHelper {

    public HttpHeaders buildHeadersWithBasicToken(String token) {
        var headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(AUTHORIZATION, buildBasicToken(token));

        return headers;
    }

    public String buildBasicToken(String token) {
        byte[] authByte = token.getBytes();
        byte[] encodedAuth = Base64.encodeBase64(authByte);

        return "Basic " + new String(encodedAuth);
    }
}
