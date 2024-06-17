package com.szs.refundinquiryservice.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.szs.refundinquiryservice.common.domain.ApiResponseCode;
import com.szs.refundinquiryservice.common.domain.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

import static java.util.Date.from;

/**
 * @author : km.oh
 * @date : 6/15/24
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private static String USER_ID_CLAIM_NAME = "userId";

    @Value("${secret.jwt.expiredMin}")
    private int expiredMin;

    @Value("${secret.jwt.secret}")
    private String secret;

    @Value("${secret.jwt.issuer}")
    private String issuer;

    public String extractUserIdFromToken(final String accessToken) {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(accessToken);

        final DecodedJWT decodedJWT = JWT.decode(accessToken);

        Map<String, Claim> claims =
                Optional.of(decodedJWT.getClaims()).orElseThrow(() -> new BusinessException(ApiResponseCode.EXPIRED_ACCESS_TOKEN));
        if (ObjectUtils.isEmpty(claims.get(USER_ID_CLAIM_NAME))) {
            throw new BusinessException(ApiResponseCode.EXPIRED_ACCESS_TOKEN);
        }
        return String.valueOf(claims.get(USER_ID_CLAIM_NAME));
    }

    public String issueAccessToken(final Integer userId) {
        final LocalDateTime expireDatetime = LocalDateTime.now().plusMinutes(expiredMin);

        return createToken(userId, expireDatetime);
    }

    private String createToken(final Integer userId, final LocalDateTime expireDt) {

        try {
            JWTCreator.Builder jwtCreatorBuilder = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("userId", userId);

            return jwtCreatorBuilder
                    .withExpiresAt(from(expireDt.toInstant(ZoneOffset.ofHours(9))))
                    .withIssuedAt(from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(9))))
                    .sign(getAlgorithm());
        } catch (JWTCreationException createEx) {
            throw new BusinessException(ApiResponseCode.UNAUTHENTICATED);
        }
    }

    private Algorithm getAlgorithm() {
        try {
            return Algorithm.HMAC256(secret);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ApiResponseCode.UNAUTHENTICATED);
        }
    }

}
