package hr.nhex.model.unit;

/**
 * Razred koji predstavlja napad određene jedinice.
 * 
 * @author Luka Ruklić
 *
 */

public class Attack {

	/**
	 * Na kojoj strani polja se napad nalazi.
	 */
	private int pointsTo;

	/**
	 * Jačina napada.
	 */
	private int value;

	/**
	 * Tip napada.
	 */
	private AttackType type;

	/**
	 * Konstruktor za napad.
	 * 
	 * @param pointsTo strana polja na kojoj se napad nalazi
	 * @param value jačina napada
	 * @param type vrsta napada
	 */
	public Attack(int pointsTo, int value, AttackType type) {
		this.pointsTo = pointsTo;
		this.value = value;
		this.type = type;
	}

	public int getPointsTo() {
		return pointsTo;
	}

	public void setPointsTo(int pointsTo) {
		this.pointsTo = pointsTo;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public AttackType getType() {
		return type;
	}

	public void setType(AttackType type) {
		this.type = type;
	}


}
