package tenpo.diegosaez.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.annotations.ExecutionHistory;
import tenpo.diegosaez.controller.dto.SumDTO;
import tenpo.diegosaez.controller.dto.mapper.OperationMapper;
import tenpo.diegosaez.controller.util.ApiUrlConstants;
import tenpo.diegosaez.controller.util.ExecutionHistoryConstants;
import tenpo.diegosaez.core.service.OperationService;
import tenpo.diegosaez.exception.error.ApplicationError;

/**
 * Controller para gestionar operaciones matemáticas.
 * @author diegosaez
 *
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "Operaciones", produces = "application/json")
public class OperationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);
	
	private final OperationService operationService;
	private final OperationMapper operationMapper;

	@PostMapping(ApiUrlConstants.OPERATION_SUM)
	@ExecutionHistory(ExecutionHistoryConstants.SUM)
	@ApiOperation(value = "Permite sumar 2 número enteros y obtener el resultado.", authorizations = {
			@Authorization(value = "Token") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Integer.class),
			@ApiResponse(code = 500, message = "Error inesperado", response = ApplicationError.class),
			@ApiResponse(code = 401, message = "No autorizado", response = ApplicationError.class)})
	public ResponseEntity<Integer> sum(@Validated @RequestBody SumDTO sum) {
		LOGGER.debug("Sumando valores {} + {}", sum.getValue1(), sum.getValue2());
		return new ResponseEntity<>(operationService.sum(operationMapper.toModel(sum)), HttpStatus.OK);
	}

}
