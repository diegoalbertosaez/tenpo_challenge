package tenpo.diegosaez.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserModel {
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
}
