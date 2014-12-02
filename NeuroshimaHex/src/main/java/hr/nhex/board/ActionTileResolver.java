package hr.nhex.board;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.game.Game;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.adapters.AdapterType;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;
import hr.nhex.graphic.timer.BattleTimer;
import hr.nhex.graphic.timer.TileAttackTimer;
import hr.nhex.model.action.ActionTile;
import hr.nhex.model.action.ActionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that resolves board changes when action tile is played.
 *
 * @author Luka Ruklic
 *
 */

public class ActionTileResolver {

	/**
	 * Method that resolves played action tile action.
	 *
	 * @param at played action tile
	 * @param ev mouse event with coordinates of mouse cursor location when tile was played
	 * @param cn top level container
	 * @return <code>true</code> if action was successfully resolved, <code>false</code> otherwise
	 */
	public boolean resolve(ActionTile at, Pair tilePos, NeuroshimaCanvas cn) {

		Game game = cn.getGameInstance();
		HexagonListContainer hlc = HexagonListContainer.getInstance();

		if (at.getActionType() == ActionType.BATTLE) {
			BattleSimulator bs = new BattleSimulator(game.getBoard());

			TileAttackTimer tat = new TileAttackTimer(cn);
			BattleTimer bt = new BattleTimer(cn, bs, tat);
			bt.animateBattle();

			System.out.println("Borba: \n"+bs.getBattleEvents());

			return true;

		} else if (tilePos != null && at.getActionType() == ActionType.SNIPER) {

			BoardTile tileTarget = game.getBoard().getTile(tilePos.getX(), tilePos.getY());
			if (tileTarget != null && tileTarget.getPlayer() != game.getCurrentPlayer()) {
				tileTarget.setHitPoints(tileTarget.getHitPoints() - 1);
				if (tileTarget.getHitPoints() == 0) {
					BattleSimulator bs = new BattleSimulator(game.getBoard());
					bs.updateAfterEffects();
				}
				return true;
			}
		} else if (tilePos != null && at.getActionType() == ActionType.MOVE) {

			BoardTile tileTarget = game.getBoard().getTile(tilePos.getX(), tilePos.getY());
			if (tileTarget != null && tileTarget.getPlayer() == game.getCurrentPlayer()
					&& !game.getBoard().tileIsNetted(tileTarget.getX(), tileTarget.getY(), 0, tileTarget.getPlayer())) {

				for (Pair p : game.getBoard().getAdjecantTiles(tileTarget.getX(), tileTarget.getY())) {
					if (!game.getBoard().isFilled(p.getX(), p.getY())) {
						hlc.getSpecialHexList().add(new SpecialHex(new Pair(p.getX(), p.getY()), game.getCurrentPlayer().getPlayerColor()));
					}
				}
				if (!hlc.getSpecialHexList().isEmpty()) {
					game.setSelectedTile(tileTarget);
					cn.getMac().mouseListenerActivate(AdapterType.MOVEMENT);
				}
				return true;
			}
		} else if (tilePos != null && at.getActionType() == ActionType.PUSH) {

			BoardTile tilePusher = game.getBoard().getTile(tilePos.getX(), tilePos.getY());
			if (tilePusher != null && tilePusher.getPlayer().equals(game.getCurrentPlayer())
					&& !game.getBoard().tileIsNetted(tilePusher.getX(), tilePusher.getY(), 0, tilePusher.getPlayer())) {

				List<Pair> pusherAdjecantTiles = game.getBoard().getAdjecantTiles(tilePusher.getX(), tilePusher.getY());
				List<Pair> pusheeTiles = new ArrayList<Pair>();

				for (Pair pusherAdj : pusherAdjecantTiles) {
					BoardTile tilePushee = game.getBoard().getTile(pusherAdj.getX(), pusherAdj.getY());

					if (tilePushee != null && !tilePushee.getPlayer().equals(game.getCurrentPlayer())) {	// da li se može pushati nettani unit?
						List<Pair> pusheeAdjecantTiles = game.getBoard().getAdjecantTiles(tilePushee.getX(), tilePushee.getY());
						for (Pair pusheeAdj : pusheeAdjecantTiles) {
							if (!game.getBoard().isFilled(pusheeAdj.getX(), pusheeAdj.getY())
									&& (Math.abs(pusheeAdj.getX() - tilePusher.getX()) > 1 || Math.abs(pusheeAdj.getY() - tilePusher.getY()) > 1)) {
								pusheeTiles.add(pusherAdj);
								hlc.getSpecialHexList().add(new SpecialHex(new Pair(tilePushee.getX(), tilePushee.getY()), game.getCurrentPlayer().getPlayerColor()));
								break;
							}
						}
					}
				}

				if (!pusheeTiles.isEmpty()) {
					game.setSelectedTile(tilePusher);
					cn.getMac().mouseListenerActivate(AdapterType.PUSH);	// dodaj push adapter
					return true;
				}

			}

		}

		return false;
	}

}
