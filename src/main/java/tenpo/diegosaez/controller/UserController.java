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
import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.annotations.ExecutionHistory;
import tenpo.diegosaez.controller.dto.UserDTO;
import tenpo.diegosaez.controller.dto.UserSignupDTO;
import tenpo.diegosaez.controller.dto.mapper.UserDTOMapper;
import tenpo.diegosaez.controller.util.ApiUrlConstants;
import tenpo.diegosaez.controller.util.ExecutionHistoryConstants;
import tenpo.diegosaez.core.service.UserService;
import tenpo.diegosaez.exception.error.ApplicationError;

/**
 * Controller para gestionar usuarios.
 * @author diegosaez
 *
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "Usuarios", produces = "application/json")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	private final UserService userService;
	private final UserDTOMapper userMapper;

	@PostMapping(ApiUrlConstants.USER_SIGNUP)
	@ExecutionHistory(ExecutionHistoryConstants.SIGNUP)
	@ApiOperation(value = "Permite registrar un usuario en la aplicación.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = UserSignupDTO.class),
			@ApiResponse(code = 500, message = "Error inesperado", response = ApplicationError.class),
			@ApiResponse(code = 409, message = "Conflicto", response = ApplicationError.class)})
	public ResponseEntity<UserSignupDTO> signup(@Validated @RequestBody UserDTO user) {
		LOGGER.debug("Registrando usuario {}", user.getUsername());
		var userModel = userService.signup(userMapper.toModel(user));
		return new ResponseEntity<>(userMapper.toDTO(userModel), HttpStatus.CREATED);
	}

}
