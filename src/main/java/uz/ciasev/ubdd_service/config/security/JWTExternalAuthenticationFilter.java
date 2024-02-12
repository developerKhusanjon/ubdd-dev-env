package uz.ciasev.ubdd_service.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.ciasev.ubdd_service.config.wrapper.FailureResponse;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.config.security.SecurityConstants.EXPIRATION_PERIOD_MILLIS;
import static uz.ciasev.ubdd_service.config.security.SecurityConstants.JWT_ISSUER;

@Slf4j
public class JWTExternalAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final String oauth2SymmetricSecret;
    private final List<String> externalSystems;
    private final String errorMessage = "AUTH_ERROR_MESSAGE";

    public JWTExternalAuthenticationFilter(List<String> externalSystems,
                                           String oauth2SymmetricSecret,
                                           ObjectMapper objectMapper,
                                           String url) {
        this.oauth2SymmetricSecret = oauth2SymmetricSecret;
        this.objectMapper = objectMapper;
        this.externalSystems = externalSystems;
        super.setFilterProcessesUrl(url);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        LoginUser login;
        try {
            BufferedReader reader = request.getReader();
            String jsonBody = reader
                    .lines()
                    .collect(Collectors.joining("\n"));
            login = objectMapper.readValue(jsonBody, LoginUser.class);

            String externalSystem = login.getUsername() + ":" + login.getPassword();
            if (!externalSystems.contains(externalSystem)) {
                throw new AuthenticationCredentialsNotFoundException(errorMessage);
            }

        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException(errorMessage);
        }

        User user = new User();
        user.setUsername(login.username);

        return new UsernamePasswordAuthenticationToken(user, login.password);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        Algorithm algorithm = Algorithm.HMAC256(oauth2SymmetricSecret.getBytes());
        User user = (User) auth.getPrincipal();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_PERIOD_MILLIS);

        String token = JWT
                .create()
                .withIssuer(JWT_ISSUER)
                .withExpiresAt(expirationDate)
                .withClaim("username", user.getUsername())
                .withClaim("login_type", "external")
                .sign(algorithm);

        PrintWriter out = response.getWriter();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String jsonToken = objectMapper.writeValueAsString(Map.of("token", token, "token_type", "Bearer", "expires_in", "86400"));
        out.print(jsonToken);
        out.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        SecurityContextHolder.clearContext();
        getRememberMeServices().loginFail(request, response);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        FailureResponse<String> fail = new FailureResponse<>();
        FailureResponse.Data<String> data = new FailureResponse.Data<>();

        data.setCode("AUTH_ERROR");
        data.setTimestamp(LocalDateTime.now());
        data.setMessage(errorMessage);
        fail.setData(data);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response
                .getOutputStream()
                .println(objectMapper.writeValueAsString(fail));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class LoginUser {

        private String username;
        private String password;
    }
}