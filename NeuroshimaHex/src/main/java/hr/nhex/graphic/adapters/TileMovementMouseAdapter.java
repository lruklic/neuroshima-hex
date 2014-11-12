package hr.nhex.graphic.adapters;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TileMovementMouseAdapter extends MouseAdapter implements IMouseAdapter {

	/**
	 * Variable that defines whether the listener is listening or not (is it on or off).
	 */
	private boolean listenerOn = false;
	/**
	 * Top level container.
	 */
	private NeuroshimaCanvas cn;
	/**
	 * Tile that is selected to perform movement.
	 */
	private BoardTile selectedTile;
	/**
	 * List that contains tile coordinates where selected tile could move.
	 */
	private List<Pair> specialHex = new ArrayList<>();

	private TileRotateMouseAdapter trma;

	/**
	 * Consturctor.
	 * 
	 * @param cn top level container
	 */
	public TileMovementMouseAdapter(NeuroshimaCanvas cn) {
		this.cn = cn;
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {
			Pair tilePos = TilePlacementMouseAdapter.getClickedTile(cn, ev);
			if (specialHex.contains(tilePos) || (tilePos.getX() == selectedTile.getX() && tilePos.getY() == selectedTile.getY())) {

				trma.setSelectedTile(selectedTile);

				if (!(tilePos.getX() == selectedTile.getX() && tilePos.getY() == selectedTile.getY())) {

					Board board = cn.getGameInstance().getBoard();
					board.getTile(selectedTile.getX(), selectedTile.getY()).setX(tilePos.getX());
					board.getTile(selectedTile.getX(), selectedTile.getY()).setY(tilePos.getY());

					BattleSimulator bs = new BattleSimulator(board);
					bs.updateAfterEffects();

				}

				cn.setDraggedHexagon(new Hexagon(
						tilePos.getX(),
						tilePos.getY(),
						cn.getHexagon(tilePos.getX(), tilePos.getY()).getxC(),
						cn.getHexagon(tilePos.getX(), tilePos.getY()).getyC(),
						cn.getHexSize()+cn.HEX_GAP
						));
			}

			specialHex.clear();

			cn.mouseListenerActivate(trma);
			cn.repaint();
		}

	}

	@Override
	public void setListenerOn() {
		this.listenerOn = true;
	}

	@Override
	public void setListenerOff() {
		this.listenerOn = false;
	}

	public List<Pair> getSpecialHex() {
		return specialHex;
	}

	public void addToSpecialHex(int x, int y) {
		specialHex.add(new Pair(x,y));
	}

	public void setSelectedTile(BoardTile selectedTile) {
		this.selectedTile = selectedTile;
	}

	public TileRotateMouseAdapter getTrma() {
		return trma;
	}

	public void setTrma(TileRotateMouseAdapter trma) {
		this.trma = trma;
	}

}
