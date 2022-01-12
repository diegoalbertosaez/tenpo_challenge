package tenpo.diegosaez.core.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import tenpo.diegosaez.core.model.ExecutionHistoryModel;
import tenpo.diegosaez.data.entity.ExecutionHistory;

@Mapper(componentModel = "spring")
public interface ExecutionHistoryModelMapper {
	ExecutionHistoryModel toModel(ExecutionHistory executionHistory);

	List<ExecutionHistoryModel> toModel(List<ExecutionHistory> executionHistories);

	ExecutionHistory toEntity(ExecutionHistoryModel executionHistory);
}
