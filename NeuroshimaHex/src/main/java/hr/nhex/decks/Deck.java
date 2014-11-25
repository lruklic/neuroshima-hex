package hr.nhex.decks;

import hr.nhex.board.BoardTile;
import hr.nhex.model.Tile;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.unit.Attack;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents deck of tiles.
 * 
 * @author Luka Rukli�
 *
 */

public class Deck {

	/**
	 * Number of sides on one NHex tile.
	 */
	protected static final int NUMBER_OF_SIDES = 6;
	/**
	 * Number of hit points standard Headquaters unit in NHex has.
	 */
	protected static final int STANDARD_HQ_HIT_POINTS = 20;
	/**
	 * Name of the deck.
	 */
	private String deckName;
	/**
	 * List of tiles that are contained in a deck.
	 */
	private List<Tile> tiles = new ArrayList<>();

	private BoardTile hq;

	public GameDeck createGameDeck() {
		return new GameDeck(this);
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public void addTileToDeck(Tile tile) {
		tiles.add(tile);
	}

	public String getDeckName() {
		return deckName;
	}

	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}

	public BoardTile getHq() {
		return hq;
	}

	public void setHq(BoardTile hq) {
		this.hq = hq;
	}

	/**
	 * Metoda koja mi�e sve elemente iz primljenih listi.
	 * 
	 * @param speed lista brzina jedinice koja se prazni
	 * @param attacks lista napada jedinice koja se prazni
	 * @param abilities lista sposobnosti jedinice koja se prazni
	 */

	public void clearAllLists(List<Integer> speed, List<Attack> attacks, List<Ability> abilities) {
		speed.clear();
		attacks.clear();
		abilities.clear();
	}

	/**
	 * Metoda koja prema primljenom imenu vra�a polje s tim imenom iz �pila.
	 * 
	 * @param name naziv polja
	 * @return polje ukoliko postoji, <code>null</code> ina�e
	 */
	public Tile getTileByName(String name) {
		for (Tile tile : tiles) {
			if (tile.getName().equals(name) && !tile.isOnBoard()) {
				return tile;
			}
		}
		return null;
	}

}
