package tenpo.diegosaez.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "User", description = "Representa un usuario de la aplicación.")
public class UserDTO {
	@ApiModelProperty("Nombre de usuario")
	@NotBlank(message = "{user.username.validation.empty}")
	private String username;

	@ApiModelProperty("Contraseña de usuario")
	@NotBlank(message = "{user.password.validation.empty}")
	@Size(min = 6, message = "{user.password.validation.length}")
	private String password;

	@ApiModelProperty("Nombre real de usuario")
	@NotBlank(message = "{user.name.validation.empty}")
	private String name;

	@ApiModelProperty("Apellido de usuario")
	@NotBlank(message = "{user.surname.validation.empty}")
	private String surname;

	@NotBlank(message = "{user.email.validation.empty}")
	@Pattern(regexp = ValidationConstants.EMAIL_REGEXP, message = "{user.email.validation.format.invalid}")
	private String email;
}
