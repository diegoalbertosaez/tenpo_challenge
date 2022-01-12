package tenpo.diegosaez.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import tenpo.diegosaez.controller.dto.ExecutionHistoryDTO;
import tenpo.diegosaez.controller.dto.mapper.ExecutionHistoryDTOMapper;
import tenpo.diegosaez.core.model.ExecutionHistoryModel;
import tenpo.diegosaez.core.model.ExecutionHistoryResult;
import tenpo.diegosaez.core.model.PageModel;
import tenpo.diegosaez.core.service.ExecutionHistoryService;

@WebMvcTest(ExecutionHistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class ExecutionHistoryControllerTest extends BaseControllerTestConfiguration{


	@MockBean
	private ExecutionHistoryService executionHistoryService;
	
	@MockBean
	private ExecutionHistoryDTOMapper executionHistoryDTOMapper;
	
	@Test
	void testFindAllPaginated() throws Exception {
		when(executionHistoryService.findAllPaginated(anyInt(), anyInt())).thenReturn(getDummyPageModel());
		when(executionHistoryDTOMapper.toDTO(anyList())).thenReturn(getDummyExecutionHistoriesDTO());
		UriComponents uriComponents = UriComponentsBuilder
		.fromPath("/execution_history")
		.queryParam("pageNumber", "0")
		.queryParam("size", "3")
		.build();
		
		mockMvc.perform(MockMvcRequestBuilders.get(uriComponents.toString())).andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements", equalTo(3))).andExpect(jsonPath("$.totalPages", equalTo(1)))
				.andExpect(jsonPath("$.elements", hasSize(3)));
		
		verify(executionHistoryService).findAllPaginated(anyInt(), anyInt());
		verify(executionHistoryDTOMapper).toDTO(anyList());
		
		
	}
	
	private PageModel<ExecutionHistoryModel> getDummyPageModel() {
		
		List<ExecutionHistoryModel> executionHistories = new ArrayList<>();		
		executionHistories.add(ExecutionHistoryModel.builder()
		.name("Test1")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.SUCCESS)
		.executionTime(2234)
		.build());
		
		executionHistories.add(ExecutionHistoryModel.builder()
		.name("Test2")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.ERROR)
		.executionTime(445)
		.build());
		
		executionHistories.add(ExecutionHistoryModel.builder()
		.name("Test3")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.SUCCESS)
		.executionTime(4456)
		.build());
		
		return new PageModel<>(executionHistories, 3L, 1);
		
	}
	
	private List<ExecutionHistoryDTO> getDummyExecutionHistoriesDTO(){
		List<ExecutionHistoryDTO> executionHistories = new ArrayList<>();		
		executionHistories.add(ExecutionHistoryDTO.builder()
		.name("Test1")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.SUCCESS.name())
		.executionTime(2234)
		.build());
		
		executionHistories.add(ExecutionHistoryDTO.builder()
		.name("Test2")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.ERROR.name())
		.executionTime(445)
		.build());
		
		executionHistories.add(ExecutionHistoryDTO.builder()
		.name("Test3")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.SUCCESS.name())
		.executionTime(4456)
		.build());
		
		return executionHistories;
	}

}
