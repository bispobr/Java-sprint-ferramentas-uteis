package ferramentas.dto.common;

import lombok.Builder;
import java.util.List;

@Builder
public record JWTUserData(Long userId, String email, List<String> roles) {}
