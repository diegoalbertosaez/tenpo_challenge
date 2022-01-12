package tenpo.diegosaez.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import tenpo.diegosaez.core.model.SumModel;

class OperationServiceTest {

	@Test
	void testSum() {
		var service = new OperationServiceImpl();
		var sum = service.sum(new SumModel(2, 2));
		assertEquals(4, sum);
	}

}
