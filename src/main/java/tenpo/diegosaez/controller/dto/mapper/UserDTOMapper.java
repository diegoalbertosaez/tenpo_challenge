package tenpo.diegosaez.controller.dto.mapper;

import org.mapstruct.Mapper;

import tenpo.diegosaez.controller.dto.UserDTO;
import tenpo.diegosaez.controller.dto.UserSignupDTO;
import tenpo.diegosaez.core.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

	UserModel toModel(UserDTO user);

	UserSignupDTO toDTO(UserModel user);

}
