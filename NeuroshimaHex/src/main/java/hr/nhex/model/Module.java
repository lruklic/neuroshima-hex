package hr.nhex.model;

import hr.nhex.board.BoardTile;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja modul u igri Neuroshima Hex. Sadr�i listu sa sposobnostima danog modula te naslje�uje svojstva
 * od razreda Tile.
 *
 * @author Luka Rukli�
 *
 */

public class Module extends BoardTile {

	/**
	 * Lista sa sposobnostima odre�enog modula.
	 */
	private List<Ability> abilities = new ArrayList<>();

	/**
	 * Konstruktor za razred Module. Zanemaruje konstruktore u nadklasama BoardTile i Tile. Koristi se za postavljanje modula u �pil.
	 *
	 * @param name naziv modula
	 * @param hitPoints �ivotni bodovi modula
	 * @param abilities lista sa sposobnostima modula
	 */

	public Module(String name, int hitPoints, List<Ability> abilities) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
		this.abilities = abilities;
	}

	/**
	 *
	 * Konstruktor koji stvara �itavi modul, zajedno s vrijednostima varijabli iz njegovih nadklasa BoardTile i Tile.
	 *
	 * @param name ime modula
	 * @param player instance igra�a koja sadr�i ime i boju igra�a te kontrolira modula
	 * @param x x-koordinata modula na plo�i
	 * @param y y-koordinata modula na plo�i
	 * @param hitPoints �ivotni bodovi
	 * @param angle kut za koji je modul rotiran
	 * @param isNetted <code>boolean</code> vrijednost koja govori da li je modul pod mre�om
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
