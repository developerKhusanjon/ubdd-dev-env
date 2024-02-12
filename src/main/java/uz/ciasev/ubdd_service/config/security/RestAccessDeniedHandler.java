package uz.ciasev.ubdd_service.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.config.wrapper.FailureResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        FailureResponse<String> fail = new FailureResponse<>();
        FailureResponse.Data<String> data = new FailureResponse.Data<>();

        data.setCode("AUTH_ERROR");
        data.setTimestamp(LocalDateTime.now());
        data.setMessage("Access denied");

        fail.setData(data);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(fail));
    }
}
