package hr.nhex.model.unit;

import hr.nhex.battle.BattleTile;
import hr.nhex.board.BoardTile;
import hr.nhex.model.HQ;

/**
 * Enum that represents attack types.
 *
 * @author Luka Ruklic
 *
 */

public enum AttackType {

	MELEE,

	RANGED,

	HQ_MELEE;

	public void attack(BattleTile battleTile, int attackStrength, AttackType attackType) {

		BoardTile tile = battleTile.getTile();
		if (tile instanceof BoardTile) {
			if (!(battleTile.getTile() instanceof HQ && attackType == HQ_MELEE)) {
				tile.setHitPoints(tile.getHitPoints() - attackStrength);
			}
		}

	};

}
