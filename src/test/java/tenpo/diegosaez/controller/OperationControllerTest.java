package tenpo.diegosaez.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import tenpo.diegosaez.controller.dto.SumDTO;
import tenpo.diegosaez.controller.dto.mapper.OperationMapper;
import tenpo.diegosaez.core.model.SumModel;
import tenpo.diegosaez.core.service.OperationService;


@WebMvcTest(OperationController.class)
@AutoConfigureMockMvc(addFilters = false)
class OperationControllerTest extends BaseControllerTestConfiguration{

	@MockBean
	private OperationService operationService;
	
	@MockBean
	private OperationMapper operationMapper;
		
	@Test
	void testSum() throws Exception {
		when(operationService.sum(any(SumModel.class))).thenReturn(8);
		when(operationMapper.toModel(any(SumDTO.class))).thenReturn(getDummySumModel());
		mockMvc.perform(MockMvcRequestBuilders.post("/operation/sum").contentType(MediaType.APPLICATION_JSON)
				.content(getSumDTOJson())).andExpect(status().isOk()).andExpect(jsonPath("$", equalTo(8)));
	}
	
	
	@Test
	void testSum_AllFieldNull_Then_Http_400_Error() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/operation/sum").contentType(MediaType.APPLICATION_JSON)
				.content(getEmptySumDTOJson())).andExpect(status().isBadRequest());
	}
	
	private SumModel getDummySumModel() {
		return SumModel.builder().value1(5).value2(3).build();		
	}

	private String getSumDTOJson() {
		return getSumDTOJson(getDummySumDTO());
	}
	
	private String getSumDTOJson(SumDTO sum) {
		
		return getJsonFrom(sum);
	}
	
	private String getEmptySumDTOJson() {
		return getSumDTOJson(new SumDTO());
	}
	
	private SumDTO getDummySumDTO() {
		return SumDTO.builder().value1(5).value2(3).build();		
	}
	
}
