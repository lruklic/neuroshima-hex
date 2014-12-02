package hr.nhex.board;

import hr.nhex.exception.BoardException;
import hr.nhex.generic.Pair;
import hr.nhex.model.HQ;
import hr.nhex.model.Netter;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.ability.AbilityType;
import hr.nhex.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents playing board in Neuroshima Hex game.
 *
 * @author Luka Rukli�
 * @author Marin Bu�an�i�
 *
 */

public class Board implements IBasicBoard {

	/**
	 * Constant that defines board size for 2 to 4 players.
	 */
	private static final int BOARD_SIZE = 2;

	int[] angleX = {1, 0, -1, -1, 0, 1};	// zakomentiraj
	int[] angleY = {0, 1, 1, 0, -1, -1};

	/**
	 * Polja na plo�i spremljena u obliku liste.
	 */
	private List<BoardTile> tiles = new ArrayList<>();

	/**
	 * ***experimental method***
	 * used for adding tiles to board without graphic interface
	 *
	 * @param tile polje koje se dodaje na plo�u
	 */
	public void addTile(BoardTile tile, int x, int y, int angle) {

		if (isFilled(x, y)) {
			throw new BoardException("Field already contains a tile.");
		}
		if (!isLegal(x,y)) {
			throw new BoardException("Given coordinates are outside board area.");
		}
		tile.x = x;
		tile.y = y;
		tile.angle = angle;
		tile.setOnBoard();
		tiles.add(tile);

		addNettedToTile(tile, x, y);
	}

	/**
	 * Method that adds tile to board and checks for tile netting.
	 *
	 * @param tile tile that is being added to the board
	 */
	public void addTile(BoardTile tile) {
		tile.setOnBoard();
		tiles.add(tile);

		addNettedToTile(tile, tile.getX(), tile.getY());
	}
	/**
	 * Metoda koja provjerava da li je dodano (odnosno pomaknuto ili pogurnuto) polje do�lo na poziciju
	 * gdje je aktivan net. Ukoliko je, polje nema efekta, ina�e se u listu polja pod mre�om dodaje polje ili
	 * polja na koje jedinica pokazuje.
	 *
	 * @param tile polje koje dolazi na plo�u
	 * @param x koordinata x na koju dolazi polje
	 * @param y koordinata y na koju dolazi polje
	 */

	private void addNettedToTile(BoardTile tile, int x, int y) {
		if (tile instanceof Netter) {
			Netter unit = ((Netter)tile);
			//			for (BoardTile bt : this.tiles) {
			//				if (bt instanceof Netter) {
			//					if (((Netter) bt).getNettedTiles().contains(new Pair(x, y))) {
			//						return;
			//					}
			//				}
			//			}
			for (Ability ability : unit.getAbilities()) {
				if (ability.getType() == AbilityType.NET) {
					int pointsToTileX = unit.getX() + angleX[(ability.getPointsTo() + unit.getAngle()) % 6];
					int pointsToTileY = unit.getY() + angleY[(ability.getPointsTo() + unit.getAngle()) % 6];
					if (isFilled(pointsToTileX, pointsToTileY)) { // i ne sadr�i mre�u usmjerenu prema meni

					}
					unit.addNettedTile(new Pair(pointsToTileX, pointsToTileY));
				}
			}
		}
	}

	public List<Pair> getAdjecantTiles (int x, int y) {
		List<Pair> pairList = new ArrayList<>();

		if (isLegal(x,y+1)) {
			pairList.add(new Pair(x, y+1));
		}
		if (isLegal(x+1,y)) {
			pairList.add(new Pair(x+1, y));
		}

		if (isLegal(x,y-1)) {
			pairList.add(new Pair(x, y-1));
		}
		if (isLegal(x-1,y)) {
			pairList.add(new Pair(x-1, y));
		}

		if (isLegal(x-1,y+1)) {
			pairList.add(new Pair(x-1, y+1));
		}
		if (isLegal(x+1,y-1)) {
			pairList.add(new Pair(x+1, y-1));
		}

		return pairList;

	}

	public List<BoardTile> getTiles() {
		return tiles;
	}

	public boolean isLegal(int x, int y) {
		if ((Math.abs(x+y) > BOARD_SIZE) || (Math.abs(x) > BOARD_SIZE) || (Math.abs(y) > BOARD_SIZE)) {
			return false;
		}
		else {
			return true;
		}
	}

	public int numberOfHQ() {
		int hqNumber = 0;
		for (BoardTile t : tiles) {
			if (t instanceof HQ) {
				hqNumber++;
			}
		}
		return hqNumber;
	}

	@Override
	public boolean isFilled(int x, int y) {
		for (BoardTile tile : tiles) {
			if (tile.x == x && tile.y == y) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BoardTile getTile(int x, int y) {
		for (BoardTile tile : tiles) {
			if (tile.x == x && tile.y == y) {
				return tile;
			}
		}
		return null;
	}

	@Override
	public boolean tileIsNetted(int x, int y, int noOfIteration, Player player) {
		if (noOfIteration > 5) {
			return false;
		}
		for (BoardTile tile : tiles) {
			if (tile instanceof Netter && !tile.getPlayer().equals(player) && !tileIsNetted(tile.getX(), tile.getY(), noOfIteration+1, player)) {
				for (Pair p : ((Netter) tile).getNettedTiles()) {
					if (p.getX() == x && p.getY() == y) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
