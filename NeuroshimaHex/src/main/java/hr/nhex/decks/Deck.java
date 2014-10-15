package hr.nhex.decks;

import hr.nhex.model.Tile;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.unit.Attack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class that represents deck of tiles.
 * 
 * @author Luka Rukliæ
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

	
	
	/**
	 * Metoda koja miï¿½e sve elemente iz primljenih listi.
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
	 * Metoda koja prema primljenom imenu vraï¿½a polje s tim imenom iz ï¿½pila.
	 * 
	 * @param name naziv polja
	 * @return polje ukoliko postoji, <code>null</code> inaï¿½e
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
