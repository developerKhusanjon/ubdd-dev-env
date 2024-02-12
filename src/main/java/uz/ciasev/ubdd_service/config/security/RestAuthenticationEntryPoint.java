package uz.ciasev.ubdd_service.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import uz.ciasev.ubdd_service.config.wrapper.FailureResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        FailureResponse<String> fail = new FailureResponse<>();
        FailureResponse.Data<String> data = new FailureResponse.Data<>();

        data.setCode("AUTH_ERROR");
        data.setTimestamp(LocalDateTime.now());
        data.setMessage(HttpStatus.UNAUTHORIZED.getReasonPhrase());

        fail.setData(data);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(fail));
    }
}
