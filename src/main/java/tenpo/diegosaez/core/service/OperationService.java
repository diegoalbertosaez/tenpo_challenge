package tenpo.diegosaez.core.service;

import tenpo.diegosaez.core.model.SumModel;

public interface OperationService {

	/**
	 * Permite sumar 2 valores enteros y obtener el resultado.
	 * @param sum - Objeto suma con los valores a sumar.
	 * @return int - resultado
	 */
	int sum(SumModel sum);

}
