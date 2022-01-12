package tenpo.diegosaez.controller.dto;

import javax.validation.constraints.NotNull;

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
@ApiModel(value = "SumOperation", description = "Representa una suma de 2 valores enteros.")
public class SumDTO {
	
	@ApiModelProperty("Primer operando de operación")
	@NotNull(message = "{operation.sum.value1.empty}")
	private Integer value1;

	@ApiModelProperty("Segundo operando de operación")
	@NotNull(message = "{operation.sum.value2.empty}")
	private Integer value2;

}
