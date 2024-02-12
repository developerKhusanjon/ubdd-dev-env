package uz.ciasev.ubdd_service.config.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
//@Service
@RequiredArgsConstructor
public class HttpResponseWriteService {

    private final ObjectMapper objectMapper;

    public void writeFailure(HttpServletResponse response, HttpStatus status, String code, String message) throws IOException {

        FailureResponse.Data<String> data = new FailureResponse.Data<>();
        data.setCode(code);
        data.setMessage(message);
        data.setTimestamp(LocalDateTime.now());

        FailureResponse<String> fail = new FailureResponse<>();
        fail.setData(data);

        write(response, status, fail);
    }

    public void writeSuccessful(HttpServletResponse response, Object body) {

    }

    public void write(HttpServletResponse response, HttpStatus status, Object body) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(status.value());

        try {
            response
                    .getOutputStream()
                    .println(objectMapper.writeValueAsString(body));
        } catch (IOException e) {
            log.error("Error writing results", e);
            throw e;
        }
    }

}
