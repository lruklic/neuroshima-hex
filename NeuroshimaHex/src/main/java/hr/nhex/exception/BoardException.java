package hr.nhex.exception;

/**
 * Iznimka koju program baca ukoliko do�e do nelegalne operacije nad plo�om.
 * 
 * @author Luka Rukli�
 *
 */

public class BoardException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Prazan konstruktor.
	 */
	public BoardException() {
	}

	/**
	 * Konstruktor koji obja�njava pogre�ke preko poruke <i>message</i>.
	 * 
	 * @param message poruka koja obja�njava pogre�ku
	 */
	public BoardException(String message) {
		super(message);
	}

}
