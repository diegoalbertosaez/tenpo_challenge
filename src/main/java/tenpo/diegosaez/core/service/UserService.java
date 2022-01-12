package tenpo.diegosaez.core.service;

import javax.validation.constraints.NotNull;

import tenpo.diegosaez.core.model.UserModel;

public interface UserService {

	/**
	 * Permite dar de alta un nuevo usuario. Si el usuario ya se encuentra registrado se lanza una runtime exception UserAlreadyExistsException.
	 * @param user
	 * @return {@link UserModel}
	 */
	UserModel signup(@NotNull UserModel user);

}
