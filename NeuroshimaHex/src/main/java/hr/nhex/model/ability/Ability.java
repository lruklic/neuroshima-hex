package hr.nhex.model.ability;

/**
 * Razred koji predstavlja sposobnost jedinice, modula ili baze.
 * 
 * @author Luka Rukliæ
 *
 */

public class Ability {

	/**
	 * Na kojoj strani polja se sposobnost nalazi.
	 */
	private int pointsTo;

	/**
	 * Vrsta sposobnosti.
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ability other = (Ability) obj;
		if (pointsTo != other.pointsTo)
			return false;
		if (type != other.type)
			return false;
		return true;
	}




}
