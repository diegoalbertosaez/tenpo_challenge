package tenpo.diegosaez.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import tenpo.diegosaez.core.model.AuthenticationUserDetails;
import tenpo.diegosaez.data.entity.User;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {

	@Mapping(source = "password", target = "password")
	@Mapping(source = "username", target = "username")
	AuthenticationUserDetails toModel(User user);
}
