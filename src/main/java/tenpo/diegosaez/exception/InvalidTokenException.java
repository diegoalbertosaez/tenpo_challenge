package tenpo.diegosaez.exception;

public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = 2754112783863993041L;

	public InvalidTokenException(String message) {
		super(message);
	}

}
