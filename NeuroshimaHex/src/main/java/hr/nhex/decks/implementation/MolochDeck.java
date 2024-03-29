package hr.nhex.decks.implementation;

import hr.nhex.decks.Deck;
import hr.nhex.decks.IBasicDeck;
import hr.nhex.model.HQ;
import hr.nhex.model.ability.Ability;
import hr.nhex.model.ability.AbilityType;
import hr.nhex.model.unit.Attack;
import hr.nhex.model.unit.AttackType;

import java.util.ArrayList;
import java.util.List;

public class MolochDeck extends Deck implements IBasicDeck {

	public MolochDeck() {

		this.setDeckName("Moloch");
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
			abilities.add(new Ability(j, AbilityType.PLUS_ONE_RANGED));
		}

		this.addTileToDeck(new HQ(
				"Moloch HQ",
				STANDARD_HQ_HIT_POINTS,
				new ArrayList<>(speed),
				new ArrayList<>(attacks),
				new ArrayList<>(abilities)));

	}

	@Override
	public void actionTileCreation() {



	}

	@Override
	public void unitTileCreation() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moduleTileCreation() {
		// TODO Auto-generated method stub

	}

}
