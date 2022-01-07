package springsecurityjwt.util;

import org.springframework.stereotype.Component;

@Component
public class Constant {

    public static final String SECRET = "Digital";
    public static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1h
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}
