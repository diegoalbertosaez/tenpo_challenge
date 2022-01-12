package tenpo.diegosaez.exception.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una respuesta en caso de error.
 * 
 * 
 * @author diegosaez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("Modelo de error")
public class ApplicationError {

	@ApiModelProperty(value = "Instante donde se produce el error", example = "1627925924")
	private Long timestamp;

	@ApiModelProperty(value = "Estado de solicitud", example = "BAD_REQUEST")
	private HttpStatus status;

	@ApiModelProperty(value = "Lista de errores", example = "[El id debe ser mayor que cero, El nombre es obligatorio]")
	private List<String> errors;

	/**
	 * Añade un error a la lista de errores.
	 * 
	 * @param error
	 */
	public void addError(String error) {
		if (Objects.isNull(errors)) {
			errors = new ArrayList<>();
		}
		errors.add(error);
	}

}
