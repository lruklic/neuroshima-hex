package hr.nhex.model.ability;

/**
 * Class that represents an ability of BoardTile or FoundationTile (nyi).
 *
 * @author Luka Rukli�
 *
 */

public class Ability {

	/**
	 * Direction at which ability is bestowed.
	 */
	private int pointsTo;

	/**
	 * Type of ability..
	 */
	private AbilityType type;

	public Ability(int pointsTo, AbilityType type) {
		this.pointsTo = pointsTo;
		this.type = type;
	}

	public int getPointsTo() {
		return pointsTo;
	}

	public void setPointsTo(int pointsTo) {
		this.pointsTo = pointsTo;
	}

	public AbilityType getType() {
		return type;
	}

	public void setType(AbilityType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pointsTo;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Ability other = (Ability) obj;
		if (pointsTo != other.pointsTo) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}




}
