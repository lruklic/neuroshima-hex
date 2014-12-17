package hr.nhex.game;

import hr.nhex.model.AbstractTile;
import hr.nhex.model.HQ;

/**
 * Set of predefined number of tiles (3 by default) that is drawn in turn by a player.
 *
 * @author Luka Ruklic
 *
 */

public class DrawnTileSet {

	/**
	 * Number of tiles that is drawn per turn.
	 */
	public final int TILES_DRAWN_PER_TURN = 3;
	/**
	 * Array of tiles where drawn tiles are stored.
	 */
	private AbstractTile[] drawnTiles = new AbstractTile[TILES_DRAWN_PER_TURN];
	/**
	 * Getter for one of the tiles that is drawn.
	 *
	 * @param numberOfTile number which is used to decide which tile to return
	 * @return one of the tiles that is drawn
	 */
	public AbstractTile getDrawnTile(int numberOfTile) {
		if (numberOfTile > TILES_DRAWN_PER_TURN) {
			return null;
		} else {
			return drawnTiles[numberOfTile];
		}
	}

	/**
	 * Getter for all drawn tiles in current turn.
	 *
	 * @return all drawn tiles packed in new instance of list
	 */
	public AbstractTile[] getDrawnTiles() {
		return drawnTiles;
	}

	/**
	 * Method that returns number of filled tiles in one DrawnTileSet instance.
	 *
	 * @return number of already drawn tiles
	 */
	public int getTilesCount() {
		int numberOfDrawnTiles = 0;
		for (int i = 0; i < TILES_DRAWN_PER_TURN; i++) {
			if (drawnTiles[i] != null) {
				numberOfDrawnTiles++;
			}
		}
		return numberOfDrawnTiles;
	}

	public void putTileToSet(AbstractTile drawnTile) {
		for (int i = 0; i < TILES_DRAWN_PER_TURN; i++) {
			if (drawnTiles[i] == null) {
				drawnTiles[i] = drawnTile;
				return;
			}
		}
	}

	public void putHqToDrawn(HQ hq) {
		drawnTiles[0] = hq;
	}

	public void discardTile(int numberOfTile) {
		drawnTiles[numberOfTile] = null;
	}

}
