package hr.nhex.decks;

import hr.nhex.model.AbstractTile;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.unit.Attack;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

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
	private Stack<AbstractTile> tiles = new Stack<>();

	private AbstractTile[] drawnTiles = new AbstractTile[3];

	public void shuffleDeck() {
		long seed = System.nanoTime();
		Collections.shuffle(tiles, new Random(seed));
	}

	public void drawHQ() {
		drawnTiles[2] = tiles.pop();
	}

	public void drawNew() {
		for (int i = 0; i < 3; i++) {
			if (drawnTiles[i] == null) {
				drawnTiles[i] = tiles.pop();
			}
		}
	}

	public AbstractTile getDrawnTile(int numberOfTile) {
		if (numberOfTile > 2) {
			return null;
		} else {
			return drawnTiles[numberOfTile];
		}
	}

	public void discardTile(int numberOfTile) {
		drawnTiles[numberOfTile] = null;
	}

	public void addTileToDeck(AbstractTile tile) {
		tiles.add(tile);
	}

	public String getDeckName() {
		return deckName;
	}

	protected void setDeckName(String deckName) {
		this.deckName = deckName;
	}

	public AbstractTile[] getDrawnTiles() {
		return drawnTiles;
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
	public AbstractTile getTileByName(String name) {
		for (AbstractTile tile : tiles) {
			if (tile.getName().equals(name) && !tile.isOnBoard()) {
				return tile;
			}
		}
		return null;
	}

}
