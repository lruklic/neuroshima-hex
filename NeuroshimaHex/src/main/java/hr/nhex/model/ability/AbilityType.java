package hr.nhex.model.ability;

import hr.nhex.battle.BattleTile;
import hr.nhex.board.BoardTile;
import hr.nhex.model.Unit;
import hr.nhex.model.unit.Attack;
import hr.nhex.model.unit.AttackType;

import java.util.List;

public enum AbilityType {

	MOVE {
		@Override
		public void applyBonus(BattleTile battleTile) {
			// TODO Auto-generated method stub

		}
	},
	BLOCK {
		@Override
		public void applyBonus(BattleTile battleTile) {
			// TODO Auto-generated method stub
		}
	},

	NET {
		@Override
		public void applyBonus(BattleTile battleTile) {
			// TODO Auto-generated method stub

		}
	},

	PLUS_ONE_MELEE {
		@Override
		public void applyBonus(BattleTile battleTile) {
			BoardTile tile = battleTile.getTile();
			if (tile instanceof Unit) {
				for (Attack attack : ((Unit)tile).getAttacks()) {
					if (attack.getType() == AttackType.MELEE) {
						attack.setValue(attack.getValue() + 1);
					}
				}
			}
		}
	},

	PLUS_ONE_RANGED {
		@Override
		public void applyBonus(BattleTile battleTile) {
			BoardTile tile = battleTile.getTile();
			if (tile instanceof Unit) {
				for (Attack attack : ((Unit)tile).getAttacks()) {
					if (attack.getType() == AttackType.RANGED) {
						attack.setValue(attack.getValue() + 1);
					}
				}
			}
		}

	},

	PLUS_ONE_SPEED {
		@Override
		public void applyBonus(BattleTile battleTile) {
			BoardTile tile = battleTile.getTile();
			if (tile instanceof Unit) {
				List<Integer> speedList = ((Unit)tile).getSpeed();
				for (int i = 0; i < speedList.size(); i++) {
					speedList.set(i, speedList.get(i) + 1);
				}
			}
		}
	},

	QUARTERMASTER {
		@Override
		public void applyBonus(BattleTile battleTile) {
			// TODO Auto-generated method stub

		}
	},

	TRANSPORT {
		@Override
		public void applyBonus(BattleTile battleTile) {
			// TODO Auto-generated method stub

		}
	},

	MEDIC {
		@Override
		public void applyBonus(BattleTile battleTile) {
			// TODO Auto-generated method stub

		}
	};

	public abstract void applyBonus(BattleTile battleTile);

}
