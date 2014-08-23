package hr.nhex.exception;

/**
 * Iznimka koju program baca ukoliko doðe do nelegalne operacije nad ploèom.
 * 
 * @author Luka Rukliæ
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
	 * Konstruktor koji objašnjava pogreške preko poruke <i>message</i>.
	 * 
	 * @param message poruka koja objašnjava pogrešku
	 */
	public BoardException(String message) {
		super(message);
	}

}
