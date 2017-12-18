package exception;

public class ParentalException  extends Exception{
	private static final long serialVersionUID = -8788726958906457416L;
	
	public ParentalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParentalException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParentalException(String message) {
		super(message);
	}

	public ParentalException(Throwable cause) {
		super("Error 288 - Edad insuficiente",cause);
	}
	

}
