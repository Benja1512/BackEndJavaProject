package com.purocodigo.backend.security;

import com.purocodigo.backend.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_DATE = 864000000;// 10dias
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String TOKEN_SECRET = "NTZXJuzDD3o5n6f5rpuT4e61HTP6h7zU";

    public static String getTokenSecret() {
        return null;
    }

    AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
    return appProperties.getTokenSecret();
}

