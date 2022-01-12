package tenpo.diegosaez.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.core.model.AuthenticationUserDetails;
import tenpo.diegosaez.data.repository.UserRepository;
import tenpo.diegosaez.security.mapper.UserDetailsMapper;

/**
 * Servicio seguridad que gestiona los detalles de un usuario autenticado.
 * 
 * @author diegosaez
 *
 */
@Service
@RequiredArgsConstructor
public class AuthenticationUserServiceDetails implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUserServiceDetails.class);

	private final UserRepository userRepository;
	private final UserDetailsMapper userDetailsMapper;

	/**
	 * Permite obtener los detalles de un usuario por su nombre de usuario.
	 * 
	 * @param username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Buscando usuario con @name: {}", username);
		AuthenticationUserDetails authenticationUserDetails = null;
		var userOptional = userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			var user = userOptional.get();
			authenticationUserDetails = userDetailsMapper.toModel(user);
		} else {
			throw new UsernameNotFoundException("No se encuentra el usuario.");
		}
		return authenticationUserDetails;
	}
}
