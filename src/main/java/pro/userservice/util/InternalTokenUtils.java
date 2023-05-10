package pro.userservice.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pro.userservice.exception.AuthenticationJwtException;
import pro.userservice.exception.ErrorCode;

import java.text.ParseException;
import java.util.Date;

@Component
@Slf4j
public class InternalTokenUtils {
    @Value("${auth.internalKeySign}")
    private String internalKeySign;
    @Value("${auth.expiredInSeconds}")
    private long expiredInSeconds;

    private RandomValueStringGenerator strGenerator;

    public InternalTokenUtils() {
        this.strGenerator = new RandomValueStringGenerator(200);
    }

    public String validateInternalJwt(String jwt) throws ParseException {
        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(jwt);
            MACVerifier verifier = new MACVerifier(internalKeySign);
            if (!signedJWT.verify(verifier)) {
                throw new AuthenticationJwtException(ErrorCode.TOKEN_INVALID, "Token sign invalid");
            }
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();
            boolean expired = expirationTime != null && expirationTime.before(new Date());
            if (expired) throw new AuthenticationJwtException(ErrorCode.TOKEN_EXPIRED);
        } catch (JOSEException | ParseException e) {
            log.error("{} parse token error {}", getClass().getSimpleName(), e);
            throw new AuthenticationJwtException(ErrorCode.TOKEN_INVALID ,"Token invalid, can not create verifier");
        }
        log.info("{} sign jwt {}", getClass().getSimpleName(), signedJWT.getJWTClaimsSet().getSubject());
        return signedJWT.getJWTClaimsSet().getSubject();
    }

    public String generateInternalJwt(String username) throws JOSEException {
        long currentTime = System.currentTimeMillis();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.HS256).build(),
                new JWTClaimsSet.Builder()
                        .subject(username)
                        .claim("data", strGenerator.generate())
                        .expirationTime(new Date(currentTime + expiredInSeconds * 1000))
                        .issueTime(new Date(currentTime))
                        .build());

        //Sign jwt
        signedJWT.sign(new MACSigner(internalKeySign));
        return signedJWT.serialize();
    }

    public String getUserDtoFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return (String) authentication.getPrincipal();
    }
}
