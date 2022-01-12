package tenpo.diegosaez.security.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.core.model.AuthenticationUserDetails;
import tenpo.diegosaez.security.SecurityUtil;
import tenpo.diegosaez.security.TokenManager;
import tenpo.diegosaez.security.model.LoginUserModel;
import tenpo.diegosaez.security.model.TokenModel;

/**
 * Servicio de core de seguridad para permitir hacer login a un usuario.
 * @author diegosaez
 *
 */
@Service
@RequiredArgsConstructor
public class LoginUserServiceImpl implements LoginUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserServiceImpl.class);

	private final TokenManager tokenManager;
	private final AuthenticationManager authenticationManager;
	private final Cache<String, String> tokenBlacklistCache;


	@Override
	public TokenModel login(LoginUserModel loginUser) {
		LOGGER.debug("Authenticando usuario, @username: {}", loginUser.getUsername());
		var authenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
				loginUser.getPassword());
		var authenticate = authenticationManager.authenticate(authenticationToken);
		return tokenManager.createToken((AuthenticationUserDetails) authenticate.getPrincipal());
	}

	@Override
	public void logout(String token) {
		if (Objects.nonNull(token) && !token.isBlank()) {
			token = SecurityUtil.removePrefixFromToken(token);
			var username = tokenManager.extractUsername(token);
			tokenBlacklistCache.put(username, token);
		}
	}

}
