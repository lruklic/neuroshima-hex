package hr.nhex.graphic.adapters;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.game.Game;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TileMovementMouseAdapter extends MouseAdapter implements IMouseAdapter {

	/**
	 * Variable that defines whether the listener is listening or not (is it on or off).
	 */
	private boolean listenerOn = false;
	/**
	 * Top level container.
	 */
	private NeuroshimaCanvas cn;

	private HexagonListContainer hlc;

	private Game game;

	private TileRotateMouseAdapter trma;

	/**
	 * Consturctor.
	 * 
	 * @param cn top level container
	 */
	public TileMovementMouseAdapter(NeuroshimaCanvas cn, HexagonListContainer hlc) {
		this.cn = cn;
		this.hlc = hlc;
		this.game = cn.getGameInstance();
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {
			Pair tilePos = TilePlacementMouseAdapter.getClickedTile(cn, ev);
			int specialTilePos = hlc.getSpecialHexList().lastIndexOf(new SpecialHex(tilePos, null));
			BoardTile bt = (BoardTile) game.getSelectedTile();
			if (specialTilePos != -1 || (tilePos.getX() == bt.getX() && tilePos.getY() == bt.getY())) {

				if (!(tilePos.getX() == bt.getX() && tilePos.getY() == bt.getY())) {

					Board board = game.getBoard();
					board.getTile(bt.getX(), bt.getY()).setX(tilePos.getX());
					board.getTile(bt.getX(), bt.getY()).setY(tilePos.getY());

					BattleSimulator bs = new BattleSimulator(board);
					bs.updateAfterEffects();

				}

				hlc.setDraggedHexagon(new Hexagon(
						tilePos.getX(),
						tilePos.getY(),
						cn.getHexagon(tilePos.getX(), tilePos.getY()).getxC(),
						cn.getHexagon(tilePos.getX(), tilePos.getY()).getyC(),
						cn.getHexSize()+cn.HEX_GAP
						));
			}

			hlc.getSpecialHexList().clear();

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

	public TileRotateMouseAdapter getTrma() {
		return trma;
	}

	public void setTrma(TileRotateMouseAdapter trma) {
		this.trma = trma;
	}

}
