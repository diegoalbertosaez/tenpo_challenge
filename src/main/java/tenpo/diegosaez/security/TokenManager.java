package tenpo.diegosaez.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tenpo.diegosaez.security.model.TokenModel;

/**
 * 
 * Manager para gestionar token de usuario.
 * 
 * @author diegosaez
 *
 */
@Component
public class TokenManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenManager.class);

	/*
	 * Duración de 1 hora
	 */
	private static final Integer EXPIRATION_TIME = 1000 * 60 * 60;
	private static final String AUTHORITIES = "authorities";
	private final String encodedSecretKey;
	private static final String JWT_KEY = "tenpo_challenge";

	public TokenManager() {
		encodedSecretKey = Base64.getEncoder().encodeToString(JWT_KEY.getBytes());
	}

	/**
	 * Crea un tokens a partir de los detalles de un usuario.
	 * 
	 * @param userDetails
	 * @return token.
	 */
	public TokenModel createToken(UserDetails userDetails) {
		LOGGER.debug("Creando token para @user {}", userDetails.getUsername());
		String username = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();		
		var expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);		
		var token = Jwts.builder().setSubject(username).claim(AUTHORITIES, authorities)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, encodedSecretKey).compact();				
		return 	TokenModel.builder()
				.token(token)
				.type(Constants.HEADER_TOKEN_PREFIX)
				.expiration(LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault()))
				.build();
	}

	/**
	 * Valida un token.
	 * 
	 * @param token
	 * @param userDetails
	 * @return verdadero o falso.
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		LOGGER.debug("Validando token");
		String username = extractUsername(token);
		return (userDetails.getUsername().equals(username) && !hasTokenExpired(token));

	}

	/**
	 * Obtiene el nombre de usuario de un token.
	 * 
	 * @param token
	 * @return nombre de usuario.
	 */
	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(encodedSecretKey).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Determina si un token a expirado.
	 * 
	 * @param token
	 * @return verdadero o falso.
	 */
	private Boolean hasTokenExpired(String token) {
		LOGGER.debug("Validando expiración de token");
		return Jwts.parser().setSigningKey(encodedSecretKey).parseClaimsJws(token).getBody().getExpiration()
				.before(new Date());
	}
}
