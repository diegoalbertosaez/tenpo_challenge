package tenpo.diegosaez.core.service;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tenpo.diegosaez.core.model.SumModel;

/**
 * 
 * Servicio de core para la gestión de operaciones matemáticas.
 * @author diegosaez
 *
 */
@Service
public class OperationServiceImpl implements OperationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);

	@Override
	public int sum(@NotNull SumModel sum) {
		LOGGER.debug("Sumando valores {} + {}", sum.getValue1(), sum.getValue2());
		return sum.getValue1() + sum.getValue2();
	}

}
