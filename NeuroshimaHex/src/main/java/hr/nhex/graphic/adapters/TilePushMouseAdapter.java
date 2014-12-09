package hr.nhex.graphic.adapters;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
import hr.nhex.game.TurnPhase;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TilePushMouseAdapter extends AbstractMouseAdapter {

	private boolean pusherTurn = true;

	public TilePushMouseAdapter(NeuroshimaCanvas cn) {
		this.cn = cn;
		this.game = cn.getGameInstance();
		this.hlc = HexagonListContainer.getInstance();
		this.type = AdapterType.PUSH;
		this.setListenerOff();
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

		if (listenerOn) {

			if (pusherTurn) {

				BoardTile tilePusher = (BoardTile) game.getSelectedTile();
				Pair tilePusheePos = getClickedTile(cn, ev);
				int specialTilePos = hlc.getSpecialHexList().lastIndexOf(new SpecialHex(tilePusheePos, null));

				if (specialTilePos != -1) {

					hlc.clearHexSpecialList();
					List<Pair> pusherAdjecantTiles = game.getBoard().getAdjecantTiles(tilePusher.getX(), tilePusher.getY());
					List<Pair> pusheeAdjecantTiles = game.getBoard().getAdjecantTiles(tilePusheePos.getX(), tilePusheePos.getY());
					List<Pair> possiblePushPos = new ArrayList<Pair>();

					for (Pair pusheeAdj : pusheeAdjecantTiles) {
						if (!game.getBoard().isFilled(pusheeAdj.getX(), pusheeAdj.getY()) && !(pusherAdjecantTiles.contains(pusheeAdj))) {
							possiblePushPos.add(pusheeAdj);
						}
					}

					if (possiblePushPos.size() == 1) {

						Pair newPosition = possiblePushPos.get(0);
						Board board = game.getBoard();
						BoardTile pushee = board.getTile(tilePusheePos.getX(), tilePusheePos.getY());
						pushee.setX(newPosition.getX());
						pushee.setY(newPosition.getY());

						//						BattleSimulator bs = new BattleSimulator(board);
						//						bs.updateAfterEffects();

						clearAndRepaint();
						game.setSelectedTile(null);
						hlc.setDraggedHexagon(null);

						game.setTurnPhase(TurnPhase.TILES_DRAWN);
						cn.getMac().mouseListenerActivate(AdapterType.PLACEMENT);

					} else { // if possiblePushPos > 1
						for (Pair p : possiblePushPos) {
							BoardTile tilePushee = game.getBoard().getTile(tilePusheePos.getX(), tilePusheePos.getY());
							hlc.getSpecialHexList().add(new SpecialHex(
									new Pair(
											p.getX(),
											p.getY()),
											tilePushee.getPlayer().getPlayerColor()
									)
									);
							game.setSelectedTile(tilePushee);
							pusherTurn = false;
						}
						cn.repaint();
					}

				}

			} else {

				BoardTile tilePushee = (BoardTile) game.getSelectedTile();
				Pair newPosition = getClickedTile(cn, ev);
				int specialTilePos = hlc.getSpecialHexList().lastIndexOf(new SpecialHex(newPosition, null));

				if (specialTilePos != -1) {

					Board board = game.getBoard();
					tilePushee.setX(newPosition.getX());
					tilePushee.setY(newPosition.getY());

					BattleSimulator bs = new BattleSimulator(board);
					bs.updateAfterEffects();

					pusherTurn = true;
					clearAndRepaint();
					game.setSelectedTile(null);
					hlc.setDraggedHexagon(null);

					game.setTurnPhase(TurnPhase.TILES_DRAWN);
					cn.getMac().mouseListenerActivate(AdapterType.PLACEMENT);

				}

			}

		}
	}

}
