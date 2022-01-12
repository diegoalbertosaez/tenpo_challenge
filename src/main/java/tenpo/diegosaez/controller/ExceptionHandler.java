package tenpo.diegosaez.controller;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.core.util.MessageUtil;
import tenpo.diegosaez.exception.UserAlreadyExistsException;
import tenpo.diegosaez.exception.error.ApplicationError;

@RestControllerAdvice
@RequiredArgsConstructor
@Profile("!test")
public class ExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

	private final MessageUtil messageUtil;

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ApplicationError handleException(Exception ex) {
		LOGGER.error("Se produjo un error inesperado", ex);
		return getApplicationError(messageUtil.getMessage("unexpected.error"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ApplicationError handleException(AuthenticationException ex) {
		LOGGER.error("El usuario y/o la contraseña son incorrectos: {}", ex.getMessage());
		return getApplicationError(messageUtil.getMessage("wrong.username.or.password"), HttpStatus.UNAUTHORIZED);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ApplicationError handleException(UsernameNotFoundException ex) {
		LOGGER.error("Usuario no encontrado.", ex);
		return getApplicationError(messageUtil.getMessage("user.not.found"), HttpStatus.UNAUTHORIZED);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ApplicationError handleException(MethodArgumentNotValidException ex) {
		LOGGER.error("Error de validación: {}", ex.getMessage());
		return getApplicationError(getErrors(ex), HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ApplicationError handleException(UserAlreadyExistsException ex) {
		LOGGER.error("El usuario ya se encuentra registrado.", ex);
		return getApplicationError(messageUtil.getMessage("user.already.exists", ex.getUsername()),
				HttpStatus.CONFLICT);
	}

	private ApplicationError getApplicationError(String error, HttpStatus status) {
		var apiError = new ApplicationError();
		apiError.setStatus(status);
		apiError.setTimestamp(Instant.now().getEpochSecond());
		apiError.addError(error);
		return apiError;
	}
	
	private ApplicationError getApplicationError(List<String> errors, HttpStatus status) {
		var apiError = new ApplicationError();
		apiError.setStatus(status);
		apiError.setTimestamp(Instant.now().getEpochSecond());
		apiError.setErrors(errors);
		return apiError;
	}

	private List<String> getErrors(MethodArgumentNotValidException ex) {
		return ex.getAllErrors().stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList());
	}
}
