package tenpo.diegosaez.core.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.core.model.ExecutionHistoryModel;
import tenpo.diegosaez.core.model.PageModel;
import tenpo.diegosaez.core.model.mapper.ExecutionHistoryModelMapper;
import tenpo.diegosaez.data.repository.ExecutionHistoryRepository;

/**
 * Servicio de core para gestionar historial de ejecución.
 * @author diegosaez
 *
 */
@Service
@RequiredArgsConstructor
public class ExecutionHistoryServiceImpl implements ExecutionHistoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionHistoryServiceImpl.class);

	private final ExecutionHistoryRepository executionHistoryRepository;
	private final ExecutionHistoryModelMapper executionHistoryModelMapper;

	@Override
	public PageModel<ExecutionHistoryModel> findAllPaginated(@NotNull Integer pageNumber, @NotNull Integer size) {
		LOGGER.debug("Inicio búsqueda historial de ejecuciones paginado: número de paǵina {}, tamaño {}", pageNumber, size);
		var page = executionHistoryRepository.findAll(PageRequest.of(pageNumber, size));
		return new PageModel<>(executionHistoryModelMapper.toModel(page.getContent()), page.getTotalElements(),
				page.getTotalPages());
	}

	@Override
	@Transactional
	public ExecutionHistoryModel save(@NotNull ExecutionHistoryModel executionHistory) {
		LOGGER.debug("Guardando historial de ejecución {}", executionHistory.getName());
		var executionHistoryEntity = executionHistoryRepository.save(executionHistoryModelMapper.toEntity(executionHistory));		
		return executionHistoryModelMapper.toModel(executionHistoryEntity);
	}

}
