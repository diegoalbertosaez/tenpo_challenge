package tenpo.diegosaez.controller.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "Token", description = "Representa un token de usuario.")
public class TokenDTO {
	
	@ApiModelProperty(value="Tipo", example = "Bearer")
	private String type;
	
	@ApiModelProperty("Nombre de usuario")
	private String token;
	
	@ApiModelProperty("Nombre de usuario")
	private LocalDateTime expiration;
}
