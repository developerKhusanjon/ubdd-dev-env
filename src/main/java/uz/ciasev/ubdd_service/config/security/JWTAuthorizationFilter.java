package uz.ciasev.ubdd_service.config.security;

import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import uz.ciasev.ubdd_service.config.wrapper.HttpResponseWriteService;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.user.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.config.security.SecurityConstants.EXTERNAL;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String webhookToken;
    private final HttpResponseWriteService responseWriteService;
    private final List<String> externalSystems;
    private final UserService userService;
    private final UserJWTService userJWTService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserJWTService userJWTService,
                                  UserService userService,
                                  ObjectMapper objectMapper,
                                  List<String> externalSystems,
                                  String webhookToken) {
        super(authenticationManager);
        this.userService = userService;
        this.webhookToken = webhookToken;
        this.responseWriteService = new HttpResponseWriteService(objectMapper);
        this.externalSystems = externalSystems;
        this.userJWTService = userJWTService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {


        Optional<String> tokenOpt = AuthHeaderHelper.getToken(request);

        if (tokenOpt.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = authorizeBearerToken(tokenOpt.get());
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
        } catch (Exception e) {
            writeFailResponse(response, e);
            return;
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authorizeBearerToken(String token) {

        DecodedJWT jwt = userJWTService.decode(token);


        User user = userJWTService.getUser(jwt);
        user.addApiAccess(EXTERNAL);

        userService.checkIfUserActive(user);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    private Optional<User> authorizeExternalUsers(String token, DecodedJWT jwt) {

        String username = jwt.getClaim("username").asString();

        List<String> logins = externalSystems.stream().map(s -> s.split(":")[0]).collect(Collectors.toList());

        Optional<User> optionalUser = Optional.empty();
        if (logins.contains(username)) {
            User user = new User();
            user.setId(126446L);
            user.setUsername(username);
            user.addApiAccess(EXTERNAL);
            optionalUser = Optional.of(user);
        }
        return optionalUser;
    }

    private Optional<User> authorizeWebhookUsers(String token, DecodedJWT jwt) {
        String username = jwt.getClaim("username").asString();

        Optional<User> optionalUser = Optional.empty();
        if (this.webhookToken.equals(token)) {
            User user = new User();
            user.setUsername(username);
            user.addApiAccess(SecurityConstants.WEBHOOKS);
            optionalUser = Optional.of(user);
        }
        return optionalUser;
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        writeFailResponse(response, failed);
    }


    private void writeFailResponse(HttpServletResponse response, Exception failure) throws IOException {

        String code = "AUTH_ERROR_TOKEN";

        if (failure instanceof TokenExpiredException) {
            code = ErrorCode.AUTH_ERROR_TOKEN_EXPIRED;
        } else if (failure instanceof UsernameNotFoundException) {
            code = ErrorCode.AUTH_ERROR_TOKEN_USER_NOT_FOUND;
        } else if (failure instanceof ApplicationException) {
            code += "_" + ((ApplicationException) failure).getCode();
        } else if (failure instanceof JWTVerificationException) {
            code = ErrorCode.AUTH_ERROR_TOKEN_INVALID;
        } else if (failure instanceof IllegalArgumentException) {
            code = ErrorCode.AUTH_ERROR_TOKEN_INVALID;
        } else if (failure instanceof DataAccessException) {
            code = ErrorCode.AUTH_ERROR_TOKEN_USER_FETCHING;
        }

        responseWriteService.writeFailure(response, HttpStatus.UNAUTHORIZED, code, failure.getMessage());
    }
}
