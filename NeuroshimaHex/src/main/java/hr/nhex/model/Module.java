package hr.nhex.model;

import hr.nhex.board.BoardTile;
import hr.nhex.model.ability.Ability;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja modul u igri Neuroshima Hex. Sadrži listu sa sposobnostima danog modula te nasljeðuje svojstva
 * od razreda Tile.
 * 
 * @author Luka Rukliæ
 *
 */

public class Module extends BoardTile {

	/**
	 * Lista sa sposobnostima odreðenog modula.
	 */
	private List<Ability> abilities = new ArrayList<>();

	/**
	 * Konstruktor za razred Module. Zanemaruje konstruktore u nadklasama BoardTile i Tile. Koristi se za postavljanje modula u špil.
	 * 
	 * @param name naziv modula
	 * @param hitPoints životni bodovi modula
	 * @param abilities lista sa sposobnostima modula
	 */

	public Module(String name, int hitPoints, List<Ability> abilities) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.abilities = abilities;
	}

	/**
	 * 
	 * Konstruktor koji stvara èitavi modul, zajedno s vrijednostima varijabli iz njegovih nadklasa BoardTile i Tile.
	 * 
	 * @param name ime modula
	 * @param player instance igraèa koja sadrži ime i boju igraèa te kontrolira modula
	 * @param x x-koordinata modula na ploèi
	 * @param y y-koordinata modula na ploèi
	 * @param hitPoints životni bodovi
	 * @param angle kut za koji je modul rotiran
	 * @param isNetted <code>boolean</code> vrijednost koja govori da li je modul pod mrežom
	 * @param abilities lista sa sposobnostima modula
	 */

	private Module(String name, Player player, int x, int y, int hitPoints, int angle, boolean isNetted, List<Ability> abilities) {
		super(name, player, x, y, hitPoints, angle, isNetted);
		this.abilities = abilities;
	}

	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public BoardTile copy() {

		List<Ability> newAbility = new ArrayList<Ability>();

		for (Ability ability : this.abilities) {
			newAbility.add(new Ability(ability.getPointsTo(), ability.getType()));
		}

		return new Module(
				this.name,
				new Player(this.player.getPlayerName(), this.player.getPlayerColor(), this.player.getPlayerDeck()),
				this.x,
				this.y,
				this.hitPoints,
				this.angle,
				this.isNetted,
				newAbility
				);
	}
}
