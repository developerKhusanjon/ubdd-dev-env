package uz.ciasev.ubdd_service.config.security;

public final class SecurityConstants {

    private SecurityConstants() {}

    public static final String GOD = "GOD";
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final String WEBHOOKS = "WEBHOOKS";
    public static final String EXTERNAL = "EXTERNAL";
    public static final String ROLE_GOD = "ROLE_GOD";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_COOKIE_NAME = "ciasevAuth";
    public static final String JWT_ISSUER = "MVD-CIA-SEV";
    public static final long EXPIRATION_PERIOD_MILLIS = 24 * 60 * 60 * 1000L;
}
