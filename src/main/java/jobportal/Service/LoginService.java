package jobportal.Service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class LoginService {
    @Value("${jwt.issuer}" )
    String issuer;
    @Value("${jwt.expiry}" )
    Long Expiry;
    private final JwtEncoder jwtEncoder;
    public LoginService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    List<String> authorities=authentication.getAuthorities()
            .stream()
            .map(authority->authority.getAuthority())
            .toList();
    JwtClaimsSet claims= JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(Expiry))
            .subject(authentication.getName())
            .claim("authorities",authorities)
            .build();

    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
}
}
