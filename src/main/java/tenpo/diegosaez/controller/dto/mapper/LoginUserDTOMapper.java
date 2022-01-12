package tenpo.diegosaez.controller.dto.mapper;

import org.mapstruct.Mapper;

import tenpo.diegosaez.controller.dto.LoginUserDTO;
import tenpo.diegosaez.controller.dto.TokenDTO;
import tenpo.diegosaez.security.model.LoginUserModel;
import tenpo.diegosaez.security.model.TokenModel;

@Mapper(componentModel = "spring")
public interface LoginUserDTOMapper {

	TokenDTO toDTO(TokenModel token);

	LoginUserModel toModel(LoginUserDTO loginUser);
}
