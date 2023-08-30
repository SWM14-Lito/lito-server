package com.lito.core.common.security.jwt;

import com.lito.core.common.exception.ApplicationException;
import com.lito.core.common.exception.auth.AuthErrorCode;
import com.lito.core.common.security.AuthUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtProvider implements InitializingBean {

    private final String secret;
    private static final String USER_ID = "userId";
    private static final String USER_PROVIDER = "provider";
    private static final String USERNAME = "username";
    private final long ACCESS_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_VALID_TIME;
    private static final String BEARER_PREFIX = "Bearer ";
    private Key key;



    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.access-expiration-time}") long accessTokenExpirationMilliseconds,
                       @Value("${jwt.refresh-expiration-time}") long refreshTokenExpirationMilliseconds,
                       @Value("${jwt.refresh-valid-time}") long refreshTokenValidMilliseconds){
        this.secret = secret;
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTokenExpirationMilliseconds;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTokenExpirationMilliseconds;
        this.REFRESH_TOKEN_VALID_TIME = refreshTokenValidMilliseconds;
    }


    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(AuthUser authUser) {
        return createToken(authUser, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String createRefreshToken(AuthUser authUser) {
        return createToken(authUser, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String createToken(AuthUser authUser, long time) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + time);
        return Jwts.builder()
                .setSubject(authUser.getUsername())
                .setClaims(createClaimsByAuthUser(authUser))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> createClaimsByAuthUser(AuthUser authUser) {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_ID, authUser.getUserId());
        map.put(USER_PROVIDER, authUser.getProvider());
        map.put(USERNAME, authUser.getUsername());
        return map;
    }

    public String getUserEmail(String token) {
        return getClaims(token)
                .get(USERNAME, String.class);
    }

    public long getRemainingMilliSecondsFromToken(String token){
        Date expiration = getClaims(token).getExpiration();
        return expiration.getTime() - new Date().getTime();
    }

    private Claims getClaims(String token) {
        return parse(token)
                .getBody();
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        try {
            parse(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new ApplicationException(AuthErrorCode.EXPIRED);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new ApplicationException(AuthErrorCode.INVALID_SIGNATURE);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new ApplicationException(AuthErrorCode.INVALID_JWT);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new ApplicationException(AuthErrorCode.UNSUPPORTED);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new ApplicationException(AuthErrorCode.EMPTY_CLAIM);
        }


    }

    public boolean isStartWithBearer(String bearerToken) {
        return bearerToken.startsWith(BEARER_PREFIX);
    }

    public boolean isRefreshTokenValidTime(long refreshTokenExpiration){
        return refreshTokenExpiration > REFRESH_TOKEN_VALID_TIME;
    }
}
