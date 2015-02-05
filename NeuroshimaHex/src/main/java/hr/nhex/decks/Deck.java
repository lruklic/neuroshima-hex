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
	/**
	 * Instance of class that contains drawn tiles from player deck that are currently in the game.
	 */
	private DrawnTileSet drawnTileSet = new DrawnTileSet();
	/**
	 * Method that shuffles current player deck using random seed.
	 */
	public void shuffleDeck() {
		long seed = System.nanoTime();
		Collections.shuffle(tiles, new Random(seed));
	}
	/**
	 * Method that draws Headquaters tile from the top of the deck.
	 */
	public void drawHQ() {
		drawnTileSet.putHqToDrawn((HQ) tiles.pop());	// razmisli o ovome, uvijek HQ mora biti na početku da bi radilo
		shuffleDeck();
	}
	/**
	 * Method that draws full hand of tiles. Default is 3, but value is specified in <code>drawnTileSet.TILES_DRAWN_PER_TURN</code>.
	 */
	public void drawNew() {
		int numberOfEmptyTiles = drawnTileSet.TILES_DRAWN_PER_TURN - drawnTileSet.getTilesCount();

		if (tiles.size() < numberOfEmptyTiles) {
			numberOfEmptyTiles = tiles.size();
		}

		for (int i = 0; i < numberOfEmptyTiles; i++) {
			drawnTileSet.putTileToSet(tiles.pop());
		}
	}
	/**
	 * Overriden method that draws custom number of tiles. Used in first and second player turn.
	 * @param numberOfTiles number of the tiles that is drawn from the deck
	 */
	public void drawNew(int numberOfTiles) {
		for (int i = 0; i < numberOfTiles; i++) {
			drawnTileSet.putTileToSet(tiles.pop());
		}
	}
	/**
	 * Getter for one of the drawn tiles.
	 * @param numberOfTile number of tile that method gets
	 * @return instance of tile
	 */
	public AbstractTile getDrawnTile(int numberOfTile) {
		return drawnTileSet.getDrawnTile(numberOfTile);
	}
	/**
	 * Getter for all drawn tiles.
	 * @return AbstractTile list of all drawn tiles
	 */
	public AbstractTile[] getDrawnTiles() {
		return drawnTileSet.getDrawnTiles();
	}
	/**
	 * Method that discards on of the drawn tiles.
	 * @param numberOfTile number of tile that is being discarded
	 */
	public void discardTile(int numberOfTile) {
		drawnTileSet.discardTile(numberOfTile);
	}
	/**
	 * Method that adds tile to deck.
	 * @param tile
	 */
	public void addTileToDeck(AbstractTile tile) {
		tiles.add(tile);
	}
	/**
	 * Getter for number of tiles left in the deck.
	 * @return number of tiles left in the deck
	 */
	public int getTilesLeft() {
		return tiles.size();
	}
	/**
	 * Getter for deck name.
	 * @return name of the deck
	 */
	public String getDeckName() {
		return deckName;
	}
	/**
	 * Setter for deck name.
	 * @param deckName deck name
	 */
	protected void setDeckName(String deckName) {
		this.deckName = deckName;
	}
	/**
	 * Method that removes all elements from received lists.
	 *
	 * @param speed list containing unit initiatives
	 * @param attacks list containing unit attacks
	 * @param abilities  list containing unit abilities
	 */
	public void clearAllLists(List<Integer> speed, List<Attack> attacks, List<Ability> abilities) {
		speed.clear();
		attacks.clear();
		abilities.clear();
	}

}
