package temporal.seguridad.repositorioUsuarios.exceptions;

public class UsuarioNoExisteException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsuarioNoExisteException() {
	}

	public UsuarioNoExisteException(String message) {
		super(message);
	}

	public UsuarioNoExisteException(Throwable cause) {
		super(cause);
	}

	public UsuarioNoExisteException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarioNoExisteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
