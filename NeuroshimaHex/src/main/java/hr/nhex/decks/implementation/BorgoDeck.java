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


public class BorgoDeck extends Deck implements IBasicDeck {

	private static final int MOVE_NUMBER = 4;
	private static final int BATTLE_NUMBER = 6;
	private static final int GRENADE_NUMBER = 1;

	private static final int UNIT_MUTANT = 6;
	private static final int UNIT_BUTCHER = 4;
	private static final int UNIT_NET_FIGHTER = 2;
	private static final int UNIT_BRAWLER = 1;
	private static final int UNIT_SUPER_MUTANT = 2;
	private static final int UNIT_ASSASSIN = 2;

	private static final int MODULE_OFFICER = 2;
	private static final int MODULE_SUPER_OFFICER = 1;
	private static final int MODULE_SCOUT = 2;
	private static final int MODULE_MEDIC = 1;

	public BorgoDeck() {

		this.setDeckName("Borgo");

		hqTileCreation();
		actionTileCreation();
		unitTileCreation();
		moduleTileCreation();

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
			abilities.add(new Ability(j, AbilityType.PLUS_ONE_SPEED));
		}

		this.addTileToDeck(new HQ(
				"Borgo HQ",
				STANDARD_HQ_HIT_POINTS,
				new ArrayList<>(speed),
				new ArrayList<>(attacks),
				new ArrayList<>(abilities)));

	}

	@Override
	public void actionTileCreation() {

		for (int i = 0; i < BATTLE_NUMBER; i++) {
			this.addTileToDeck(new ActionTile("Battle", ActionType.BATTLE));
		}
		for (int i = 0; i < MOVE_NUMBER; i++) {
			this.addTileToDeck(new ActionTile("Move", ActionType.MOVE));
		}
		for (int i = 0; i < GRENADE_NUMBER; i++) {
			this.addTileToDeck(new ActionTile("Grenade", ActionType.GRENADE));
		}

	}

	@Override
	public void unitTileCreation() {

		List<Integer> speed = new ArrayList<>();
		List<Attack> attacks = new ArrayList<>();
		List<Ability> abilities = new ArrayList<>();

		for (int i = 0; i < UNIT_BRAWLER; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(2);

			attacks.add(new Attack(0, 2, AttackType.MELEE));
			attacks.add(new Attack(1, 1, AttackType.MELEE));
			attacks.add(new Attack(5, 1, AttackType.MELEE));

			abilities.add(new Ability(0, AbilityType.BLOCK));
			abilities.add(new Ability(1, AbilityType.BLOCK));
			abilities.add(new Ability(5, AbilityType.BLOCK));


			this.addTileToDeck(new Unit(
					"Brawler",
					2,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_BUTCHER; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(3);
			attacks.add(new Attack(0, 1, AttackType.MELEE));
			attacks.add(new Attack(1, 1, AttackType.MELEE));
			this.addTileToDeck(new Unit(
					"Butcher",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_MUTANT; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(2);
			attacks.add(new Attack(0, 1, AttackType.MELEE));
			attacks.add(new Attack(1, 1, AttackType.MELEE));
			attacks.add(new Attack(5, 1, AttackType.MELEE));
			this.addTileToDeck(new Unit(
					"Mutant",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_ASSASSIN; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(3);
			attacks.add(new Attack(0, 1, AttackType.RANGED));

			abilities.add(new Ability(6, AbilityType.MOVE));

			this.addTileToDeck(new Unit(
					"Assassin",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_NET_FIGHTER; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(1);
			attacks.add(new Attack(0, 3, AttackType.MELEE));

			abilities.add(new Ability(0, AbilityType.NET));

			this.addTileToDeck(new Netter(
					"Net Fighter",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < UNIT_SUPER_MUTANT; i++) {
			clearAllLists(speed, attacks, abilities);
			speed.add(2);
			attacks.add(new Attack(0, 2, AttackType.MELEE));

			this.addTileToDeck(new Unit(
					"Super Mutant",
					1,
					new ArrayList<>(speed),
					new ArrayList<>(attacks),
					new ArrayList<>(abilities)));
		}
	}

	@Override
	public void moduleTileCreation() {

		List<Ability> abilities = new ArrayList<>();

		for (int i = 0; i < MODULE_OFFICER; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(1, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(5, AbilityType.PLUS_ONE_MELEE));
			this.addTileToDeck(new Module(
					"Officer",
					1,
					new ArrayList<>(abilities)));
		}

		for (int i = 0; i < MODULE_SUPER_OFFICER; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(1, AbilityType.PLUS_ONE_MELEE));
			abilities.add(new Ability(5, AbilityType.PLUS_ONE_MELEE));

			this.addTileToDeck(new Module(
					"Super Officer",
					2,
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

		for (int i = 0; i < MODULE_MEDIC; i++) {
			abilities.clear();
			abilities.add(new Ability(0, AbilityType.MEDIC));
			abilities.add(new Ability(1, AbilityType.MEDIC));
			abilities.add(new Ability(5, AbilityType.MEDIC));
			this.addTileToDeck(new Module(
					"Medic",
					1,
					new ArrayList<>(abilities)));
		}

	}

}
