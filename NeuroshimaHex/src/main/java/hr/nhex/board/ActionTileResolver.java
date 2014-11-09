package hr.nhex.board;

import hr.nhex.battle.BattleSimulator;
import hr.nhex.game.Game;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.NeuroshimaCanvas;
import hr.nhex.graphic.adapters.TilePlacementMouseAdapter;
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

	public boolean resolve(ActionTile at, MouseEvent ev, NeuroshimaCanvas cn) {

		Pair tilePos = TilePlacementMouseAdapter.getClickedTile(cn, ev);
		Game game = cn.getGameInstance();
		if (tilePos != null && at.getActionType() == ActionType.SNIPER) {

			BoardTile tileTarget = game.getBoard().getTile(tilePos.getX(), tilePos.getY());
			if (tileTarget != null && tileTarget.getPlayer() != game.getCurrentPlayer()) {
				tileTarget.setHitPoints(tileTarget.getHitPoints() - 1);
				if (tileTarget.getHitPoints() == 0) {
					BattleSimulator bs = new BattleSimulator(game.getBoard());
					bs.updateAfterEffects();
				}
			}
			return true;
		}

		return false;
	}

}
