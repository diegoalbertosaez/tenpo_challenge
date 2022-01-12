package tenpo.diegosaez.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.benmanes.caffeine.cache.Cache;

import tenpo.diegosaez.core.model.AuthenticationUserDetails;
import tenpo.diegosaez.security.Constants;
import tenpo.diegosaez.security.TokenManager;
import tenpo.diegosaez.security.model.LoginUserModel;
import tenpo.diegosaez.security.model.TokenModel;

class LoginUserServiceTest {

	private TokenManager tokenManager;
	private AuthenticationManager authenticationManager;
	private Cache<String, String> cache;
	
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	void setup() throws Exception {
		tokenManager = mock(TokenManager.class);
		authenticationManager = mock(AuthenticationManager.class);
		cache = mock(Cache.class);		
	}

	@Test
	void testLogin() {
		UsernamePasswordAuthenticationToken passwordAuthenticationToken = getDummyUsernamePasswordAuthenticationTokenMock();
		when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(passwordAuthenticationToken);
		when(tokenManager.createToken(any(UserDetails.class))).thenReturn(getDummyTokenModel());
		var service = new LoginUserServiceImpl(tokenManager, authenticationManager, cache);		
		TokenModel tokenModel = service.login(LoginUserModel.builder().username("test").password("test").build());
		assertNotNull(tokenModel);
		assertEquals("Bearer", tokenModel.getType());
		assertEquals("token", tokenModel.getToken());
		assertNotNull(tokenModel.getExpiration());
		verify(authenticationManager).authenticate(any(Authentication.class));
		verify(tokenManager).createToken(any(UserDetails.class));
		verify(passwordAuthenticationToken).getPrincipal();
	}

	@Test
	void testLogout() {
		when(tokenManager.extractUsername(anyString())).thenReturn("test");
		doNothing().when(cache).put(anyString(), anyString());
		var service = new LoginUserServiceImpl(tokenManager, authenticationManager, cache);		
		service.logout("token");
		verify(tokenManager).extractUsername(anyString());
		verify(cache).put(anyString(), anyString());
	}
	
	private UsernamePasswordAuthenticationToken getDummyUsernamePasswordAuthenticationTokenMock() {
		var authentication = mock(UsernamePasswordAuthenticationToken.class);
		when(authentication.getPrincipal()).thenReturn(getDummyAuthenticationUserDetails());
		return authentication;
	}

	
	private TokenModel getDummyTokenModel() {
		return new TokenModel(Constants.HEADER_TOKEN_PREFIX, "token", LocalDateTime.now());
	}
	
	private AuthenticationUserDetails getDummyAuthenticationUserDetails() {
		return new AuthenticationUserDetails("test", "test");
	}
	
}
