package tenpo.diegosaez.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import tenpo.diegosaez.annotations.ExecutionHistory;
import tenpo.diegosaez.controller.dto.ExecutionHistoryDTO;
import tenpo.diegosaez.controller.dto.PageDTO;
import tenpo.diegosaez.controller.dto.mapper.ExecutionHistoryDTOMapper;
import tenpo.diegosaez.controller.util.ApiUrlConstants;
import tenpo.diegosaez.controller.util.ExecutionHistoryConstants;
import tenpo.diegosaez.core.service.ExecutionHistoryService;
import tenpo.diegosaez.exception.error.ApplicationError;

/**
 * 
 * Controller para gestionar historial de ejecución.
 * @author diegosaez
 *
 */
@RestController
@AllArgsConstructor
@Api(tags = "Historial de ejecución", produces = "application/json")
public class ExecutionHistoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionHistoryController.class);
	
	private final ExecutionHistoryService executionHistoryService;
	private final ExecutionHistoryDTOMapper executionHistoryDTOMapper;

	@GetMapping(ApiUrlConstants.EXECUTION_HISTORY_PAGINATED)
	@ExecutionHistory(ExecutionHistoryConstants.EXECUTION_HISTORY_PAGINATED)
	@ApiOperation(value = "Permite obtener una página de historial de ejecución")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = PageDTO.class),
			@ApiResponse(code = 500, message = "Error inesperdado", response = ApplicationError.class) })
	public ResponseEntity<PageDTO<ExecutionHistoryDTO>> findAllPaginated(@ApiParam("Número de paǵina (desde 0)") @RequestParam Integer pageNumber,
			@ApiParam("Tamaño de página") @RequestParam Integer size) {
		LOGGER.debug("Inicio búsqueda historial de ejecuciones paginado: número de paǵina {}, tamaño {}", pageNumber, size);
		var page = executionHistoryService.findAllPaginated(pageNumber, size);
		var pageResponse = new PageDTO<>(executionHistoryDTOMapper.toDTO(page.getElements()), page.getTotalElements(),
				page.getTotalPages());
		return new ResponseEntity<>(pageResponse, HttpStatus.OK);
	}

}
