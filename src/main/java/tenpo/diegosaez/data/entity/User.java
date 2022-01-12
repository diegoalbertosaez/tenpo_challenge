package tenpo.diegosaez.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad de base de datos que representa un registro de usuario.
 * 
 * 
 * @author diegosaez
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "app_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "app_user_seq", allocationSize = 1)
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
}
