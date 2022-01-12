package tenpo.diegosaez.controller.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import tenpo.diegosaez.controller.dto.ExecutionHistoryDTO;
import tenpo.diegosaez.core.model.ExecutionHistoryModel;

@Mapper(componentModel = "spring")
public interface ExecutionHistoryDTOMapper {
	ExecutionHistoryDTO toDTO(ExecutionHistoryModel executionHistory);

	List<ExecutionHistoryDTO> toDTO(List<ExecutionHistoryModel> executionHistory);
}
