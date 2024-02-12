package uz.ciasev.ubdd_service.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.ciasev.ubdd_service.config.security.exception.CiasevAuthenticationError;
import uz.ciasev.ubdd_service.config.security.exception.CiasevAuthenticationException;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.config.wrapper.HttpResponseWriteService;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.UserLoginTimeService;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeRequest;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class UserLoginFilter<T> extends UsernamePasswordAuthenticationFilter {

    protected final AuthenticationManager authenticationManager;
    protected final HttpResponseWriteService responseWriteService;
    protected final ObjectMapper objectMapper;
    protected final UserJWTService jwtService;
    private final LoginRequestMapper<T> loginRequestMapper;
    protected final UserLoginTimeService userLoginTimeService;


    public UserLoginFilter(AuthenticationManager authenticationManager,
                           ObjectMapper objectMapper,
                           String url,
                           UserJWTService jwtService,
                           LoginRequestMapper<T> loginRequestMapper,
                           UserLoginTimeService userLoginTimeService

    ) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.loginRequestMapper = loginRequestMapper;
        this.responseWriteService = new HttpResponseWriteService(objectMapper);
        this.userLoginTimeService = userLoginTimeService;
        super.setFilterProcessesUrl(url);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        T body = parseRequest(request, loginRequestMapper.getBodyClass());
        if (!loginRequestMapper.validateCredentials(body)) {
            throw new CiasevAuthenticationException(ErrorCode.AUTH_ERROR_INVALID_CREDENTIALS, "Invalid credentials");
        }

        Authentication authentication = loginRequestMapper.build(body);


        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        User user = (User) auth.getPrincipal();
        JWTToken token = jwtService.generateToken(user, loginRequestMapper.loginType());
//        AuthCookieHelper.setToken(response, token);
//
        responseWriteService.write(response, HttpStatus.OK, new TokenDTO(token));
        UserLoginTimeRequest userLoginTimeRequest = new UserLoginTimeRequest();
        userLoginTimeRequest.setUser(user);
        userLoginTimeRequest.setCreateAt(LocalDateTime.now());
        userLoginTimeService.save(userLoginTimeRequest);


//        writeResponse(response, HttpServletResponse.SC_OK, new TokenDTO(token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        SecurityContextHolder.clearContext();
        getRememberMeServices().loginFail(request, response);


        String code = "AUTH_ERROR";
        if (failed instanceof CiasevAuthenticationError) {
            code = ((CiasevAuthenticationError) failed).getCode();
        }
        if (failed instanceof BadCredentialsException) {
            code = ErrorCode.AUTH_ERROR_BAD_CREDENTIALS;
        }
        if (failed instanceof DisabledException) {
            code = ErrorCode.AUTH_ERROR_USER_DEACTIVATED;
        }

        responseWriteService.writeFailure(response, HttpStatus.UNAUTHORIZED, code, failed.getMessage());

//        FailureResponse<String> fail = new FailureResponse<>();
//        FailureResponse.Data<String> data = new FailureResponse.Data<>();
//        data.setCode(code);
//        data.setTimestamp(LocalDateTime.now());
//        data.setMessage(failed.getMessage());
//        fail.setData(data);
//
//        writeResponse(response, HttpServletResponse.SC_UNAUTHORIZED, fail);
    }

    protected <T> T parseRequest(HttpServletRequest request, Class<T> clazz) {
        try (BufferedReader reader = request.getReader()) {
            return objectMapper.readValue(reader, clazz);
        } catch (IOException e) {
            throw new CiasevAuthenticationException(ErrorCode.AUTH_ERROR_INVALID_REQUEST_BODY, "Request serialization error");
        }
    }

//    private void writeResponse(HttpServletResponse response, int status, Object body) throws IOException {
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        response.setStatus(status);
//
//        try (PrintWriter out = response.getWriter()) {
//            String jsonToken = objectMapper.writeValueAsString(body);
//            out.print(jsonToken);
//            out.flush();
//        }
//    }
}