package hr.nhex.model;

import hr.nhex.board.BoardTile;
import hr.nhex.generic.Pair;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.unit.Attack;

import java.util.ArrayList;
import java.util.List;

public class Netter extends Unit {

	private List<Pair> nettedTiles = new ArrayList<>();

	public Netter(String name, int hitPoints, List<Integer> speed, List<Attack> attacks,
			List<Ability> abilities) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.speed = speed;
		this.attacks = attacks;
		this.abilities = abilities;
	}

	private Netter(String name, Player player, int x, int y, int hitPoints, int angle, boolean isNetted,
			List<Integer> speed, List<Attack> attacks, List<Ability> abilities, List<Pair> nettedTiles) {
		super(name, player, x, y, hitPoints, angle, isNetted, speed, attacks, abilities);
		this.nettedTiles = nettedTiles;
	}

	public void addNettedTile(Pair tileCoordinates) {
		nettedTiles.add(tileCoordinates);
	}

	public List<Pair> getNettedTiles() {
		return nettedTiles;
	}

	@Override
	public BoardTile copy() {

		List<Integer> newSpeed = new ArrayList<Integer>();
		List<Attack> newAttack = new ArrayList<Attack>();
		List<Ability> newAbility = new ArrayList<Ability>();
		List<Pair> newNetted = new ArrayList<Pair>();


		for (Integer speed : this.speed) {
			newSpeed.add(speed.intValue());
		}

		for (Attack attack : this.attacks) {
			newAttack.add(new Attack(attack.getPointsTo(), attack.getValue(), attack.getType()));
		}

		for (Ability ability : this.abilities) {
			newAbility.add(new Ability(ability.getPointsTo(), ability.getType()));
		}

		for (Pair netted : this.nettedTiles) {
			newNetted.add(new Pair(netted.getX(), netted.getY()));
		}

		return new Netter(
				this.name,
				new Player(this.player.getPlayerName(), this.player.getPlayerColor(), this.player.getPlayerDeck()),
				this.x,
				this.y,
				this.hitPoints,
				this.angle,
				this.isNetted,
				newSpeed,
				newAttack,
				newAbility,
				newNetted
				);

	}

}
