package hr.nhex.graphic.adapters;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;

import java.awt.event.MouseEvent;

public class TileMovementMouseAdapter extends AbstractMouseAdapter {

	/**
	 * Consturctor.
	 *
	 * @param cn top level container
	 */
	public TileMovementMouseAdapter(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.MOVEMENT;
		this.setListenerOff();
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {
			Pair tilePos = getClickedTile(cn, ev);
			int specialTilePos = hlc.getSpecialHexList().lastIndexOf(new SpecialHex(tilePos, null));
			BoardTile bt = (BoardTile) game.getSelectedTile();
			if (specialTilePos != -1 || (tilePos.getX() == bt.getX() && tilePos.getY() == bt.getY())) {

				if (!(tilePos.getX() == bt.getX() && tilePos.getY() == bt.getY())) {

					Board board = game.getBoard();
					bt.setX(tilePos.getX());
					bt.setY(tilePos.getY());

					BattleSimulator bs = new BattleSimulator(board);
					bs.updateAfterEffects();

				}

				hlc.setDraggedHexagon(new Hexagon(
						tilePos.getX(),
						tilePos.getY(),
						hlc.getHexagon(tilePos.getX(), tilePos.getY()).getxC(),
						hlc.getHexagon(tilePos.getX(), tilePos.getY()).getyC(),
						hlc.getHexSize()+HexagonListContainer.HEX_GAP
						)
						);
			}

			hlc.getSpecialHexList().clear();

			cn.getMac().mouseListenerActivate(AdapterType.ROTATE);
			cn.repaint();
		}

	}

}
