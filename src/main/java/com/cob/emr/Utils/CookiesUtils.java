package com.cob.emr.Utils;

import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;

public class CookiesUtils {
    public static Cookie getCookieByName(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie; // Return the desired cookie
                }
            }
        }
        return null; //
    }

    public static ResponseCookie deleteCookie(String name) {
        return ResponseCookie.from(name, null)
                .httpOnly(true)
                .secure(true)
                .path("/emr/api/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

    public static ResponseCookie createCookie(String cookieName, String cookieValue, long expiration) {
        return ResponseCookie.from(cookieName, cookieValue)
                .httpOnly(true)
                .secure(true)
                .path("/emr/api")
                .sameSite("Strict")
                .build();
    }
}
