package tenpo.diegosaez.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de base de datos que representa un registro de historial de
 * ejecución.
 * 
 * 
 * @author diegosaez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "execution_history")
public class ExecutionHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "execution_history_generator")
	@SequenceGenerator(name = "execution_history_generator", sequenceName = "execution_history_seq", allocationSize = 1)
	private Integer id;
	private String name;
	private LocalDateTime executionDate;
	private Integer executionTime;
	@Enumerated(EnumType.STRING)
	private ExecutionHistoryResult executionResult;
}
