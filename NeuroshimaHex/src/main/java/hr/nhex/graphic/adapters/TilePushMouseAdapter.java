package hr.nhex.graphic.adapters;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.board.Board;
import hr.nhex.board.BoardTile;
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
					List<Pair> pusheeAdjecantTiles = game.getBoard().getAdjecantTiles(tilePusheePos.getX(), tilePusheePos.getY());
					List<Pair> possiblePushPos = new ArrayList<Pair>();

					for (Pair pusheeAdj : pusheeAdjecantTiles) {
						if (!game.getBoard().isFilled(pusheeAdj.getX(), pusheeAdj.getY())
								&& (Math.abs(pusheeAdj.getX() - tilePusher.getX()) > 1 || Math.abs(pusheeAdj.getY() - tilePusher.getY()) > 1)) {
							possiblePushPos.add(pusheeAdj);
						}
					}

					if (possiblePushPos.size() == 1) {

						Pair newPosition = possiblePushPos.get(0);
						Board board = game.getBoard();
						BoardTile pushee = board.getTile(tilePusheePos.getX(), tilePusheePos.getY());
						pushee.setX(newPosition.getX());
						pushee.setY(newPosition.getY());
						BattleSimulator bs = new BattleSimulator(board);
						bs.updateAfterEffects();

						cn.repaint();

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
						}
					}

				}

			} else {

				BoardTile tilePushee = (BoardTile) game.getSelectedTile();
				Pair tilePusheePos = getClickedTile(cn, ev);
				int specialTilePos = hlc.getSpecialHexList().lastIndexOf(new SpecialHex(tilePusheePos, null));

				if (specialTilePos != -1) {

				}


			}

		}
	}

}
