package hr.nhex.graphic.timer;


/**
 * Enumeration that represent geographic directions in which tile can attack.
 * 
 * @author Luka Rukliæ
 *
 */

public enum AttackDirection {

	/**
	 * Northwest.
	 */
	NW {
		@Override
		public AttackDirection oppositeDirection() {
			return AttackDirection.SE;
		}
	},
	/**
	 * West.
	 */
	W {
		@Override
		public AttackDirection oppositeDirection() {
			return AttackDirection.E;
		}
	},
	/**
	 * Southwest.
	 */
	SW {
		@Override
		public AttackDirection oppositeDirection() {
			return AttackDirection.NE;
		}
	},
	/**
	 * Southeast.
	 */
	SE {
		@Override
		public AttackDirection oppositeDirection() {
			return AttackDirection.NW;
		}
	},
	/**
	 * East.
	 */
	E {
		@Override
		public AttackDirection oppositeDirection() {
			return AttackDirection.W;
		}
	},
	/**
	 * Northeast.
	 */
	NE {
		@Override
		public AttackDirection oppositeDirection() {
			return AttackDirection.SW;
		}
	};

	/**
	 * Method that returns opposite direction to the one given.
	 * 
	 * @return opposite direction
	 */
	public abstract AttackDirection oppositeDirection();
}
