package tenpo.diegosaez.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@JsonIgnoreProperties(value = "password")
@ApiModel(value = "User", description = "Representa el alta usuario en la aplicación.")
public class UserSignupDTO extends UserDTO {
	@ApiModelProperty("Identificador")
	private Integer id;
}
