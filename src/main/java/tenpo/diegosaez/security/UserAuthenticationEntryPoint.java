package tenpo.diegosaez.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.core.util.MessageUtil;
import tenpo.diegosaez.exception.error.ApplicationError;

/**
 * 
 * Componente que nos permite interceptar el error de autenticación antes de que
 * le llegue al usuario.
 * 
 * @author diegosaez
 *
 */
@Component
@RequiredArgsConstructor
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final MessageUtil messageUtil;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = response.getWriter();
		var apiError = new ApplicationError();
		apiError.setStatus(HttpStatus.UNAUTHORIZED);
		apiError.setTimestamp(Instant.now().getEpochSecond());
		apiError.addError(messageUtil.getMessage("must.be.authenticated"));
		var mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		writer.print(mapper.writeValueAsString(apiError));
	}

}
