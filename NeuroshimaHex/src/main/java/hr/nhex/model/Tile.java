package hr.nhex.model;

/**
 * Razred koji predstavlja polje (engl. <i>Tile</i>) u igri Neuroshima Hex.
 * 
 * @author Luka Ruklić
 *
 */

public class Tile {

	/**
	 * Naziv polja.
	 */
	protected String name;

	/**
	 * Binarna vrijednost koja određuje da li je polje na ploči.
	 */
	private boolean onBoard;

	/**
	 * Prazan konstruktor.
	 */
	public Tile() {

	}

	public Tile(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isOnBoard() {
		return onBoard;
	}

	public void setOnBoard() {
		this.onBoard = true;
	}



}
