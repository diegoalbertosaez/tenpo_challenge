package tenpo.diegosaez.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.annotations.ExecutionHistory;
import tenpo.diegosaez.controller.dto.LoginUserDTO;
import tenpo.diegosaez.controller.dto.TokenDTO;
import tenpo.diegosaez.controller.dto.mapper.LoginUserDTOMapper;
import tenpo.diegosaez.controller.util.ApiUrlConstants;
import tenpo.diegosaez.controller.util.ExecutionHistoryConstants;
import tenpo.diegosaez.exception.error.ApplicationError;
import tenpo.diegosaez.security.Constants;
import tenpo.diegosaez.security.service.LoginUserService;

/**
 * Controller para gestionar login y logout de usuarios.
 * @author diegosaez
 *
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
@Api(tags = "Usuarios", produces = "application/json")
public class LoginUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserController.class);
	
	private final LoginUserService loginUserService;
	private final LoginUserDTOMapper loginUserDTOMapper;

	@PostMapping(ApiUrlConstants.USER_LOGIN)
	@ExecutionHistory(ExecutionHistoryConstants.LOGIN)
	@ApiOperation(value = "Permite al usuario hacer login y obtener un token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TokenDTO.class),
			@ApiResponse(code = 500, message = "Error inesperado", response = ApplicationError.class),
			@ApiResponse(code = 401, message = "No autorizado", response = ApplicationError.class)})
	public ResponseEntity<TokenDTO> login(@Validated @RequestBody LoginUserDTO loginUser) {
		LOGGER.debug("Login para @usuario {}", loginUser.getUsername());
		var tokenModel = loginUserService.login(loginUserDTOMapper.toModel(loginUser));
		return new ResponseEntity<>(loginUserDTOMapper.toDTO(tokenModel), HttpStatus.OK);
	}

	@PostMapping(ApiUrlConstants.USER_LOGOUT)
	@ExecutionHistory(ExecutionHistoryConstants.LOGOUT)
	@ApiOperation(value = "Permite al usuario hacer logout invalidando su token", authorizations = { @Authorization(value = "Token") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = TokenDTO.class),
			@ApiResponse(code = 500, message = "Error inesperado", response = ApplicationError.class),
			@ApiResponse(code = 401, message = "No autorizado", response = ApplicationError.class)})
	public ResponseEntity<Void> logout(@ApiParam(hidden = true) @RequestHeader(name = Constants.HEADER_AUTHORIZATION_KEY) String token) {
		LOGGER.debug("Logout de usuario");
		loginUserService.logout(token);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
