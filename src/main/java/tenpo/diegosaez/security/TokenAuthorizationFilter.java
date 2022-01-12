package tenpo.diegosaez.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.benmanes.caffeine.cache.Cache;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.exception.InvalidTokenException;

/**
 * Filtro de peticiones para validar seguridad.
 * 
 * @author diegosaez
 *
 */
@Component
@RequiredArgsConstructor
@Profile("!test")
public class TokenAuthorizationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthorizationFilter.class);

	private final UserDetailsService userDetailsService;
	private final TokenManager tokenManager;
	private final Cache<String, String> tokenBlacklistCache;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOGGER.debug("Filtrando autorización");
		var authorizationHeaderToken = request.getHeader(Constants.HEADER_AUTHORIZATION_KEY);
		if (Objects.nonNull(authorizationHeaderToken) && !authorizationHeaderToken.isBlank()) {
			authorizationHeaderToken = SecurityUtil.removePrefixFromToken(authorizationHeaderToken);
			var username = tokenManager.extractUsername(authorizationHeaderToken);
			validateTokenBlacklist(username);
			if (Objects.nonNull(username) && !username.isBlank()) {
				var userDetails = userDetailsService.loadUserByUsername(username);
				if (tokenManager.validateToken(authorizationHeaderToken, userDetails)) {
					LOGGER.debug("Token válido");
					var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
							userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private void validateTokenBlacklist(String username) {
		String blacklistedToken = tokenBlacklistCache.getIfPresent(username);
		if (Objects.nonNull(blacklistedToken)) {
			throw new InvalidTokenException("Token inválido.");
		}
	}

}
