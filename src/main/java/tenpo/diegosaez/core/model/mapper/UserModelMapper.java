package tenpo.diegosaez.core.model.mapper;

import org.mapstruct.Mapper;

import tenpo.diegosaez.core.model.UserModel;
import tenpo.diegosaez.data.entity.User;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

	User toEntity(UserModel user);

	UserModel toModel(User user);

}
