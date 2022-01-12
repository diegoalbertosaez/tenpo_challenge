package tenpo.diegosaez.core.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExecutionHistoryModel {
	private Integer id;
	private String name;
	private LocalDateTime executionDate;
	private Integer executionTime;
	private ExecutionHistoryResult executionResult;
}
