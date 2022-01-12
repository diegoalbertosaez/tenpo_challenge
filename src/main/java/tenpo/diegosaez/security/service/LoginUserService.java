package tenpo.diegosaez.security.service;

import tenpo.diegosaez.security.model.LoginUserModel;
import tenpo.diegosaez.security.model.TokenModel;

public interface LoginUserService {

	/**
	 * Realiza la autenticación de un usuario y devuelve un token.
	 * 
	 * @param loginUser - Credenciales de usuario.
	 * @return {@link TokenModel}
	 */
	TokenModel login(LoginUserModel loginUser);

	/**
	 * Invalida un token de usuario.
	 * @param token - Token a invalidar
	 */
	void logout(String token);

}
