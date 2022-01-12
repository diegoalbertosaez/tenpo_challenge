package tenpo.diegosaez.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import tenpo.diegosaez.core.model.AuthenticationUserDetails;
import tenpo.diegosaez.data.entity.User;
import tenpo.diegosaez.data.repository.UserRepository;
import tenpo.diegosaez.security.mapper.UserDetailsMapper;

class AuthenticationUserServiceDetailsTest {

	
	private UserRepository repository;
	private UserDetailsMapper mapper;
	
	@BeforeEach
	void setup() throws Exception {
		repository = mock(UserRepository.class);
		mapper = mock(UserDetailsMapper.class);
	}

	@Test
	void testLoadUserByUsername() {
		when(repository.findByUsername(anyString())).thenReturn(Optional.of(getDummyUserEntity()));		
		when(mapper.toModel(any(User.class))).thenReturn(getDummyAuthenticationUserDetails());
		AuthenticationUserServiceDetails service = new AuthenticationUserServiceDetails(repository, mapper);
		UserDetails userDetails = service.loadUserByUsername("test");
		assertNotNull(userDetails);
		assertEquals("test", userDetails.getUsername());
		verify(repository).findByUsername(anyString());
		verify(mapper).toModel(any(User.class));
	}
	
	@Test
	void testLoadUserByUsername_User_Not_Found_Then_Throw_UsernameNotFoundException() {
		when(repository.findByUsername(anyString())).thenReturn(Optional.empty());		
		AuthenticationUserServiceDetails service = new AuthenticationUserServiceDetails(repository, mapper);
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("test")); 
		verify(repository).findByUsername(anyString());
	}
	
	
	
	private User getDummyUserEntity() {
		return User.builder()
				.id(1)
				.email("prueba@gmail.com")
				.username("test")
				.name("Test")
				.surname("Test")
				.password("TestPassword")
				.build();
	}
	
	private AuthenticationUserDetails getDummyAuthenticationUserDetails() {
		AuthenticationUserDetails userDetails = new AuthenticationUserDetails();		
		userDetails.setUsername("test");		
		return userDetails;
	}

}
