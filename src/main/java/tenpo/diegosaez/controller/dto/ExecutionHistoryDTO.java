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
@ApiModel(value = "ExecutionHistory", description = "Representa un historial de ejecución.")
public class ExecutionHistoryDTO {
	@ApiModelProperty("Identificador")
	private Integer id;
	
	@ApiModelProperty("Nombre")
	private String name;
	
	@ApiModelProperty("Fecha de ejecución")
	private LocalDateTime executionDate;
	
	@ApiModelProperty("Tiempo de ejecución en milisegundos")
	private Integer executionTime;
	
	@ApiModelProperty(value = "Resultado de ejecución", example = "SUCCESS")
	private String executionResult;
}
