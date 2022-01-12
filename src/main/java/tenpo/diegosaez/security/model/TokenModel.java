package tenpo.diegosaez.security.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenModel {
	private String type;
	private String token;
	private LocalDateTime expiration;
}
