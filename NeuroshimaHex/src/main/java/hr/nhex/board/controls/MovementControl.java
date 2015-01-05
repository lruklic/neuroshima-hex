package hr.nhex.board.controls;

import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.generic.BoardTilePair;
import hr.nhex.generic.Pair;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.ability.AbilityType;

import java.util.ArrayList;
import java.util.List;

public class MovementControl {

	private List<BoardTilePair> tileSource = new ArrayList<BoardTilePair>();
	private List<BoardTilePair> movedTileSource = new ArrayList<BoardTilePair>();

	public void locateAllMovementSource(Board board) {
		for (BoardTile tile : board.getTiles()) {
			for (Ability ability : tile.getAbilities()) {

				if (ability.getType().equals(AbilityType.MOVE)) {
					tileSource.add(new BoardTilePair(tile, tile));
				}
			}

			for (Pair p : board.getAdjecantTiles(tile.getX(), tile.getY())) {
				BoardTile adjTile = board.getTile(p.getX(), p.getY());
				if (adjTile != null && !board.tileIsNetted(adjTile.getX(), adjTile.getY(), 0, adjTile.getPlayer())) {
					for (Ability a : adjTile.getAbilities()) {
						if (a.getType() == AbilityType.TRANSPORT) {		// podrazumijeva da svi Transportovi pokazuju u svim smjerovima (u stvarnoj igri je tako)
							tileSource.add(new BoardTilePair(tile, adjTile));
							break;
						}
					}
				}
			}
		}
		for (BoardTilePair boardTilePair : tileSource) {
			System.out.println(boardTilePair.getUser().getName()+":"+boardTilePair.getSource().getName());
		}
	}

	public boolean tileCanMove(BoardTile tile) {

		List<BoardTilePair> possibleTileSourcePairs = new ArrayList<BoardTilePair>();

		// Check for each move source for given tile and whether that move source is already used
		for (BoardTilePair boardTilePair : tileSource) {

			if (tile == boardTilePair.getUser()) {
				boolean alreadyMoved = false;
				for (BoardTilePair movedPair : movedTileSource) {
					if (boardTilePair.equals(movedPair)) {
						alreadyMoved = true;
						break;
					}
				}
				if (!alreadyMoved) {
					possibleTileSourcePairs.add(boardTilePair);
				}
			}

		}

		// Decide whether tile can move based on newfound list
		if (possibleTileSourcePairs.size() == 0) {
			return false;
		} else {
			for (BoardTilePair boardTilePair : possibleTileSourcePairs) {
				if (!boardTilePair.getUser().equals(boardTilePair.getSource())) {		// ako ima više sourceova, odabrati koji se šalje
					movedTileSource.add(boardTilePair);
					return true;
				}
			}

			movedTileSource.add(possibleTileSourcePairs.get(0));
			return true;
		}

	}

	public void clear() {
		tileSource.clear();
		movedTileSource.clear();
	}


}
