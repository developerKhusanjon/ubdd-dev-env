package uz.ciasev.ubdd_service.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.ciasev.ubdd_service.config.security.captcha.CaptchaUsernameLoginDTO;
import uz.ciasev.ubdd_service.config.security.captcha.CaptchaUsernameLoginRequestMapper;
import uz.ciasev.ubdd_service.config.security.captcha.SessionManager;
import uz.ciasev.ubdd_service.config.security.captcha.UserCaptchaLoginFilter;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.UserLoginTimeService;
import uz.ciasev.ubdd_service.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static uz.ciasev.ubdd_service.config.security.SecurityConstants.*;


@Slf4j
@Getter
@Setter
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@ConfigurationProperties("external-systems")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Profile("!publicapi")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String URI_LOGIN = "/v0/ubdd-api/auth/login";
    private static final String URI_CAPTCHA_LOGIN = "/captcha/auth/login";
    private static final String URI_LOGIN_GAI = "/auth/login/gai";
    private static final String EXTERNAL_SYSTEM_LOGIN = "/auth/external/login";

    @Value("${mvd-ciasev.url-v0}")
    private String baseUrlV0;

    @Value("${mvd-ciasev.webhooks.token}")
    private String webhooksToken;

    @Value("${mvd-ciasev.jwt.token.symmetric-key}")
    private String oauth2SymetricSecret;

    @Value("${mvd-ciasev.jwt.token.expiration-period-hours}")
    private Long tokenExpiredHours;

    @Value("${springdoc.api-docs.path}")
    private String openApiUrl;

    private final List<String> externalLogins = new ArrayList<>();

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final UserJWTService userJWTService;
    private final UsernameDaoAuthenticationProvider usernameAuthenticationProvider;
    private final WorkCertificateDaoAuthenticationProvider workCertificateAuthenticationProvider;
    private final UserLoginTimeService userLoginTimeService;
    private final SessionManager sessionManager;


    public SecurityConfig(
            ObjectMapper objectMapper,
            UserService userService,
            RestAccessDeniedHandler restAccessDeniedHandler,
            UserJWTService userJWTService,
            UsernameDaoAuthenticationProvider usernameAuthenticationProvider,
            WorkCertificateDaoAuthenticationProvider workCertificateDaoAuthenticationProvider,
            UserLoginTimeService userLoginTimeService,
            SessionManager sessionManager
    ) {
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
        this.userJWTService = userJWTService;
        this.usernameAuthenticationProvider = usernameAuthenticationProvider;
        this.workCertificateAuthenticationProvider = workCertificateDaoAuthenticationProvider;
        this.userLoginTimeService = userLoginTimeService;
        this.sessionManager = sessionManager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(CorsProperties corsProperties) {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());
        configuration.setAllowCredentials(corsProperties.isAllowedCredentials());
        configuration.setMaxAge(corsProperties.getMaxAge());

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        usernameAuthenticationProvider.setHideUserNotFoundExceptions(false);
        workCertificateAuthenticationProvider.setHideUserNotFoundExceptions(false);

        auth.authenticationProvider(usernameAuthenticationProvider)
                .authenticationProvider(workCertificateAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureLogout(http);
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(openApiUrl + "/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v0/citizen-api/**").permitAll()
                .antMatchers("/v0/core-api/**").permitAll()
                .antMatchers("/internal/core-api/**").permitAll()
                .antMatchers("/tools/**").permitAll()
                .antMatchers(URI_LOGIN).permitAll()
                .antMatchers(HttpMethod.GET, baseUrlV0 + "/catalogs/message-localizations").permitAll()
                .antMatchers(baseUrlV0 + "/**").hasAnyRole(EXTERNAL)
                .antMatchers("/**").authenticated()
                .and()
                .addFilter(getUsernameLoginFilter())
                .addFilter(getCaptchaLoginFilter())
                .addFilter(getWorkCertificateLoginFilter())
                .addFilter(new JWTExternalAuthenticationFilter(externalLogins,
                        oauth2SymetricSecret, objectMapper, EXTERNAL_SYSTEM_LOGIN))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),
                        userJWTService, userService, objectMapper, externalLogins, webhooksToken))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .accessDeniedHandler(restAccessDeniedHandler);
    }

    private void configureLogout(HttpSecurity http) throws Exception {
        AuthCookieHelper.configureLogout(http.logout())
                .logoutUrl("/auth/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .permitAll();
    }

    private UserLoginFilter<UsernameLoginDTO> getUsernameLoginFilter() throws Exception {
        return new UserLoginFilter<>(authenticationManager(),
                objectMapper,
                URI_LOGIN,
                userJWTService,
                new UsernameLoginRequestMapper(),
                userLoginTimeService
        );
    }

    private UserLoginFilter<CaptchaUsernameLoginDTO> getCaptchaLoginFilter() throws Exception {
        return new UserCaptchaLoginFilter<>(authenticationManager(),
                objectMapper,
                URI_CAPTCHA_LOGIN,
                userJWTService,
                new CaptchaUsernameLoginRequestMapper(sessionManager),
                userLoginTimeService
        );
    }

    private UserLoginFilter<WorkCertificateLoginDTO> getWorkCertificateLoginFilter() throws Exception {
        return new UserLoginFilter<>(authenticationManager(),
                objectMapper,
                URI_LOGIN_GAI,
                userJWTService,
                new WorkCertificateLoginRequestMapper(),
                userLoginTimeService);
    }

    public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User)
                return (User) principal;
        }

        return null;
    }
}
