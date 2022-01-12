package tenpo.diegosaez.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.annotations.ExecutionHistory;
import tenpo.diegosaez.core.model.ExecutionHistoryModel;
import tenpo.diegosaez.core.model.ExecutionHistoryResult;
import tenpo.diegosaez.core.service.ExecutionHistoryService;

/**
 * 
 * Aspecto para interceptar llamadas a métodos.
 * 
 * @author diegosaez
 *
 */
@Aspect
@Component
@RequiredArgsConstructor
public class ExecutionHistoryAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionHistoryAspect.class);

	private final ExecutionHistoryService executionHistoryService;

	/**
	 * Genera historial de ejecución de métodos anotados con ExecutionHistory.
	 * 
	 * @param joinPoint
	 * @return Object.
	 * @throws Throwable
	 */
	@Around("@annotation(tenpo.diegosaez.annotations.ExecutionHistory)")
	public Object generateExecutionHistory(ProceedingJoinPoint joinPoint) throws Throwable {

		LOGGER.debug("Registrando de historial de ejecución.");
		long start = System.currentTimeMillis();
		Object proceed = null;
		try {
			proceed = joinPoint.proceed();
		} catch (Throwable e) {
			LOGGER.error("Error al registrar historial de ejecución.", e);			
			saveErrorExecutionHistory(calculateExecutionTime(start), joinPoint);
			throw e;
		}
		saveSuccessExecutionHistory(calculateExecutionTime(start), joinPoint);
		return proceed;
	}

	private long calculateExecutionTime(long start) {
		return  System.currentTimeMillis() - start;
	}
	
	private void saveSuccessExecutionHistory(long executionTime, ProceedingJoinPoint joinPoint) {
		saveExecutionHistory(executionTime, ExecutionHistoryResult.SUCCESS, joinPoint);
	}

	private void saveErrorExecutionHistory(long executionTime, ProceedingJoinPoint joinPoint) {
		saveExecutionHistory(executionTime, ExecutionHistoryResult.ERROR, joinPoint);
	}

	private void saveExecutionHistory(long executionTime, ExecutionHistoryResult executionHistoryResult,
			ProceedingJoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		ExecutionHistory executionHistoryannotation = method.getAnnotation(ExecutionHistory.class);
		try {
			ExecutionHistoryModel executionHistory = ExecutionHistoryModel.builder().executionDate(LocalDateTime.now())
					.executionTime((int) executionTime).name(executionHistoryannotation.value())
					.executionResult(executionHistoryResult).build();
			executionHistoryService.save(executionHistory);
		} catch (Exception e) {
			LOGGER.error("Error al registrar historial de ejecución", e);
		}

	}

}
