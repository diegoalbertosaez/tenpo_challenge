package tenpo.diegosaez.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import tenpo.diegosaez.controller.dto.LoginUserDTO;
import tenpo.diegosaez.controller.dto.TokenDTO;
import tenpo.diegosaez.controller.dto.mapper.LoginUserDTOMapper;
import tenpo.diegosaez.security.Constants;
import tenpo.diegosaez.security.model.LoginUserModel;
import tenpo.diegosaez.security.model.TokenModel;
import tenpo.diegosaez.security.service.LoginUserService;

@WebMvcTest(LoginUserController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginUserControllerTest extends BaseControllerTestConfiguration {

	@MockBean
	private LoginUserService service;

	@MockBean
	private LoginUserDTOMapper mapper;

	@Test
	void testLogin() throws Exception {
		when(service.login(any(LoginUserModel.class))).thenReturn(getDummyTokenModel());
		when(mapper.toDTO(any(TokenModel.class))).thenReturn(getDummyTokenDTO());
		when(mapper.toModel(any(LoginUserDTO.class))).thenReturn(getDummyLoginUserModel());

		mockMvc.perform(MockMvcRequestBuilders.post("/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(getUserDTOJson())).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.token", notNullValue()));
		verify(service).login(any(LoginUserModel.class));
	}

	@Test
	void testLogout() throws Exception {
		doNothing().when(service).logout(anyString());
		mockMvc.perform(MockMvcRequestBuilders.post("/users/logout").header(Constants.HEADER_AUTHORIZATION_KEY, getDummyToken())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}

	private TokenModel getDummyTokenModel() {
		return new TokenModel(Constants.HEADER_TOKEN_PREFIX, getDummyToken(), LocalDateTime.now());
	}

	private TokenDTO getDummyTokenDTO() {
		return new TokenDTO(Constants.HEADER_TOKEN_PREFIX, getDummyToken(), LocalDateTime.now());
	}

	private String getUserDTOJson() {
		LoginUserDTO loginUser = getDummyLoginUserDTO();
		return getJsonFrom(loginUser);
	}

	private LoginUserDTO getDummyLoginUserDTO() {
		return new LoginUserDTO("test", "pass");
	}

	private LoginUserModel getDummyLoginUserModel() {
		return new LoginUserModel("test", "pass");
	}

	private String getDummyToken() {
		return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWVnby5zYWV6IiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImV4cCI6MTY0MTg3MTQxOX0.VBZgXd6C87h5FjD_Ru_3zCEE0SBoDUU_hbZe2zF-6VMPgAwS6d64EFrsx-QRgKVSut0DHkumcx50dqnfFdcwPA";
	}
}
