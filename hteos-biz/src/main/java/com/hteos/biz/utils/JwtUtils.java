package com.hteos.biz.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author LIQIU
 * @date 2018-7-18
 **/
public class JwtUtils {

    public static String generate(String subject) {
        String token = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "HteOSJwtSecret")
                .compact();
        return "Bearer " + token;
    }

    public static String parse(String token) {
        try {
            String subject = Jwts.parser()
                    .setSigningKey("HteOSJwtSecret")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
            return subject;
        } catch (Exception e) {
            return null;
        }
    }
}
