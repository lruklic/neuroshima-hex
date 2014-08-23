package hr.nhex.model;

import java.awt.Color;

/**
 * Razred koji predstavlja igra�a u igri Neuroshima Hex. Svaki igra� je predstavljen jedinstvenim imenom te bojom.
 * 
 * @author Luka Rukli�
 *
 */

public class Player {

	/**
	 * Ime igra�a.
	 */
	private String playerName;
	/**
	 * Boja igra�a.
	 */
	private Color playerColor;

	/**
	 * Konstruktor.
	 * 
	 * @param playerName ime igra�a
	 * @param playerColor boja igra�a
	 */
	public Player(String playerName, Color playerColor) {
		super();
		this.playerName = playerName;
		this.playerColor = playerColor;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((playerColor == null) ? 0 : playerColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playerColor == null) {
			if (other.playerColor != null)
				return false;
		} else if (!playerColor.equals(other.playerColor))
			return false;
		return true;
	}



}
