package hr.nhex.decks;

import hr.nhex.game.Game;
import hr.nhex.game.TurnPhase;
import hr.nhex.model.Tile;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * Class that represents deck that is used during NHex game.
 * 
 * @author Luka Rukliæ
 *
 */

public class GameDeck {

	private String deckName;

	private Stack<Tile> gameTiles;

	private Tile[] drawnTiles = new Tile[3];

	public GameDeck(Deck deck) {

		long seed = System.nanoTime();
		Collections.shuffle(deck.getTiles(), new Random(seed));
		this.gameTiles = new Stack<>();
		this.gameTiles.addAll(deck.getTiles());
		this.deckName = deck.getDeckName();

	}

	public void drawNew(Game gameInstance) {
		for (int i = 0; i < 3; i++) {
			if (drawnTiles[i] == null) {
				drawnTiles[i] = this.gameTiles.pop();
			}
		}
		gameInstance.setTurnPhase(TurnPhase.DISCARD_PHASE);
	}

	public Tile getDrawnTile(int numberOfTile) {
		if (numberOfTile > 2) {
			return null;
		} else {
			return drawnTiles[numberOfTile];
		}
	}

	public void discardTile(int numberOfTile) {
		drawnTiles[numberOfTile] = null;
	}

	public void discardAllTiles() {
		drawnTiles = new Tile[3];
	}

	public String getDeckName() {
		return deckName;
	}

	public Tile[] getDrawnTiles() {
		return drawnTiles;
	}



	//	public void drawNew() {
	//		if (emptyTiles() == 3) {
	//			firstTile = this.gameTiles.pop();
	//			secondTile = this.gameTiles.pop();
	//			thirdTile = this.gameTiles.pop();
	//		} else if (emptyTiles() == 2) {
	//			if (firstTile != null) {
	//				secondTile = this.gameTiles.pop();
	//				thirdTile = this.gameTiles.pop();
	//			} else if (secondTile != null) {
	//				firstTile = this.gameTiles.pop();
	//				thirdTile = this.gameTiles.pop();
	//			} else {
	//				firstTile = this.gameTiles.pop();
	//				secondTile = this.gameTiles.pop();
	//			}
	//		} else if (emptyTiles() == 1) {
	//			if (firstTile == null) {
	//				firstTile = this.gameTiles.pop();
	//			} else
	//		}
	//	}
	//
	//	private int emptyTiles() {
	//		int numberOfEmptyTiles = 0;
	//		if (firstTile != null) {
	//			numberOfEmptyTiles++;
	//		}
	//		if (secondTile != null) {
	//			numberOfEmptyTiles++;
	//		}
	//		if (thirdTile != null) {
	//			numberOfEmptyTiles++;
	//		}
	//		return numberOfEmptyTiles;
	//	}


}
