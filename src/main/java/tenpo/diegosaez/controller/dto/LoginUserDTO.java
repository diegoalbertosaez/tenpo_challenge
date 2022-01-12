package tenpo.diegosaez.controller.dto;

import javax.validation.constraints.NotBlank;

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
@ApiModel(value = "LoginUser", description = "Representa un login de usuario.")
public class LoginUserDTO {
	
	@ApiModelProperty(value = "Nombre de usuario", position = 1)
	@NotBlank(message = "{login.username.validation.empty}")
	private String username;

	@ApiModelProperty(value = "Contraseña de usuario", position = 2)
	@NotBlank(message = "{login.password.validation.empty}")
	private String password;
}
