package kikaha.samples.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.*;

@RequiredArgsConstructor
class JwtToken {

    @Getter
    final String token;

    @JsonIgnore
    final Claims claims;
}