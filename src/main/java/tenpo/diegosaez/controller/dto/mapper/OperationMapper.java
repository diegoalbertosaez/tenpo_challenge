package tenpo.diegosaez.controller.dto.mapper;

import org.mapstruct.Mapper;

import tenpo.diegosaez.controller.dto.SumDTO;
import tenpo.diegosaez.core.model.SumModel;

@Mapper(componentModel = "spring")
public interface OperationMapper {
	SumModel toModel(SumDTO sum);
}
