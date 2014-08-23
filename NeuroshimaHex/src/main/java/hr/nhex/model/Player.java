package hr.nhex.model;

import java.awt.Color;

/**
 * Razred koji predstavlja igraèa u igri Neuroshima Hex. Svaki igraè je predstavljen jedinstvenim imenom te bojom.
 * 
 * @author Luka Rukliæ
 *
 */

public class Player {

	/**
	 * Ime igraèa.
	 */
	private String playerName;
	/**
	 * Boja igraèa.
	 */
	private Color playerColor;

	/**
	 * Konstruktor.
	 * 
	 * @param playerName ime igraèa
	 * @param playerColor boja igraèa
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
