package tenpo.diegosaez.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import tenpo.diegosaez.controller.dto.UserDTO;
import tenpo.diegosaez.controller.dto.UserSignupDTO;
import tenpo.diegosaez.controller.dto.mapper.UserDTOMapper;
import tenpo.diegosaez.core.model.UserModel;
import tenpo.diegosaez.core.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest extends BaseControllerTestConfiguration {

	@MockBean
	private UserService userService;

	@MockBean
	private UserDTOMapper userMapper;
	
	@Test
	void testSignup() throws Exception {
		when(userMapper.toModel(any(UserDTO.class))).thenReturn(getDummyUserModel());
		when(userMapper.toDTO(any(UserModel.class))).thenReturn(getDummyUserSignupDTO());
		when(userService.signup(any(UserModel.class))).thenReturn(getDummyUserModel());
		mockMvc.perform(MockMvcRequestBuilders.post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(getUserDTOJson())).andExpect(status().isCreated()).andExpect(jsonPath("$.id", equalTo(1)));
		verify(userMapper).toModel(any(UserDTO.class));
		verify(userMapper).toDTO(any(UserModel.class));
		verify(userService).signup(any(UserModel.class));
	}
	
	@Test
	void testSignup_AllFieldNull_Then_Http_400_Error() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/users/signup").contentType(MediaType.APPLICATION_JSON)
				.content(getEmptyUserDTOJson())).andExpect(status().isBadRequest());
	}

	private UserDTO getDummyUserDTO() {
		return UserDTO.builder()
				.email("prueba@gmail.com")
				.username("test")
				.name("Test")
				.surname("Test")
				.password("TestPassword")
				.build();
	}

	private String getUserDTOJson() {
		UserDTO user = getDummyUserDTO();
		return getUserDTOJson(user);
	}
	
	private String getEmptyUserDTOJson() {
		return getUserDTOJson(new UserDTO());
	}
	
	private String getUserDTOJson(UserDTO user) {
		return getJsonFrom(user);
	}

	private UserModel getDummyUserModel() {
		return UserModel.builder()
				.email("prueba@gmail.com")
				.username("test")
				.name("Test")
				.surname("Test")
				.password("TestPassword")
				.build();
	}

	private UserSignupDTO getDummyUserSignupDTO() {
		return UserSignupDTO.builder()
				.id(1)
				.email("prueba@gmail.com")
				.username("test")
				.name("Test")
				.surname("Test")
				.password("TestPassword")
				.build();
	}

}
