package tenpo.diegosaez.security;

/**
 * Clase util para operaciones de seguridad.
 * @author diegosaez
 *
 */
public class SecurityUtil {
	
	private SecurityUtil() {
	}
	
	/**
	 * Remueve el prefijo Bearer de token.
	 * @param token - Token a procesar.
	 * @return token sin prefijo.
	 */
	public static String removePrefixFromToken(String token) {
		token = token.replace(Constants.HEADER_TOKEN_PREFIX, "");
		return token.trim();
	}
}
