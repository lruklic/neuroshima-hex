package hr.nhex.decks.implementation;

import hr.nhex.decks.Deck;
import hr.nhex.decks.IBasicDeck;
import hr.nhex.model.HQ;
import hr.nhex.model.Module;
import hr.nhex.model.Netter;
import hr.nhex.model.Unit;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.ability.AbilityType;
import hr.nhex.model.action.ActionTile;
import hr.nhex.model.action.ActionType;
import hr.nhex.model.unit.Attack;
import hr.nhex.model.unit.AttackType;

import java.util.ArrayList;
import java.util.List;

public class HegemonyDeck extends Deck implements IBasicDeck {

	private static final int MOVE_NUMBER = 3;
	private static final int BATTLE_NUMBER = 5;
	private static final int SNIPER_NUMBER = 1;
	private static final int PUSH_NUMBER = 2;

	private static final int UNIT_GANGER = 4;
	private static final int UNIT_GLADIATOR = 1;
	private static final int UNIT_NET_FIGHTER = 2;
	private static final int UNIT_NET_MASTER = 1;
	private static final int UNIT_RUNNER = 3;
	private static final int UNIT_THUG = 1;
	private static final int UNIT_GUARD = 1;
	private static final int UNIT_UNIVERSAL_SOLDIER = 3;

	private static final int MODULE_OFFICER_ONE = 2;
	private static final int MODULE_OFFICER_TWO = 1;
	private static final int MODULE_THE_BOSS = 1;
	private static final int MODULE_TRANSPORT = 20; // 1
	private static final int MODULE_QUARTERMASTER = 1;
	private static final int MODULE_SCOUT = 1;

	public HegemonyDeck() {

		this.setDeckName("Hegemony");

		actionTileCreation();
		unitTileCreation();
		moduleTileCreation();
		hqTileCreation();

	}

	@Override
	public void hqTileCreation() {

		List<Integer> speed = new ArrayList<>();
		List<Attack> attacks = new ArrayList<>();
		List<Ability> abilities = new ArrayList<>();


		clearAllLists(speed, attacks, abilities);
		speed.add(0);
		for (int j = 0; j < NUMBER_OF_SIDES; j++) {
			attacks.add(new Attack(j, 1, AttackType.HQ_MELEE));
		}
		for (int j = 0; j < NUMBER_OF_SIDES; j++) {
			abilities.add(new Ability(j, AbilityType.PLUS_ONE_MELEE));
		}

		this.addTileToDeck(new HQ(
				"Hegemony HQ",
				STANDARD_HQ_HIT_POINTS,
				new ArrayList<>(speed),
				new ArrayList<>(attacks),
				new ArrayList<>(abilities))
				);


	}

	@Override
	public void actionTileCreation() {

		for (int i = 0; i < BATTLE_NUMBER; i++) {
			this.addTileToDeck(new ActionTile("Battle", ActionType.BATTLE));
		}
		for (int i = 0; i < MOVE_NUMBER; i++) {
			this.addTileToDeck(new ActionTile("Move", ActionType.MOVE));
		}
		for (int i = 0; i < SNIPER_NUMBER; i++) {
			this.addTileToDeck(new ActionTile("Sniper", ActionType.SNIPER));
		}
		for (int i = 0; i < PUSH_NUMBER; i++) {
			this.addTileToDeck(new ActionTile("Push", ActionType.PUSH));
		}

	}

	@Override
	public void unitTileCreation() {

		List<Integer> speed = new ArrayList<>();
		List<Attack> attacks = new ArrayList<>();
		List<Ability> abilities = new ArrayList<>();

		for (int i = 0; i < UNIT_GANGER; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(3);
			attacks.add(new Attack(0, 1, AttackType.MELEE));
			this.addTileToDeck(new Unit(
					"Ganger",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_GLADIATOR; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(1);

			attacks.add(new Attack(0, 2, AttackType.MELEE));
			attacks.add(new Attack(1, 2, AttackType.MELEE));
			attacks.add(new Attack(5, 2, AttackType.MELEE));

			abilities.add(new Ability(0, AbilityType.BLOCK));
			abilities.add(new Ability(1, AbilityType.BLOCK));
			abilities.add(new Ability(5, AbilityType.BLOCK));

			this.addTileToDeck(new Unit(
					"Gladiator",
					2,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_THUG; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(2);
			attacks.add(new Attack(0, 2, AttackType.MELEE));
			attacks.add(new Attack(1, 1, AttackType.MELEE));
			attacks.add(new Attack(5, 1, AttackType.MELEE));

			this.addTileToDeck(new Unit(
					"Thug",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_GUARD; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(2);
			attacks.add(new Attack(0, 1, AttackType.MELEE));
			attacks.add(new Attack(1, 1, AttackType.MELEE));
			attacks.add(new Attack(5, 1, AttackType.MELEE));

			this.addTileToDeck(new Unit(
					"Guard",
					2,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_RUNNER; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(2);
			attacks.add(new Attack(0, 1, AttackType.MELEE));

			abilities.add(new Ability(6, AbilityType.MOVE));

			this.addTileToDeck(new Unit(
					"Runner",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_NET_FIGHTER; i++) {
			clearAllLists(speed, attacks, abilities);

			abilities.add(new Ability(0, AbilityType.NET));

			this.addTileToDeck(new Netter(
					"Net Fighter",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_NET_MASTER; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(2);
			attacks.add(new Attack(0, 1, AttackType.MELEE));

			abilities.add(new Ability(1, AbilityType.NET));
			abilities.add(new Ability(5, AbilityType.NET));

			this.addTileToDeck(new Netter(
					"Net Master",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_UNIVERSAL_SOLDIER; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(3);
			attacks.add(new Attack(0, 1, AttackType.MELEE));
			attacks.add(new Attack(0, 1, AttackType.RANGED));

			this.addTileToDeck(new Unit(
					"Universal Soldier",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

	}


	@Override
	public void moduleTileCreation() {
		List<Ability> abilities = new ArrayList<>();

		for (int i = 0; i < MODULE_OFFICER_ONE; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(1, AbilityType.PLUS_ONE_MELEE));
			this.addTileToDeck(new Module(
					"Officer One",
					1,
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < MODULE_OFFICER_TWO; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(1, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(5, AbilityType.PLUS_ONE_MELEE));
			this.addTileToDeck(new Module(
					"Officer Two",
					1,
					new ArrayList<>(abilities)));
		}
		for (int i = 0; i < MODULE_SCOUT; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.PLUS_ONE_SPEED));
			abilities.add(new Ability(1, AbilityType.PLUS_ONE_SPEED));
			abilities.add(new Ability(5, AbilityType.PLUS_ONE_SPEED));
			this.addTileToDeck(new Module(
					"Scout",
					1,
					new ArrayList<>(abilities)));
		}
		for (int i = 0; i < MODULE_THE_BOSS; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.PLUS_ONE_SPEED));
			abilities.add(new Ability(1, AbilityType.PLUS_ONE_SPEED));
			abilities.add(new Ability(0, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(1, AbilityType.PLUS_ONE_MELEE));
			this.addTileToDeck(new Module(
					"The Boss",
					1,
					new ArrayList<>(abilities)));
		}
		for (int i = 0; i < MODULE_TRANSPORT; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.TRANSPORT));
			abilities.add(new Ability(1, AbilityType.TRANSPORT));
			abilities.add(new Ability(2, AbilityType.TRANSPORT));
			abilities.add(new Ability(3, AbilityType.TRANSPORT));
			abilities.add(new Ability(4, AbilityType.TRANSPORT));
			abilities.add(new Ability(5, AbilityType.TRANSPORT));
			this.addTileToDeck(new Module(
					"Transport",
					1,
					new ArrayList<>(abilities)));
		}
		for (int i = 0; i < MODULE_QUARTERMASTER; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.QUARTERMASTER));
			this.addTileToDeck(new Module(
					"Quartermaster",
					1,
					new ArrayList<>(abilities)));
		}

	}

}
