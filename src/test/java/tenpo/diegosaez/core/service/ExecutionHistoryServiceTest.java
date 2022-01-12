package tenpo.diegosaez.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import tenpo.diegosaez.core.model.ExecutionHistoryModel;
import tenpo.diegosaez.core.model.mapper.ExecutionHistoryModelMapper;
import tenpo.diegosaez.data.entity.ExecutionHistory;
import tenpo.diegosaez.data.entity.ExecutionHistoryResult;
import tenpo.diegosaez.data.repository.ExecutionHistoryRepository;

class ExecutionHistoryServiceTest {

	private ExecutionHistoryRepository repository;
	private ExecutionHistoryModelMapper mapper;
	
	
	@BeforeEach
	void setup() {
		repository = mock(ExecutionHistoryRepository.class);
		mapper = mock(ExecutionHistoryModelMapper.class);
	}
	
	@Test
	void testFindAllPaginated() {
		when(repository.findAll(any(Pageable.class))).thenReturn(getDummyPageEntity());
		when(mapper.toModel(anyList())).thenReturn(getDummyExecutionHistoriesModel());
		var service = new ExecutionHistoryServiceImpl(repository, mapper);
		var page = service.findAllPaginated(0, 10);
		assertNotNull(page);
		assertNotNull(page.getElements());
		assertEquals(3L, page.getTotalElements());
		assertEquals(3, page.getElements().size());
		verify(repository).findAll(any(Pageable.class));
		verify(mapper).toModel(anyList());
	}

	@Test
	void testSave() {
		when(mapper.toEntity(any(ExecutionHistoryModel.class))).thenReturn(getExecutionHistoryEntity());
		when(mapper.toModel(any(ExecutionHistory.class))).thenReturn(getExecutionHistoryModel());
		when(repository.save(any(ExecutionHistory.class))).thenReturn(getExecutionHistoryEntity());
		var service = new ExecutionHistoryServiceImpl(repository, mapper);
		var executionHistory = service.save(getExecutionHistoryModel());
		assertNotNull(executionHistory);
		assertEquals(4, executionHistory.getId());		
	}
	
	private Page<ExecutionHistory> getDummyPageEntity(){				
		return new PageImpl<>(getDummyExecutionHistories(), PageRequest.of(0, 10), 3L);
	}

	
	private List<ExecutionHistory> getDummyExecutionHistories(){
		List<ExecutionHistory> executionHistories = new ArrayList<>();		
		executionHistories.add(ExecutionHistory.builder()
		.id(1)
		.name("Test1")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.SUCCESS)
		.executionTime(2234)
		.build());
		
		executionHistories.add(ExecutionHistory.builder()
		.id(2)
		.name("Test2")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.ERROR)
		.executionTime(445)
		.build());
		
		executionHistories.add(ExecutionHistory.builder()
		.name("Test3")
		.id(3)
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.SUCCESS)
		.executionTime(4456)
		.build());
		
		return executionHistories;
	}
	
	private List<ExecutionHistoryModel> getDummyExecutionHistoriesModel() {
		
		List<ExecutionHistoryModel> executionHistories = new ArrayList<>();		
		executionHistories.add(ExecutionHistoryModel.builder()
		.id(1)
		.name("Test1")
		.executionDate(LocalDateTime.now())
		.executionResult(tenpo.diegosaez.core.model.ExecutionHistoryResult.SUCCESS)
		.executionTime(2234)
		.build());
		
		executionHistories.add(ExecutionHistoryModel.builder()
		.id(2)
		.name("Test2")
		.executionDate(LocalDateTime.now())
		.executionResult(tenpo.diegosaez.core.model.ExecutionHistoryResult.ERROR)
		.executionTime(445)
		.build());
		
		executionHistories.add(ExecutionHistoryModel.builder()
		.id(3)
		.name("Test3")
		.executionDate(LocalDateTime.now())
		.executionResult(tenpo.diegosaez.core.model.ExecutionHistoryResult.SUCCESS)
		.executionTime(4456)
		.build());
		
		return executionHistories;		
	}
	
	private ExecutionHistory getExecutionHistoryEntity() {
		return ExecutionHistory.builder()
		.id(4)
		.name("Test2")
		.executionDate(LocalDateTime.now())
		.executionResult(ExecutionHistoryResult.ERROR)
		.executionTime(445)
		.build();
	}
	
	private ExecutionHistoryModel getExecutionHistoryModel() {
		return ExecutionHistoryModel.builder()
		.id(4)
		.name("Test2")
		.executionDate(LocalDateTime.now())
		.executionResult(tenpo.diegosaez.core.model.ExecutionHistoryResult.ERROR)
		.executionTime(445)
		.build();
	}
	
}
