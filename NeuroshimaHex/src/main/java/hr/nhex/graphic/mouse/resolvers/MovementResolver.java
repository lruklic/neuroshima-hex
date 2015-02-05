package hr.nhex.graphic.mouse.resolvers;

import hr.nhex.board.BoardTile;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.canvas.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.Hexagon;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;
import hr.nhex.graphic.mouse.adapters.AdapterType;

import java.awt.event.MouseEvent;

public class MovementResolver extends AbstractMouseResolver {

	public MovementResolver(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.MOVEMENT;
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		Pair tilePos = getClickedTile(cn, ev);
		int specialTilePos = hlc.getSpecialHexList().lastIndexOf(new SpecialHex(tilePos, null));
		BoardTile bt = (BoardTile) game.getSelectedTile();
		if (specialTilePos != -1 || (tilePos.getX() == bt.getX() && tilePos.getY() == bt.getY())) {		// RUŠI SE, PROVJERI ZAŠTO

			if (!(tilePos.getX() == bt.getX() && tilePos.getY() == bt.getY())) {

				// Board board = game.getBoard();
				bt.setX(tilePos.getX());
				bt.setY(tilePos.getY());

				//					BattleSimulator bs = new BattleSimulator(board);
				//					bs.updateAfterEffects();

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

		hlc.clearHexSpecialList();
		cn.repaint();
		setActiveAdapterType(AdapterType.ROTATE);

	}

}
