package hr.nhex.board;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.game.Game;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.adapters.TilePlacementMouseAdapter;
import hr.nhex.graphic.timer.BattleTimer;
import hr.nhex.graphic.timer.TileAttackTimer;
import hr.nhex.model.action.ActionTile;
import hr.nhex.model.action.ActionType;

import java.awt.event.MouseEvent;

/**
 * Class that resolves board changes when action tile is played.
 * 
 * @author Luka Rukliæ
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
	public boolean resolve(ActionTile at, MouseEvent ev, NeuroshimaCanvas cn) {

		Pair tilePos = TilePlacementMouseAdapter.getClickedTile(cn, ev);
		Game game = cn.getGameInstance();

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
			if (tileTarget != null && tileTarget.getPlayer() == game.getCurrentPlayer()) {

				for (Pair p : cn.getGameInstance().getBoard().getAdjecantTiles(tileTarget.getX(), tileTarget.getY())) {
					if (!cn.getGameInstance().getBoard().isFilled(p.getX(), p.getY())) {
						cn.getTmma().addToSpecialHex(p.getX(), p.getY());
					}
				}
				if (!cn.getTmma().getSpecialHex().isEmpty()) {
					cn.getTmma().setSelectedTile(tileTarget);
					cn.mouseListenerActivate(cn.getTmma());
				}
				return true;
			}

		}

		return false;
	}

}
