package hr.nhex.decks;

import hr.nhex.game.DrawnTileSet;
import hr.nhex.model.AbstractTile;
import hr.nhex.model.HQ;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.unit.Attack;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Class that represents deck of tiles.
 *
 * @author Luka Ruklic
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

	private DrawnTileSet drawnTileSet = new DrawnTileSet();

	public void shuffleDeck() {
		long seed = System.nanoTime();
		Collections.shuffle(tiles, new Random(seed));
	}

	public void drawHQ() {
		drawnTileSet.putHqToDrawn((HQ) tiles.pop());	// razmisli o ovome, uvijek HQ mora biti na početku da bi radilo
	}

	public void drawNew() {
		int numberOfEmptyTiles = drawnTileSet.TILES_DRAWN_PER_TURN - drawnTileSet.getTilesCount();
		for (int i = 0; i < numberOfEmptyTiles; i++) {
			drawnTileSet.putTileToSet(tiles.pop());
		}
	}

	public AbstractTile getDrawnTile(int numberOfTile) {
		return drawnTileSet.getDrawnTile(numberOfTile);
	}

	public AbstractTile[] getDrawnTiles() {
		return drawnTileSet.getDrawnTiles();
	}

	public void discardTile(int numberOfTile) {
		drawnTileSet.discardTile(numberOfTile);
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
