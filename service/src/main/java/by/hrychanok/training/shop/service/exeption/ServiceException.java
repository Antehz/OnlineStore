package by.hrychanok.training.shop.service.exeption;

public class ServiceException extends RuntimeException {

	protected final String message;

	/**
	 * Constructs a new runtime exception with {@code null} as its detail
	 * message. The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public ServiceException() {
		this.message = "SERVICE EXCEPTION";
	}

	public ServiceException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}