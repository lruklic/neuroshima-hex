package hr.nhex.graphic.timer;

import hr.nhex.battle.BattleEvent;
import hr.nhex.battle.BattleEventType;
import hr.nhex.battle.BattleSimulator;
import hr.nhex.generic.Pair;
import hr.nhex.graphic.hexagon.HexagonListContainer;
import hr.nhex.graphic.hexagon.SpecialHex;
import hr.nhex.graphic.mouse.GenericMouseAdapter;
import hr.nhex.graphic.mouse.adapters.AdapterType;
import hr.nhex.graphic.mouse.resolvers.MedicResolver;
import hr.nhex.graphic.timer.observer.TimerObserver;
import hr.nhex.graphic.timer.observer.TimerSubject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public class BattleAnimation implements ActionListener, TimerSubject {

	private List<TimerObserver> observers = new ArrayList<TimerObserver>();

	private Timer battleAnimationTimer;

	private TileAttackTimer tat;
	private BattleSimulator bs;

	private int battleAnimationTime = 0;

	private int currentEventNumber = 0;

	public BattleAnimation(BattleSimulator bs, TileAttackTimer tat) {
		this.bs = bs;
		this.tat = tat;
	}

	public void animateRound() {

		this.battleAnimationTime = bs.getBattleEvents().size() * 500 + 500;

		this.battleAnimationTimer = new Timer(battleAnimationTime, this);
		battleAnimationTimer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// System.out.println("Possible medic pairs: "+bs.getMedicControl().getPossibleMedicPairs());

		if (currentEventNumber == 0 && bs.getMedicControl().getPossibleMedicPairs().size() != 0) {
			battleAnimationTimer.stop();

			GenericMouseAdapter gma = tat.getCn().getGenericMouseAdapter();
			gma.setActiveAdapterType(AdapterType.MEDIC);
			((MedicResolver) gma.getActiveResolver()).setCurrentBattleAnimation(this);
			((MedicResolver) gma.getActiveResolver()).setCurrentBattleSimulator(bs);


			List<SpecialHex> medicList = bs.getMedicControl().possibleMedicSpecialHex();
			HexagonListContainer.getInstance().setSpecialHexList(medicList);

			return;

		}

		if (currentEventNumber == bs.getBattleEvents().size()) {
			battleAnimationTimer.stop();
			currentEventNumber = 0;
			notifyObservers();
			return;
		}

		BattleEvent currentEvent = bs.getBattleEvents().get(currentEventNumber);

		if (currentEvent.getEventType() == BattleEventType.MELEE_ATTACK || currentEvent.getEventType() == BattleEventType.RANGED_ATTACK) {

			Pair attackerPosition = new Pair(currentEvent.getAttacker().getX(), currentEvent.getAttacker().getY());
			Pair targetPosition = new Pair(currentEvent.getTarget().getX(), currentEvent.getTarget().getY());

			tat.animateAttack(attackerPosition, targetPosition);
		}

		currentEventNumber++;

	}

	public List<TimerObserver> getObservers() {
		return observers;
	}

	@Override
	public void register(TimerObserver obj) {
		if (!observers.contains(obj)) {
			observers.add(obj);
		}
	}

	@Override
	public void unregister(TimerObserver obj) {
		observers.remove(obj);
	}

	@Override
	public void notifyObservers() {
		for (TimerObserver obj : observers) {
			obj.update();
		}
	}

	@Override
	public Object getUpdate(TimerObserver obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
