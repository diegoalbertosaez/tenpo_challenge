package tenpo.diegosaez.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Anotación utilizada para registrar el historial de ejecución.
 * 
 * @author diegosaez
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface ExecutionHistory {
	String value();
}
