package hr.nhex.model;

import hr.nhex.board.BoardTile;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.unit.Attack;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja jedinicu u igri Neuroshima Hex. Sadrži listu s brzinama, listu s napadima te listu
 * sa sposobnostima danog modula te nasljeđuje svojstva od razreda Tile.
 * 
 * @author Luka Ruklić
 *
 */

public class Unit extends BoardTile {

	/**
	 * Lista s brzinama određene jedinice.
	 */
	protected List<Integer> speed;

	/**
	 * Lista s napadima određene jedinice.
	 */
	protected List<Attack> attacks;

	/**
	 * Lista sa sposobnostima određene jedinice.
	 */
	protected List<Ability> abilities;

	public Unit () {

	}
	/**
	 * Konstruktor za razred Unit. Zanemaruje konstruktore u nadklasama BoardTile i Tile. Koristi se za postavljanje jedinice u špil.
	 * 
	 * @param name naziv jedinice
	 * @param hitPoints �ivotni bodovi
	 * @param speed lista s brzinama
	 * @param attack lista s napadima
	 * @param abilities lista sa sposobnostima
	 */
	public Unit(String name, int hitPoints, List<Integer> speed, List<Attack> attacks, List<Ability> abilities) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.speed = speed;
		this.attacks = attacks;
		this.abilities = abilities;
	}

	/**
	 * 
	 * Konstruktor koji stvara čitavu jedinicu, zajedno s vrijednostima varijabli iz njegovih nadklasa BoardTile i Tile.
	 * 
	 * @param name ime jedinice
	 * @param player instance igrača koja sadrži ime i boju igrača te kontrolira jedinicu
	 * @param x x-koordinata jedinice na ploči
	 * @param y y-koordinata jedinice na ploči
	 * @param hitPoints životni bodovi
	 * @param angle kut za koji je jedinica rotirana
	 * @param isNetted <code>boolean</code> vrijednost koja govori da li je jedinica pod mrežom
	 * @param speed lista s brzinama
	 * @param attacks lista s napadima
	 * @param abilities lista sa sposobnostima
	 */

	protected Unit(String name, Player player, int x, int y, int hitPoints, int angle, boolean isNetted,
			List<Integer> speed, List<Attack> attacks, List<Ability> abilities) {
		super(name, player, x, y, hitPoints, angle, isNetted);
		this.speed = speed;
		this.attacks = attacks;
		this.abilities = abilities;
	}

	public List<Integer> getSpeed() {
		return speed;
	}

	public List<Attack> getAttacks() {
		return attacks;
	}

	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public BoardTile copy() {

		List<Integer> newSpeed = new ArrayList<Integer>();
		List<Attack> newAttack = new ArrayList<Attack>();
		List<Ability> newAbility = new ArrayList<Ability>();

		for (Integer speed : this.speed) {
			newSpeed.add(speed.intValue());
		}

		for (Attack attack : this.attacks) {
			newAttack.add(new Attack(attack.getPointsTo(), attack.getValue(), attack.getType()));
		}

		for (Ability ability : this.abilities) {
			newAbility.add(new Ability(ability.getPointsTo(), ability.getType()));
		}

		return new Unit(
				this.name,
				new Player(this.player.getPlayerName(), this.player.getPlayerColor(), this.player.getPlayerDeck()),
				this.x,
				this.y,
				this.hitPoints,
				this.angle,
				this.isNetted,
				newSpeed,
				newAttack,
				newAbility
				);
	}


}
