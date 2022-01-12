package tenpo.diegosaez.core.service;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import tenpo.diegosaez.core.model.UserModel;
import tenpo.diegosaez.core.model.mapper.UserModelMapper;
import tenpo.diegosaez.data.entity.User;
import tenpo.diegosaez.data.repository.UserRepository;
import tenpo.diegosaez.exception.UserAlreadyExistsException;

class UserServiceTest {

	
	private UserRepository repository;
	private UserModelMapper mapper;
	private PasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setup() {
		repository = mock(UserRepository.class);
		mapper = mock(UserModelMapper.class);
		passwordEncoder = mock(PasswordEncoder.class);
	}
	
	@Test
	void testSignup() {
		when(repository.findByUsername(anyString())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(mapper.toEntity(any(UserModel.class))).thenReturn(getDummyUserEntity());
		when(mapper.toModel(any(User.class))).thenReturn(getDummyUserModel());
		when(repository.save(any(User.class))).thenReturn(getDummyUserEntity());
		var service = new UserServiceImpl(repository, mapper, passwordEncoder);
		var user = service.signup(getDummyUserModel());
		assertNotNull(user);
		assertEquals(1,user.getId());
		assertEquals("test",user.getUsername());
		assertEquals("prueba@gmail.com",user.getEmail());
		assertEquals("Test",user.getName());
		assertEquals("Test",user.getSurname());
		verify(repository).findByUsername(anyString());
		verify(passwordEncoder).encode(anyString());
		verify(mapper).toEntity(any(UserModel.class));
		verify(mapper).toModel(any(User.class));
		verify(repository).save(any(User.class));
		
	}

	@Test
	void testSignup_UserAlreadyExits_Then_Throw_UserAlreadyExistsException() {
		when(repository.findByUsername(anyString())).thenReturn(Optional.of(getDummyUserEntity()));
		var service = new UserServiceImpl(repository, mapper, passwordEncoder);
		assertThrows(UserAlreadyExistsException.class, () -> service.signup(getDummyUserModel()));
		verify(repository).findByUsername(anyString());
	}
	
	
	private UserModel getDummyUserModel() {
		return UserModel.builder()
				.id(1)
				.email("prueba@gmail.com")
				.username("test")
				.name("Test")
				.surname("Test")
				.password("TestPassword")
				.build();
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
	
	
}
