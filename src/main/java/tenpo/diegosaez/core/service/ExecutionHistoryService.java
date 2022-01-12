package tenpo.diegosaez.core.service;

import javax.validation.constraints.NotNull;

import tenpo.diegosaez.core.model.ExecutionHistoryModel;
import tenpo.diegosaez.core.model.PageModel;

public interface ExecutionHistoryService {

	/**
	 * Obtiene una página de historia de ejecuciones.
	 * @param pageNumber - Número de página
	 * @param size - Tamaño de página.
	 * @return {@link PageModel}
	 */
	PageModel<ExecutionHistoryModel> findAllPaginated(Integer pageNumber, Integer size);

	/**
	 * Guarda un historia de ejecución.
	 * @param executionHistory - Historial a guardar
	 * @return {@link ExecutionHistoryModel}
	 */
	ExecutionHistoryModel save(@NotNull ExecutionHistoryModel executionHistory);

}
