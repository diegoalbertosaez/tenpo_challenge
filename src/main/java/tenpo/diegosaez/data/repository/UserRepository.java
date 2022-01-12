package tenpo.diegosaez.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tenpo.diegosaez.data.entity.User;

/**
 * Repositorio para realizar operaciones de lectura y escritura sobre un usuario
 * en base de datos.
 * 
 * @author diegosaez
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);
}
