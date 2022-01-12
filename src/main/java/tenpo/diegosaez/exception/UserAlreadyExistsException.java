package tenpo.diegosaez.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -2601652402372975962L;

	private final String username;

	public UserAlreadyExistsException(String username, String message) {
		super(message);
		this.username = username;
	}
}
