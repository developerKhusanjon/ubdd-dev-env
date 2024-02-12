package uz.ciasev.ubdd_service.mvd_core.api.court.service.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtTokenDTO;
import uz.ciasev.ubdd_service.exception.CourtServiceUnavailableException;
import uz.ciasev.ubdd_service.exception.CourtTooManyRequestsException;
import uz.ciasev.ubdd_service.exception.CourtUnauthorizedException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
@Service
public class CourtTokenServiceImpl implements CourtTokenService {

    private final String tokenUrl;
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;

    private CourtTokenDTO token;
    private LocalDateTime expiredTimeToken = LocalDateTime.now();

    @Autowired
    public CourtTokenServiceImpl(@Value("${court-api.base-url}")String courtBaseUrl,
                                 @Value("${court-api.username}")String courtUsername,
                                 @Value("${court-api.password}")String courtPassword,
                                 RestTemplate restTemplate) {
        this.headers = new HttpHeaders();
        this.restTemplate = restTemplate;
        this.tokenUrl = buildTokenUrl(courtBaseUrl, courtUsername, courtPassword);
    }

    @Override
    public HttpHeaders getHeadersForCourt() {
        if (this.expiredTimeToken.isBefore(LocalDateTime.now()))
            initializeToken();

        headers.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(AUTHORIZATION, "bearer " + token.getAccessToken());

        return headers;
    }

    private void initializeToken() {
        HttpHeaders courtHeaders = new HttpHeaders();
        courtHeaders.set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Object> requestBody = new HttpEntity<>(courtHeaders);

        try {
            ResponseEntity<CourtTokenDTO> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    requestBody,
                    CourtTokenDTO.class);

            this.token = response.getBody();
            if (this.token != null && this.token.getExpiresIn() != null)
                this.expiredTimeToken = LocalDateTime.now().plusSeconds(this.token.getExpiresIn());

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
                throw new CourtUnauthorizedException(e.getMessage());
            if (e.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS))
                throw new CourtTooManyRequestsException(e.getMessage());
        } catch (HttpServerErrorException e) {
            throw new CourtServiceUnavailableException(e.getMessage());
        }
    }

    private String buildTokenUrl(String courtBaseUrl, String courtUsername, String courtPassword) {
        return courtBaseUrl + "/adm/api/mvd-case/auth" + "?username=" + courtUsername + "&password=" + courtPassword;
    }
}
