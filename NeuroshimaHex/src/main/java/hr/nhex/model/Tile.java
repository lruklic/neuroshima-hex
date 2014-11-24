package hr.nhex.model;

/**
 * Razred koji predstavlja polje (engl. <i>Tile</i>) u igri Neuroshima Hex.
 *
 * @author Luka Ruklić
 *
 */

public abstract class Tile {

	/**
	 * Tile name.
	 */
	protected String name;
	/**
	 * Binary value that determines whether the tile is on board or not.
	 */
	private boolean onBoard;

	/**
	 * Empty constructor.
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

	public abstract int getAngle();
	public abstract Player getPlayer();



}
