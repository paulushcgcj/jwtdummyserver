package org.paulushc.service;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.paulushc.config.WebSecurityConfig;
import org.paulushc.model.UserModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Slf4j
public class TokenAuthenticationService {

    private TokenAuthenticationService(){ /* Created to prevent Initialization */}

    private static final String SECRET = "org.paulushc.secret";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse response, String username) {
        String jwt = generateJWT(username);

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        response.addHeader("User",username);
    }

    public static String generateJWT(String username) {
        return Jwts.builder()
                    .setSubject(username)
                    .setExpiration(genExpiration())
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {

                try {
                    UserModel[] users = new ObjectMapper().readValue(Paths.get(WebSecurityConfig.USERDATABASE_JSON).toFile(), UserModel[].class);
                    Optional<UserModel> userModel = Arrays.stream(users).filter(userTemp -> userTemp.getLogin().getUsername().equals(user)).findFirst();
                    return new UsernamePasswordAuthenticationToken(userModel.orElse(new UserModel()), user, Collections.emptyList());
                }catch (Exception e){ log.error("Error while trying to convert user list",e);}
            }
        }
        return null;
    }

    private static Date genExpiration(){
        return Date
                .from(LocalDateTime
                        .now()
                        .plusMinutes(5)
                        .atZone(ZoneId.systemDefault()
                )
                .toInstant()
            );
    }
}
