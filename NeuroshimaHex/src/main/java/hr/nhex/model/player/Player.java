package hr.nhex.model.player;

import hr.nhex.decks.Deck;

import java.awt.Color;

/**
 * Razred koji predstavlja igra�a u igri Neuroshima Hex. Svaki igra� je predstavljen jedinstvenim imenom te bojom.
 *
 * @author Luka Ruklic
 *
 */

public class Player {

	/**
	 * Player name.
	 */
	private String playerName;
	/**
	 * Player color.
	 */
	private Color playerColor;
	/**
	 * Deck that player uses.
	 */
	private Deck playerDeck;

	/**
	 * Constructor.
	 *
	 * @param playerName name of the player
	 * @param playerColor color of the player
	 */
	public Player(String playerName, Color playerColor, Deck playerDeck) {
		super();
		this.playerName = playerName;
		this.playerColor = playerColor;
		this.playerDeck = playerDeck;
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

	public Deck getPlayerDeck() {
		return playerDeck;
	}

	public void setPlayerDeck(Deck playerDeck) {
		this.playerDeck = playerDeck;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Player other = (Player) obj;
		if (playerColor == null) {
			if (other.playerColor != null) {
				return false;
			}
		} else if (!playerColor.equals(other.playerColor)) {
			return false;
		}
		return true;
	}

}
