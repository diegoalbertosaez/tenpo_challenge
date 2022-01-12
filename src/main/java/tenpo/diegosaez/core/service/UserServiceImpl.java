package tenpo.diegosaez.core.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.core.model.UserModel;
import tenpo.diegosaez.core.model.mapper.UserModelMapper;
import tenpo.diegosaez.data.repository.UserRepository;
import tenpo.diegosaez.exception.UserAlreadyExistsException;

/**
 * Servicio de core para gestionar usuarios.
 * 
 * @author diegosaez
 *
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;
	private final UserModelMapper userModelMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserModel signup(@NotNull UserModel user) {
		LOGGER.debug("Registrando usuario {}", user.getUsername());
		checkIfUserAlreadyExists(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		var userEntity = userModelMapper.toEntity(user);
		userEntity = userRepository.save(userEntity);
		return userModelMapper.toModel(userEntity);
	}

	private void checkIfUserAlreadyExists(UserModel user) {
		var userOptional = userRepository.findByUsername(user.getUsername());
		if (userOptional.isPresent()) {
			var errorMessage = "El usuario ".concat(user.getUsername()).concat(" ya se encuentra registrado.");
			LOGGER.error(errorMessage);
			throw new UserAlreadyExistsException(user.getUsername(), errorMessage);
		}
	}
}
