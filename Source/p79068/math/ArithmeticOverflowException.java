package p79068.math;


/**
 * Thrown when a result cannot be returned because its value exceeds the range of its type. This may also be thrown when there is overflow in intermediate calculations.
 */
@SuppressWarnings("serial")
public class ArithmeticOverflowException extends ArithmeticException {
	
	/**
	 * Creates an arithmetic overflow exception with a {@code null} detail message.
	 */
	public ArithmeticOverflowException() {
		super();
	}
	
	
	/**
	 * Creates an arithmetic overflow exception with the specified detail message.
	 */
	public ArithmeticOverflowException(String message) {
		super(message);
	}
	
}